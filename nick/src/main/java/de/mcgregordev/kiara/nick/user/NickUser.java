package de.mcgregordev.kiara.nick.user;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.mcgregordev.kiara.nick.NickModule;
import de.mcgregordev.kiara.nick.util.NickUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class NickUser {
    
    @Setter
    private static NickModule module;
    
    @Getter
    private static Map<UUID, NickUser> cache = new HashMap<>();
    
    public static NickUser getNickUser( Player player ) {
        return cache.getOrDefault( player.getUniqueId(), new NickUser( player ) );
    }
    
    private Player player;
    private UUID uuid;
    private String realName;
    private boolean isNicked;
    
    public NickUser( Player player ) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.realName = player.getName();
        this.isNicked = false;
        cache.put( player.getUniqueId(), this );
    }
    
    public void nick() {
        List<String> nickNames = new ArrayList<>( module.getNickNames() );
        nickNames.removeAll( module.getInUse() );
        int i = ThreadLocalRandom.current().nextInt( module.getNickNames().size() - 1 );
        String name = nickNames.get( i ), nickName = "";
        if ( isNicked ) {
            nickName = player.getName();
            name = realName;
        }
        isNicked = !isNicked;
        String nickNameF = nickName;
        Futures.addCallback( NickUtil.nickPlayer( player, name ), new FutureCallback<String>() {
            @Override
            public void onSuccess( String name ) {
                player.sendMessage( module.getMessageStorage().getMessage( "command.nick.success", name ) );
                if ( isNicked ) {
                    module.getInUse().add( name );
                } else {
                    module.getInUse().remove( nickNameF );
                }
            }
            
            @Override
            public void onFailure( Throwable ignored ) {
                player.sendMessage( module.getMessageStorage().getMessage( "command.nick.error" ) );
            }
        } );
    }
    
    public String getSeeName( Player player ) {
        if ( player.hasPermission( "command.nick.bypass" ) ) {
            return realName;
        }
        return player.getName();
    }
    
    //TODO: with rank module
    public String getRankName( Player player ) {
        return null;
    }
    
}
