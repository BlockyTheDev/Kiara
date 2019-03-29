package de.mcgregordev.kiara.test;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.test.command.TestCommand;
import org.apache.commons.lang.ArrayUtils;

public class TestModule extends Module {
    
    @Override
    public void onEnable() {
        System.out.println( "Module " + getName() + " Version " + getVersion() + " by " + getAuthor() );
        System.out.println( "Required Modules: [" + getDependencies().length + "]: " + ArrayUtils.toString( getDependencies() ) );
        registerCommand( new TestCommand() );
    }
    
    @Override
    public void onDisable() {
    
    }
    
}
