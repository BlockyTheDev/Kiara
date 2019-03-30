package de.mcgregordev.kiara.core;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {
    
    @Getter
    private static CorePlugin instance;
    private ModuleLoader moduleLoader;
    
    @Override
    public void onEnable() {
        instance = this;
        moduleLoader = new ModuleLoader( getDataFolder().getAbsolutePath() + "/Modules/" );
    }
    
    @Override
    public void onDisable() {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            module.onDisable();
        }
    }
    
}
