package de.mcgregordev.kiara.core.storage;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.core.storage.Variable;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class VariableStorage {
    
    private Module holder;
    
    private Map<String, Variable> variableMap = new HashMap<>();
    
    public VariableStorage( Module holder ) {
        this.holder = holder;
    }
    
    public void addVariable( String string, Variable variable ) {
        variableMap.put( string, variable );
    }
    
    public String translate( Player player, final String string ) {
        AtomicReference<String> translated = new AtomicReference<>( string );
        variableMap.forEach( ( s, variable ) -> {
            translated.set( translated.get().replace( "%" + s + "%", variable.replace( player ) ) );
        } );
        return translated.get();
    }
    
}
