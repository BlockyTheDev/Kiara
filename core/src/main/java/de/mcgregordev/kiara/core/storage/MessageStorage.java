package de.mcgregordev.kiara.core.storage;

import de.mcgregordev.kiara.core.module.Module;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MessageStorage {
    
    private Module module;
    private YamlConfiguration messageConfig;
    private File file;
    private Map<String, Message> messageMap = new HashMap<>();
    
    public MessageStorage( Module module ) {
        this.module = module;
        file = new File( module.getDataFolder(), "messages.yml" );
        
        try {
            file.createNewFile();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        messageConfig = YamlConfiguration.loadConfiguration( file );
        try {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration( getFileFromResources( "messages.yml" ) );
            defaultConfig.getValues( true ).forEach( ( s, o ) -> {
                        System.out.println( s );
                        System.out.println( o );
                        if ( o instanceof String ) {
                            messageConfig.addDefault( s, o );
                        }
                    }
            );
        } catch ( Exception ignored ) {
            ignored.printStackTrace();
        }
        
        messageConfig.options().copyDefaults( true );
        try {
            messageConfig.save( file );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        
        messageConfig.getValues( true ).forEach( ( s, o ) -> {
            if ( o instanceof String ) {
                messageMap.put( s, new Message( s, (String) o ) );
            }
        } );
    }
    
    public void addMessage( String key, Message message ) {
        this.messageMap.put( key, message );
    }
    
    public String getMessage( String key, Object... arguments ) {
        return ChatColor.translateAlternateColorCodes( '&', messageMap.getOrDefault( key, new Message( key, "N/A (" + key + ")" ) ).translate( arguments ) );
    }
    
    private File getFileFromResources( String fileName ) throws IllegalArgumentException {
        
        ClassLoader classLoader = getClass().getClassLoader();
        
        URL resource = classLoader.getResource( fileName );
        if ( resource == null ) {
            throw new IllegalArgumentException( "file is not found!" );
        } else {
            return new File( resource.getFile() );
        }
        
    }
    
}
