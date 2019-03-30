package de.mcgregordev.kiara.debug;

import de.mcgregordev.kiara.core.module.Module;
import de.mcgregordev.kiara.test.TestModule;

public class DebugModule extends Module {
    
    @Override
    public void onEnable() {
        System.out.println( "loading debug" );
        TestModule module = getModule( "test" );
        System.out.println( "testmodule loaded? " + ( module != null ) );
    }
}
