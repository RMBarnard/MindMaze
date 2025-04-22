package org.rbarnard.mindmaze;

import org.rbarnard.mindmaze.messaging.MessageHandlerRegistry;
import org.rbarnard.mindmaze.messaging.NewGameRequestHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MindMazeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GameRegistry.class).in(Singleton.class);
        bind(MessageHandlerRegistry.class).in(Singleton.class);
        bind(SessionRegistry.class).in(Singleton.class);
        bind(BroadcastService.class).in(Singleton.class);
        bind(NewGameRequestHandler.class).in(Singleton.class);
    }
}
