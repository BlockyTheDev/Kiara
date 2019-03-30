package de.mcgregordev.kiara.scoreboard.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ToggleScoreboardCommand extends Command {
    
    protected ToggleScoreboardCommand() {
        super( "togglescoreboard", "ich bin cool", "<>", Arrays.asList( "tsb" ) );
    }
    
    public boolean execute( CommandSender commandSender, String s, String[] strings ) {
        if ( !( commandSender instanceof Player ) ) return false;
        Player player = (Player) commandSender;
        
        return false;
    }
}
