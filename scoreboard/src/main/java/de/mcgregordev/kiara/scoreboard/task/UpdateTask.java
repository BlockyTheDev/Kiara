package de.mcgregordev.kiara.scoreboard.task;

import de.mcgregordev.kiara.scoreboard.ScoreboardModule;
import de.mcgregordev.kiara.scoreboard.scoreboard.SidebarBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UpdateTask implements Runnable {
    
    private ScoreboardModule module;
    
    public UpdateTask( ScoreboardModule module ) {
        this.module = module;
    }
    
    @Override
    public void run() {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            String[] lines = replaceVariables( player, module.getScoreboardContent() ).toArray( new String[ 0 ] );
            String title = replaceVariables( player, module.getScoreboardTitle() );
            new SidebarBuilder().add( lines ).setTitle( title ).send( player );
        }
    }
    
    private String replaceVariables( Player player, String string ) {
        return ChatColor.translateAlternateColorCodes( '&', module.getVariableStorage().translate( player, string ) );
    }
    
    private List<String> replaceVariables( Player player, List<String> list ) {
        List<String> newList = new ArrayList<>();
        for ( String s : list ) {
            newList.add( replaceVariables( player, s ) );
        }
        return newList;
    }
    
}
