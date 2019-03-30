package de.mcgregordev.kiara.scoreboard;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.scoreboard.listener.PlayerJoinListener;
import de.mcgregordev.kiara.scoreboard.storage.VariableStorage;
import de.mcgregordev.kiara.scoreboard.task.UpdateTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Getter
public class ScoreboardModule extends Module {
    
    private static ScoreboardModule instance;
    
    private String scoreboardTitle;
    private List<String> scoreboardContent;
    private boolean autoUpdate;
    private int updateInterval;
    private VariableStorage variableStorage;
    
    @Override
    public void onEnable() {
        instance = this;
        variableStorage = new VariableStorage( this );
        getConfig().addDefault( "moduleScoreboard", true );
        getConfig().addDefault( "use-rank-module", true );
        getConfig().addDefault( "auto-update", true );
        getConfig().addDefault( "update-interval", 10 );
        getConfig().addDefault( "scoreboard-title", "&4&l%name%" );
        getConfig().addDefault( "scoreboard", Arrays.asList( "test1", "test2" ) );
        getConfig().options().copyDefaults( true );
        saveConfig();
        scoreboardTitle = getConfig().getString( "scoreboard-title" );
        scoreboardContent = getConfig().getStringList( "scoreboard" );
        autoUpdate = getConfig().getBoolean( "auto-update" );
        updateInterval = getConfig().getInt( "update-interval" );
        registerListener( new PlayerJoinListener( this ) );
        registerVariables();
        if ( autoUpdate ) {
            Bukkit.getScheduler().runTaskTimerAsynchronously( getCorePlugin(), new UpdateTask( this ), 0, 20 * updateInterval );
        }
    }
    
    private void registerVariables() {
        
        variableStorage.addVariable( "name", HumanEntity::getName );
        variableStorage.addVariable( "displayName", Player::getDisplayName );
        variableStorage.addVariable( "health", player -> String.valueOf( player.getHealth() ) );
        variableStorage.addVariable( "level", player -> String.valueOf( player.getLevel() ) );
        variableStorage.addVariable( "food", player -> String.valueOf( player.getFoodLevel() ) );
        
    }
    
    
}
