package de.mcgregordev.kiara.debug;

import com.google.common.reflect.ClassPath;

import java.io.IOException;

public class DebugModule {
    
    public static void main( String[] args ) throws IOException {
        for ( ClassPath.ClassInfo classInfo : ClassPath.from( ClassLoader.getSystemClassLoader() ).getAllClasses() ) {
            System.out.println( classInfo.getName() );
        }
    }
    
}
