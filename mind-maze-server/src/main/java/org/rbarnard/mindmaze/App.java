package org.rbarnard.mindmaze;

import org.rbarnard.mindmaze.maze.Maze;
import org.rbarnard.mindmaze.maze.MazeLoader;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        new App().renderMenu();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "1":
                new GameServer().run();
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            default:
                break;
        }
    }

    public void renderMenu() {
        String menu = """
                1. New Game
                2. Settings
                3. Friends List
                4. Stats
                """;

        System.out.print(menu);
    }
}
