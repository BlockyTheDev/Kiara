package de.mcgregordev.kiara.nick.util;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;

public class NickUtil {
    private static final ListeningExecutorService service = MoreExecutors.listeningDecorator( Executors.newCachedThreadPool() );
    
    private static final String PACKAGENAME = Bukkit.getServer().getClass().getPackage().getName();
    private static final String VERSION = PACKAGENAME.substring( PACKAGENAME.lastIndexOf( "." ) + 1 );
    
    private static final String SKINURL = "http://skinapi.minesucht.net/";
    private static HashMap<String, JSONObject> textureCache = new HashMap<>();
   // private static Field nickField = accessField( GameProfile.class, "name" );
    
    private static boolean is13OrAbove() {
        return VERSION.contains( "13" ) || VERSION.contains( "14" );
    }
    
    public static String getName( UUID uuid ) {
        JSONObject json = getJSON( uuid.toString().replace( "-", "" ) );
        String name;
        try {
            name = json.get( "name" ).toString();
        } catch ( Exception e ) {
            name = "";
            System.out.println( uuid + " mag nicht." );
        }
        return name;
    }
    
    public static String getSkin( UUID uuid ) {
        JSONObject json = getJSON( uuid.toString().replaceAll( "-", "" ) );
        return ( (JSONObject) ( (JSONArray) json.get( "properties" ) ).get( 0 ) ).get( "value" ).toString();
    }
    
    public static String getSigniture( UUID uuid ) {
        JSONObject json = getJSON( uuid.toString().replaceAll( "-", "" ) );
        return ( (JSONObject) ( (JSONArray) json.get( "properties" ) ).get( 0 ) ).get( "signature" ).toString();
    }
    
    private static JSONObject getJSON( String uuid ) {
        if ( !textureCache.containsKey( uuid ) ) {
            try {
                URL url = new URL( SKINURL + uuid );
                URLConnection con = url.openConnection();
                InputStream in;
                in = con.getInputStream();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                /*JSONObject obj = (JSONObject) new JSONParser().parse( IOUtils.toString( in, encoding ) );
                textureCache.put( uuid, obj );
                return obj;/*
                 */
                return null;
            } catch ( IOException  e ) {
                e.printStackTrace();
            }
        } else {
            return textureCache.get( uuid );
        }
        return null;
    }
    
