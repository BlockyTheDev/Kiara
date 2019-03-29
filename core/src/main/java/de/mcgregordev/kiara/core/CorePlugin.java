package de.mcgregordev.kiara.core;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {
    
    @Getter
    private static CorePlugin instance;
    private ModuleLoader moduleLoader = new ModuleLoader( getDataFolder().getAbsolutePath() + "/modules/" );
    
    @Override
    public void onEnable() {
        instance = this;
    }
    
    @Override
    public void onDisable() {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            module.onDisable();
        }
    }
    
}
