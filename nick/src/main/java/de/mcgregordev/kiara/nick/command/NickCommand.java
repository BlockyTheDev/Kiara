package de.mcgregordev.kiara.nick.command;

import de.mcgregordev.kiara.nick.user.NickUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand extends Command {
    
    
    public NickCommand( String name ) {
        super( name );
        setPermission( "command.nick" );
    }
    
    @Override
    public boolean execute( CommandSender commandSender, String s, String[] strings ) {
        Player player = (Player) commandSender;
        NickUser nickUser = NickUser.getNickUser( player );
        nickUser.nick( strings[ 0 ] );
        return false;
    }
}
