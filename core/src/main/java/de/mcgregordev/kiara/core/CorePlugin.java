package de.mcgregordev.kiara.core;

import de.mcgregordev.kiara.core.command.ModuleCommand;
import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.module.ModuleLoader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CorePlugin extends JavaPlugin {
    
    @Getter
    private static CorePlugin instance;
    private ModuleLoader moduleLoader;
    private CommandMap commandMap;

    @Override
    public void onEnable() {
        instance = this;
        moduleLoader = new ModuleLoader( getDataFolder().getAbsolutePath() + "/Modules/" );
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField( "commandMap" );
            bukkitCommandMap.setAccessible( true );
            commandMap = (CommandMap) bukkitCommandMap.get( Bukkit.getServer() );
        } catch ( NoSuchFieldException | IllegalAccessException e ) {
            e.printStackTrace();
        }
        registerCommand(new ModuleCommand());
    }
    
    @Override
    public void onDisable() {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            module.onDisable();
        }
    }


    protected void registerCommand( Command command ) {
        commandMap.register( command.getName(), command );
    }
    
}
