package de.mcgregordev.kiara.core.module;

import de.mcgregordev.kiara.core.storage.MessageStorage;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Getter
public class ModuleLoader {
    
    @Getter
    private static ModuleLoader instance;
    
    private Yaml yaml = new Yaml();
    private String path;
    private Map<String, Module> toLoad = new HashMap<>();
    private List<Module> loadedModules = new ArrayList<>();
    
    public ModuleLoader( String path ) {
        long l = System.currentTimeMillis();
        instance = this;
        this.path = path;
        new File( path ).mkdirs();
        loadModulesFromFolder();
        while ( !toLoad.isEmpty() ) {
            List<Module> toRemove = new ArrayList<>();
            for ( Module module : toLoad.values() ) {
                for ( String s : module.getDependencies() ) {
                    if ( toLoad.containsKey( s ) ) continue;
                }
                module.onEnable();
                loadedModules.add( module );
                toRemove.add( module );
            }
            for ( Module module : toRemove ) {
                toLoad.remove( module.getName() );
            }
        }
        System.out.println( "loaded " + loadedModules.size() + " modules in " + ( System.currentTimeMillis() - l ) + "ms" );
    }
    
    private void loadModulesFromFolder() {
        for ( File file : Objects.requireNonNull( new File( path ).listFiles() ) ) {
            if ( file.getName().endsWith( ".jar" ) ) {
                try {
                    ZipFile javaFile = new ZipFile( file );
                    ZipEntry entry = javaFile.getEntry( "module.yml" );
                    FileConfiguration config = YamlConfiguration.loadConfiguration( new InputStreamReader( javaFile.getInputStream( entry ) ) );
                    String main = config.getString( "main" );
                    Module module = loadIntoRuntime( file, main );
                    module.setName( config.getString( "name" ) );
                    module.setAuthor( config.getString( "author" ) );
                    module.setVersion( config.getDouble( "version" ) );
                    module.setDependencies( config.getStringList( "dependencies" ).toArray( new String[ 0 ] ) );
                    module.setFile( file.getParentFile() );
                    module.setMain( main );
                    module.onLoad();
                    module.setupConfig();
                    module.setMessageStorage( new MessageStorage( module ) );
                    toLoad.put( module.getName(), module );
                    javaFile.close();
                } catch ( IOException | IllegalAccessException | ClassNotFoundException | InstantiationException | NoSuchMethodException | InvocationTargetException e ) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Module loadIntoRuntime( File file, String mainClass ) throws NoSuchMethodException, MalformedURLException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        URLClassLoader loader = (URLClassLoader) ModuleLoader.class.getClassLoader();
        Method addURL = URLClassLoader.class.getDeclaredMethod( "addURL", URL.class );
        addURL.setAccessible( true );
        addURL.invoke( loader, file.toURI().toURL() );
        Class<?> main = loader.loadClass( mainClass );
        return (Module) main.getConstructor().newInstance();
    }
    
}
