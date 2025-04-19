package org.rbarnard.mindmaze.maze;

import java.util.List;
import java.lang.StringBuilder;

public class Maze {
    private List<List<Integer>> maze;

    public Maze(List<List<Integer>> maze) {
        this.maze = maze;
    }

    public void renderMaze() {
        System.out.println(maze);
        StringBuilder sb = new StringBuilder();
        for (List<Integer> row : maze) {
            for (Integer space : row) {
                if (space == 1) {
                    sb.append("##");
                } else if (space == 0) {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}
