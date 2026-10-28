package DSA_Practice_in_Java.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * =====================================================================================
 * PROBLEM: Number of Distinct Islands (LeetCode 694 / GFG)
 * =====================================================================================
 * 
 * An island is considered distinct if it cannot be formed by simply translating 
 * (shifting) another island. Rotations/reflections are considered different.
 * 
 * KEY IDEA:
 * - To check if two islands have the same shape, subtract the starting cell's
 *   coordinates (baseRow, baseCol) from every cell in that island:
 *       relativeRow = currentRow - baseRow
 *       relativeCol = currentCol - baseCol
 * - Store the sequence of relative coordinates in a List<String>.
 * - Add this list to a HashSet<List<String>>. Since Java's List compares by elements,
 *   islands with identical shapes will produce identical lists and get deduplicated!
 * =====================================================================================
 */

public class DistinctIslands {

    public int NumberOfDistinctIslands(int[][] adj) {
        if (adj == null || adj.length == 0)
            return 0;

        int m = adj.length;
        int n = adj[0].length;

        // Set to store unique island shapes
        Set<List<String>> set = new HashSet<>();
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (!visited[i][j] && adj[i][j] == 1) {
                    List<String> currentIsland = new ArrayList<>(); // list of pairs (coordinates)
                    dfs(adj, i, j, visited, currentIsland, i, j);
                    set.add(currentIsland);
                }
            }
        }

        return set.size();
    }

    private void dfs(int[][] adj, int row, int col, boolean[][] visited,
            List<String> currentIsland, int baseRow, int baseCol) {

        visited[row][col] = true;

        // Record the cell's position relative to the island's starting point
        currentIsland.add((row - baseRow) + " " + (col - baseCol));

        int m = adj.length;
        int n = adj[0].length;

        int[] dRow = { -1, 1, 0, 0 };
        int[] dCol = { 0, 0, -1, 1 };

        for (int d = 0; d < 4; d++) {
            int nRow = row + dRow[d];
            int nCol = col + dCol[d];

            if (nRow >= 0 && nRow < m && nCol >= 0 && nCol < n) {
                if (!visited[nRow][nCol] && adj[nRow][nCol] == 1) {
                    dfs(adj, nRow, nCol, visited, currentIsland, baseRow, baseCol);
                }
            }
        }
    }

    public static void main(String[] args) {
        DistinctIslands solver = new DistinctIslands();

        int[][] grid = {
                { 1, 1, 0, 1, 1 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1 },
                { 1, 1, 0, 1, 0 }
        };

        int count = solver.NumberOfDistinctIslands(grid);
        System.out.println("Number of distinct islands: " + count);
        // Expected output: 2
    }
}
