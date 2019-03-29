package de.mcgregordev.kiara.core.module;

import de.mcgregordev.kiara.core.CorePlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

import java.io.File;
import java.lang.reflect.Field;

@Getter
public abstract class Module {
    
    private CorePlugin corePlugin = CorePlugin.getInstance();
    private ModuleLoader moduleLoader = ModuleLoader.getInstance();
    
    private CommandMap commandMap;
    
    public Module() {
        final Field bukkitCommandMap;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField( "commandMap" );
            bukkitCommandMap.setAccessible( true );
            commandMap = (CommandMap) bukkitCommandMap.get( Bukkit.getServer() );
        } catch ( NoSuchFieldException | IllegalAccessException e ) {
            e.printStackTrace();
        }
        
    }
    
    private String name;
    private String author;
    private double version;
    private String[] dependencies;
    private File file;
    private String main;
    
    public void onEnable() {
    
    }
    
    public void onDisable() {
    
    }
    
    public void onLoad() {
    
    }
    
    public <O> O getModule( String s ) {
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
        Bukkit.getPluginManager().registerEvents( listener, corePlugin );
    }
    
    
    void setName( String name ) {
        this.name = name;
    }
    
    void setAuthor( String author ) {
        this.author = author;
    }
    
    void setVersion( double version ) {
        this.version = version;
    }
    
    void setDependencies( String[] dependencies ) {
        this.dependencies = dependencies;
    }
    
    void setMain( String main ) {
        this.main = main;
    }
    
    void setFile( File file ) {
        this.file = file;
    }
}
