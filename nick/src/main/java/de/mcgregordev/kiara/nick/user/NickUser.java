package de.mcgregordev.kiara.nick.user;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.mcgregordev.kiara.nick.util.NickUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class NickUser {
    
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
    
    public void nick( String name ) {
        if ( isNicked ) name = realName;
        isNicked = !isNicked;
        Futures.addCallback( NickUtil.nickPlayer( player, name ), new FutureCallback<String>() {
            @Override
            public void onSuccess( @Nullable String name ) {
                player.sendMessage( "§aDu bist nun als §e" + name + " §agenickt" );
            }
            
            @Override
            public void onFailure( Throwable ignored ) {
                player.sendMessage( "§cEs ist irgendwas schief gelaufen" );
            }
        } );
        ;
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
