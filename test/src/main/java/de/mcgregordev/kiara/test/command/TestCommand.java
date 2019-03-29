package de.mcgregordev.kiara.test.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TestCommand extends Command {
    
    public TestCommand() {
        super( "test" );
    }
    
    public boolean execute( CommandSender commandSender, String s, String[] strings ) {
        commandSender.sendMessage( "hallo!" );
        return false;
    }
    
}
