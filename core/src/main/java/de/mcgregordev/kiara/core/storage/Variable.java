package de.mcgregordev.kiara.core.storage;

import org.bukkit.entity.Player;

public interface Variable {
    
    /***
     * @return the string the variable should replaced to
     */
    String replace( Player player );
    
}
