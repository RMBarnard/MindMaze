package org.rbarnard.mindmaze.maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MazeLoader {
    private final String mazesBaseFolder;

    public MazeLoader(String mazesBaseFolder) {
        this.mazesBaseFolder = mazesBaseFolder;
    }

    public Maze loadMaze(String filename) throws IOException {
        Path filepath = Paths.get(mazesBaseFolder, filename);
        List<List<Integer>> mazeArray = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath.toString()))) {
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