    public static ListenableFuture<String> nickPlayer( Player player, String name ) {
        try {
            Class<?> craftPlayerClass = Class.forName( "org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer" );
            Class<?> entityPlayerClass = Class.forName( "net.minecraft.server." + VERSION + ".EntityPlayer" );
            Class<?> entityClass = Class.forName( "net.minecraft.server." + VERSION + ".Entity" );
            Class<?> worldClass = Class.forName( "net.minecraft.server." + VERSION + ".World" );
            Class<?> worldDataClass = Class.forName( "net.minecraft.server." + VERSION + ".WorldData" );
            Class<?> playerInfoPacketClass = Class.forName( "net.minecraft.server." + VERSION + ".PacketPlayOutPlayerInfo" );
            Class<?> playerRespawnPacketClass = Class.forName( "net.minecraft.server." + VERSION + ".PacketPlayOutRespawn" );
            Class<?> worldTypeClass = Class.forName( "net.minecraft.server." + VERSION + ".WorldType" );
            Class<?> worldSettingsClass = Class.forName( "net.minecraft.server." + VERSION + ".WorldSettings" );
            Class<?> enumDifficultyClass = Class.forName( "net.minecraft.server." + VERSION + ".EnumDifficulty" );
            
            Field enumPlayerInfoActionField = playerInfoPacketClass.getDeclaredField( "a" );
            Field enumGamemode = worldSettingsClass.getDeclaredField( "b" );
            
            Class<?> type = enumPlayerInfoActionField.getType();
            Method valueOf = type.getDeclaredMethod( "valueOf", String.class );
            
            Object addPlayer = valueOf.invoke( null, "ADD_PLAYER" );
            Object removePlayer = valueOf.invoke( null, "REMOVE_PLAYER" );
            
            Method getById = enumGamemode.getType().getMethod( "getById", int.class );
            Method getHandle = craftPlayerClass.getMethod( "getHandle" );
            Method getProfile = craftPlayerClass.getMethod( "getProfile" );
            Method getWorld = entityClass.getMethod( "getWorld" );
            Method getDifficulty = worldClass.getMethod( "getDifficulty" );
            Method getWorldData = worldClass.getMethod( "getWorldData" );
            Method getType = worldDataClass.getMethod( "getType" );
            
            Object craftPlayer = craftPlayerClass.cast( player );
            Object entityPlayer = getHandle.invoke( craftPlayer );
            /*GameProfile gameProfile = (GameProfile) getProfile.invoke( craftPlayer );
            */
            Constructor<?> playerInfoConstructor = playerInfoPacketClass.getConstructor( enumPlayerInfoActionField.getType(), Array.newInstance( entityPlayerClass, 1 ).getClass() );
            Constructor<?> playerRespawnConstructor;
            
            if ( is13OrAbove() ) {
                Class<?> dimensionManagerClass = Class.forName( "net.minecraft.server." + VERSION + ".DimensionManager" );
                playerRespawnConstructor = playerRespawnPacketClass.getConstructor( dimensionManagerClass, enumDifficultyClass, worldTypeClass, enumGamemode.getType() );
            } else {
                playerRespawnConstructor = playerRespawnPacketClass.getConstructor( int.class, enumDifficultyClass, worldTypeClass, enumGamemode.getType() );
            }
            
            Object array = Array.newInstance( entityPlayerClass, 1 );
            Array.set( array, 0, entityPlayer );
            
            Object remove_player = playerInfoConstructor.newInstance( removePlayer, array );
            Object add_player = playerInfoConstructor.newInstance( addPlayer, array );
            Object world = getWorld.invoke( entityPlayer );
            Object respawnPacket;
            
            if ( is13OrAbove() ) {
                Class<?> dimensionManagerClass = Class.forName( "net.minecraft.server." + VERSION + ".DimensionManager" );
                Method a = dimensionManagerClass.getDeclaredMethod( "a", int.class );
                Object dimension = a.invoke( null, player.getWorld().getEnvironment().getId() );
                respawnPacket = playerRespawnConstructor.newInstance( dimension, getDifficulty.invoke( world ),
                        getType.invoke( getWorldData.invoke( world ) ), getById.invoke( null, player.getGameMode().getValue() ) );
            } else {
                respawnPacket = playerRespawnConstructor.newInstance( player.getWorld().getEnvironment().getId(), getDifficulty.invoke( world ),
                        getType.invoke( getWorldData.invoke( world ) ), getById.invoke( null, player.getGameMode().getValue() ) );
            }
            
            UUID uuid = UUIDFetcher.getUUID( name );
            final String skin = getSkin( uuid );
            final String signature = getSigniture( uuid );
            //final Property textures = new Property( "textures", skin, signature );
            final boolean canFly = player.getAllowFlight();
            
            //gameProfile.getProperties().removeAll( "textures" );
            //gameProfile.getProperties().put( "textures", textures );
            
            /*try {
                nickField.set( gameProfile, name );
            } catch ( IllegalAccessException var8 ) {
                var8.printStackTrace();
            }*/
            
            sendPacket( remove_player, player );
            sendPacket( add_player, player );
            sendPacket( respawnPacket, player );
            player.updateInventory();
            
            for ( Player player1 : Bukkit.getOnlinePlayers() ) {
                boolean canSee = player1.canSee( player );
                if ( !player1.hasPermission( "command.nick.bypass" ) ) {
                    player1.hidePlayer( player );
                    player1.showPlayer( player );
                }
                if ( !canSee ) player1.hidePlayer( player );
            }
            player.teleport( player );
            player.setAllowFlight( canFly );
            
        } catch ( ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchFieldException e ) {
            e.printStackTrace();
            return Futures.immediateFailedFuture( new Throwable() );
        }
        return service.submit( () -> name );
    }
    
    private static Field accessField( Class<?> clazz, String name ) {
        try {
            Field field = clazz.getDeclaredField( name );
            field.setAccessible( true );
            return field;
        } catch ( NoSuchFieldException | SecurityException e ) {
            return null;
        }
    }
    
    
    private static void sendPacket( Object packet, Player player ) {
        try {
            Class<?> packetClass = Class.forName( "net.minecraft.server." + VERSION + ".Packet" );
            if ( !packetClass.isInstance( packet ) ) return;
            Class<?> craftPlayerClass = Class.forName( "org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer" );
            Object craftPlayer = craftPlayerClass.cast( player );
            Method getHandle = craftPlayerClass.getMethod( "getHandle" );
            Object h = getHandle.invoke( craftPlayer );
            Field f1 = h.getClass().getDeclaredField( "playerConnection" );
            Object pc = f1.get( h );
            Method m5 = pc.getClass().getDeclaredMethod( "sendPacket", packetClass );
            m5.invoke( pc, packet );
        } catch ( ClassNotFoundException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }
    
    
}
