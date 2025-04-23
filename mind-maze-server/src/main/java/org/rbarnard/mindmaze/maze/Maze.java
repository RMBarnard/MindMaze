package org.rbarnard.mindmaze.maze;

import java.util.List;
import java.io.PrintWriter;
import java.lang.StringBuilder;

public class Maze {
    private List<List<Integer>> maze;
    private String size;

    public Maze(List<List<Integer>> maze, String size) {
        this.maze = maze;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void renderMaze(PrintWriter out) {
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
        out.print(sb.toString());
    }
}
