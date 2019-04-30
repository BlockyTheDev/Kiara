package de.mcgregordev.kiara.nick;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.nick.command.NickCommand;
import de.mcgregordev.kiara.nick.listener.PlayerJoinListener;

public class NickModule extends Module {
    
    @Override
    public void onEnable() {
        registerCommand( new NickCommand( "nick" ) );
        registerListener( new PlayerJoinListener() );
    }
    
    @Override
    public void onDisable() {
    
    }
    
}
