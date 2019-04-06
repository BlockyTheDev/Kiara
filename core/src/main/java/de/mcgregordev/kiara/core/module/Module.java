package de.mcgregordev.kiara.core.module;

import de.mcgregordev.kiara.core.CorePlugin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Getter
@Setter(AccessLevel.PACKAGE)
public abstract class Module {
    
    private CorePlugin corePlugin;
    private static ModuleLoader moduleLoader = ModuleLoader.getInstance();
    private String name;
    private String author;
    private double version;
    private String[] dependencies;
    private File file;
    private String main;
    private CommandMap commandMap;
    private File dataFolder, configFile;
    private FileConfiguration config;
    
    public Module() {
        corePlugin = CorePlugin.getInstance();
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField( "commandMap" );
            bukkitCommandMap.setAccessible( true );
            commandMap = (CommandMap) bukkitCommandMap.get( Bukkit.getServer() );
        } catch ( NoSuchFieldException | IllegalAccessException e ) {
            e.printStackTrace();
        }
    }
    
    protected void setupConfig() {
        dataFolder = new File( file, name );
        dataFolder.mkdirs();
        
        System.out.println( dataFolder );
        
        configFile = new File( dataFolder, "config.yml" );
        try {
            configFile.createNewFile();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration( configFile );
    }
    
    public void saveConfig() {
        try {
            config.save( configFile );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
    
    public void onEnable() {
    
    }
    
    public void onDisable() {
    
    }
    
    public void onLoad() {
    
    }
    
    public static <O> O getModule( String s ) {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            if ( module.getName().equalsIgnoreCase( s ) ) {
                return (O) module;
            }
        }
        return null;
    }
    
    public void registerCommand( Command command ) {
        commandMap.register( command.getName(), command );
    }
    
    public void registerListener( Listener listener ) {
        Bukkit.getPluginManager().registerEvents( listener, CorePlugin.getInstance() );
    }
}
