package de.mcgregordev.kiara.scoreboard;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.scoreboard.listener.PlayerJoinListener;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ScoreboardModule extends Module {
    
    private String scoreboardTitle;
    private List<String> scoreboardContent;
    
    @Override
    public void onEnable() {
        getConfig().addDefault( "moduleScoreboard", true );
        getConfig().addDefault( "use-rank-module", true );
        getConfig().addDefault( "scoreboard-title", "&4&l%name%" );
        getConfig().addDefault( "scoreboard", Arrays.asList( "test1", "test2" ) );
        getConfig().options().copyDefaults( true );
        saveConfig();
        scoreboardTitle = getConfig().getString( "scoreboard-title" );
        scoreboardContent = getConfig().getStringList( "scoreboard" );
        registerListener( new PlayerJoinListener( this ) );
    }
    
    
}
