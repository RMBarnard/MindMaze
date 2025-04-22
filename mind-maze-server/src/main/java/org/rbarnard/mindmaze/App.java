package org.rbarnard.mindmaze;

import java.io.IOException;

import org.rbarnard.mindmaze.GameServer;
import java.util.Scanner;
import org.glassfish.tyrus.server.Server;
import org.rbarnard.mindmaze.GameServer;

public class App {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/", null, GameServer.class);

        try {
            server.start();
            System.out.println("WebSocket server started at ws://localhost:8080/");

            System.out.println("Press Enter to stop the server...");
            new Scanner(System.in).nextLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
            System.out.println("WebSocket server stopped.");
        }
    }
}
