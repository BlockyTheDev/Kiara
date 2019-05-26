package de.mcgregordev.kiara.nick;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.nick.command.NickCommand;
import de.mcgregordev.kiara.nick.listener.PlayerJoinListener;
import de.mcgregordev.kiara.nick.user.NickUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class NickModule extends Module {
    
    private List<String> nickNames = new ArrayList<>(), inUse = new ArrayList<>();
    
    @Override
    public void onEnable() {
        registerCommand( new NickCommand( "nick" ) );
        registerListener( new PlayerJoinListener() );
        NickUser.setModule( this );
        getConfig().addDefault( "nicknames", Arrays.asList( "Lango", "ungespielt", "GommeHD" ) );
        getConfig().options().copyDefaults( true );
        saveConfig();
        nickNames = getConfig().getStringList( "nicknames" );
        System.out.println( getMessageStorage().getFile() );
    }
    
    @Override
    public void onDisable() {
    
    }
    
}
