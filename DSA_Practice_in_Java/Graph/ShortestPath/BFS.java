package DSA_Practice_in_Java.Graph.ShortestPath;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/*
 * =====================================================================================
 * ALGORITHM: Breadth-First Search (BFS) for Shortest Path
 * =====================================================================================
 * 
 * WHEN TO USE:
 *   - Use BFS for shortest path problems ONLY when all edges / steps have UNIFORM weights 
 *     (e.g., each move costs 1).
 *   - Guarantees that the first time a node is reached, it is reached via the shortest path.
 * 
 * TIME COMPLEXITY:  O(V + E) for graphs, O(rows * cols) for grids
 * SPACE COMPLEXITY: O(V) for graphs, O(rows * cols) for grids
 * =====================================================================================
 */

public class BFS {

    /**
     * Problem 1: Shortest path in an unweighted, undirected graph from source node.
     * 
     * @param V   Number of vertices (0 to V - 1)
     * @param adj Adjacency list
     * @param src Source vertex
     * @return int[] array where dist[i] is shortest distance from src to i (-1 if unreachable)
     */
    public static int[] shortestPathUnweighted(int V, List<List<Integer>> adj, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new ArrayDeque<>();
        dist[src] = 0;
        queue.offer(src);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int neighbor : adj.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    queue.offer(neighbor);
                }
            }
        }

        return dist;
    }

    /**
     * Problem 2: Shortest Path in Binary Matrix (LeetCode 1091 / Grid BFS).
     * Moves are allowed in 8 directions (horizontal, vertical, diagonal).
     * 0 represents an open cell, 1 represents an obstacle.
     * 
     * @param grid        2D binary matrix
     * @param src         Source cell coordinates [row, col]
     * @param destination Target destination cell coordinates [row, col]
     * @return Shortest path length (number of visited cells), or -1 if unreachable
     */
    public static int shortestPathBinaryMatrix(int[][] grid, int[] src, int[] destination) {
        if (grid == null || grid.length == 0 || grid[0].length == 0 ||
                src == null || destination == null || src.length < 2 || destination.length < 2) {
            return -1;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        int srcRow = src[0];
        int srcCol = src[1];
        int destRow = destination[0];
        int destCol = destination[1];

        // Boundary checks for source and destination
        if (srcRow < 0 || srcRow >= rows || srcCol < 0 || srcCol >= cols ||
                destRow < 0 || destRow >= rows || destCol < 0 || destCol >= cols) {
            return -1;
        }

        // Source or destination is blocked
        if (grid[srcRow][srcCol] == 1 || grid[destRow][destCol] == 1) {
            return -1;
        }

        // If source is already destination
        if (srcRow == destRow && srcCol == destCol) {
            return 1;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        int[][] dist = new int[rows][cols];

        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        dist[srcRow][srcCol] = 1;
        queue.offer(new int[] { srcRow, srcCol });

        int[] dRow = { 0, 0, 1, -1, 1, 1, -1, -1 };
        int[] dCol = { 1, -1, 0, 0, -1, 1, -1, 1 };

        while (!queue.isEmpty()) {
            int[] state = queue.poll();

            int row = state[0];
            int col = state[1];

            // Destination reached
            if (row == destRow && col == destCol) {
                return dist[row][col];
            }

            for (int direction = 0; direction < 8; direction++) {
                int nextRow = row + dRow[direction];
                int nextCol = col + dCol[direction];

                // Check boundaries
                if (nextRow < 0 || nextRow >= rows ||
                        nextCol < 0 || nextCol >= cols) {
                    continue;
                }

                // Skip blocked or visited cells
                if (grid[nextRow][nextCol] == 1 ||
                        dist[nextRow][nextCol] != -1) {
                    continue;
                }

                dist[nextRow][nextCol] = dist[row][col] + 1;

                // Early exit if destination reached
                if (nextRow == destRow && nextCol == destCol) {
                    return dist[nextRow][nextCol];
                }

                queue.offer(new int[] { nextRow, nextCol });
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        // 1. Unweighted Graph Demo
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(1).add(0);
        adj.get(0).add(2); adj.get(2).add(0);
        adj.get(1).add(3); adj.get(3).add(1);
        adj.get(2).add(4); adj.get(4).add(2);
        adj.get(3).add(4); adj.get(4).add(3);

        int[] graphDist = shortestPathUnweighted(V, adj, 0);
        System.out.println("BFS Graph Shortest Distances from 0: " + Arrays.toString(graphDist));

        // 2. Binary Matrix Demo
        int[][] binaryGrid = {
                { 0, 0, 0 },
                { 1, 1, 0 },
                { 1, 1, 0 }
        };
        int[] start = { 0, 0 };
        int[] end = { 2, 2 };
        int gridDist = shortestPathBinaryMatrix(binaryGrid, start, end);
        System.out.println("BFS Binary Matrix Shortest Path from " + Arrays.toString(start) + " to "
                + Arrays.toString(end) + ": " + gridDist);
    }
}

