package de.mcgregordev.kiara.core.module;

import de.mcgregordev.kiara.core.CorePlugin;
import de.mcgregordev.kiara.core.message.Message;
import de.mcgregordev.kiara.core.message.MessageStorage;
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
import java.nio.file.Path;

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
    private MessageStorage messageStorage;
    
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
        messageStorage = new MessageStorage(this);
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
    
    protected static <O> O getModule( String s ) {
        for ( Module module : moduleLoader.getLoadedModules() ) {
            if ( module.getName().equalsIgnoreCase( s ) ) {
                return (O) module;
            }
        }
        return null;
    }
    
    protected void registerCommand( Command command ) {
        commandMap.register( command.getName(), command );
    }
    
    protected void registerListener( Listener listener ) {
        Bukkit.getPluginManager().registerEvents( listener, CorePlugin.getInstance() );
    }
    
    public void registerListener( String path ) {
        try {
            for ( Class<?> aClass : Class.forName( path ).getClasses() ) {
                if ( aClass.isInstance( Listener.class ) ) {
                    registerListener( (Listener) aClass.newInstance() );
                }
            }
        } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e ) {
            e.printStackTrace();
        }
    }

    public void addMessage(String key, Message message) {
        messageStorage.addMessage(key, message);
    }


}
