package de.mcgregordev.kiara.nick;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.nick.command.NickCommand;
import de.mcgregordev.kiara.nick.listener.PlayerJoinListener;
import de.mcgregordev.kiara.nick.user.NickUser;

public class NickModule extends Module {
    
    @Override
    public void onEnable() {
        registerCommand( new NickCommand( "nick" ) );
        registerListener( new PlayerJoinListener() );
        NickUser.setModule(this);
    }
    
    @Override
    public void onDisable() {
    
    }
    
}
