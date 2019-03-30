package de.mcgregordev.kiara.scoreboard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ScoerboardUpdateEvent extends PlayerEvent {
    
    public ScoerboardUpdateEvent( Player who ) {
        super( who );
    }
    
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
