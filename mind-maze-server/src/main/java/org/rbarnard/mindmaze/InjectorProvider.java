package org.rbarnard.mindmaze;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorProvider {
    private static final Injector injector;

    static {
        Injector tempInjector = null;
        try {
            tempInjector = Guice.createInjector(new MindMazeModule());
        } catch (Throwable t) {
            t.printStackTrace(); // THIS will show the real error
        }
        injector = tempInjector;
    }

    public static Injector getInjector() {
        return injector;
    }
}
