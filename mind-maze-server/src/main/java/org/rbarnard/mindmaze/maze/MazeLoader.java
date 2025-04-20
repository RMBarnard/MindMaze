package org.rbarnard.mindmaze.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MazeLoader {
    public Maze loadMaze(String filename) throws IOException {
        List<List<Integer>> mazeArray = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/mazes/" + filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                List<Integer> row = new ArrayList<>();
                for (String space : splitLine) {
                    row.add(Integer.parseInt(space));
                }
                mazeArray.add(row);
            }
        } catch (IOException e) {
            throw e;
        }
        return new Maze(mazeArray);
    }
}
