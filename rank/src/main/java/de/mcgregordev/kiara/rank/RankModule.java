package de.mcgregordev.kiara.rank;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.rank.object.Rank;
import de.mcgregordev.kiara.scoreboard.ScoreboardModule;
import de.mcgregordev.kiara.scoreboard.storage.Variable;
import de.mcgregordev.kiara.scoreboard.storage.VariableStorage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class RankModule extends Module {
    
    private Map<String, Rank> ranks = new HashMap<>();
    
    @Override
    public void onEnable() {
        getConfig().addDefault( "ranks", new HashMap<String, Object>() {{
            put( "Admin", new Rank( "Admin", "&4Admin | ", " | suffix", "rank.admin", 0 ) );
            put( "Moderator", new Rank( "Moderator", "&cMod | ", " | suffix", "rank.moderator", 0 ) );
            put( "Default", new Rank( "Default", "&7Player | ", " | suffix", "", 10 ) );
        }} );
        getConfig().options().copyDefaults( true );
        saveConfig();
        System.out.println( getConfig() == null );
        getConfig().getConfigurationSection( "ranks" ).getValues( false ).forEach( ( s, o ) -> {
            ConfigurationSection configurationSection = getConfig().getConfigurationSection( "ranks." + s );
            String permission = configurationSection.getString( "permission" );
            String prefix = configurationSection.getString( "prefix" );
            String suffix = configurationSection.getString( "suffix" );
            int tabId = configurationSection.getInt( "tabId" );
            ranks.put( s, new Rank( s, ChatColor.translateAlternateColorCodes( '&', prefix ),
                    ChatColor.translateAlternateColorCodes( '&', suffix ), permission, tabId ) );
        } );
        this.ranks.forEach( ( s, rank ) -> {
            System.out.println( "loaded Rank " + rank.getName() );
        } );
    
        ScoreboardModule module = getModule( "Scoreboard" );
        if( module != null ) {
            VariableStorage variableStorage = module.getVariableStorage();
            variableStorage.addVariable( "rank", player -> getRank( player ).getName() );
            variableStorage.addVariable( "rankPrefix", player -> getRank( player ).getPrefix() );
            variableStorage.addVariable( "rankSuffix", player -> getRank( player ).getSuffix() );
        }
    }
    
    @Override
    public void onDisable() {
    
    }
    
    public Rank getRank( Player player ) {
        List<Rank> collect = ranks.values().stream().sorted( Comparator.comparing( Rank::getTabId ) ).collect( Collectors.toList() );
        for ( Rank rank : collect ) {
            if ( player.hasPermission( rank.getPermission() ) ) {
                return rank;
            }
        }
        return ranks.get( "Default" );
    }
    
}
