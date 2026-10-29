package DSA_Practice_in_Java.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * =====================================================================================
 * PROBLEM: Dijkstra's Algorithm (Single-Source Shortest Path & Path Reconstruction)
 * =====================================================================================
 * 
 * Given:
 *   - A weighted, directed or undirected graph with V vertices (0 to V-1).
 *   - Non-negative edge weights.
 *   - A source vertex `src`.
 * 
 * Goals:
 *   1. Find the shortest distance from `src` to all other vertices (`dijkstra`).
 *   2. Reconstruct and return the actual shortest path using a `parent[]` array
 *      (`findShortestPath`).
 * 
 * -------------------------------------------------------------------------------------
 * CORE CONCEPT:
 *   - Uses a Greedy approach + Min-Heap (PriorityQueue).
 *   - Distance to source = 0, all other distances = infinity (Integer.MAX_VALUE).
 *   - Relaxation condition:
 *       if (dist[u] + weight < dist[v]) {
 *           dist[v] = dist[u] + weight;
 *           parent[v] = u; // Track parent to reconstruct the path
 *           pq.offer(new Pair(v, dist[v]));
 *       }
 * 
 * Time Complexity:  O((V + E) * log V)
 * Space Complexity: O(V + E)
 * =====================================================================================
 */

public class Dijkstra {

    // Helper class to represent a node and its distance / edge weight
    public static class Pair implements Comparable<Pair> {
        public int node;
        public int distance;

        public Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        // Compare based on distance for Min-Heap (PriorityQueue)
        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    /**
     * Finds shortest distances from source node to all other nodes.
     * 
     * @param V   Number of vertices (0 to V - 1)
     * @param adj Adjacency list: adj.get(u) contains Pairs of (neighborNode, weight)
     * @param src Starting source vertex
     * @return int[] array where result[i] is shortest distance from src to i
     */
    public static int[] dijkstra(int V, List<List<Pair>> adj, int src) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        /**
         * In DSA simple array is used for Pair and This is how min heap is implemented
         * 
         * PriorityQueue<long[]> minHeap = new PriorityQueue<>(
         *      (a,b) -> Long.compare(a[0], b[0])
         * );
         * 
         */

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Distance from source to itself is 0: Pair(node, distance)
        dist[src] = 0;
        pq.offer(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair state = pq.poll();
            int n = state.node;
            int d = state.distance;

            // If we found a shorter path to n already, skip this stale entry
            if (d > dist[n])
                continue;

            // Traverse adjacent vertices
            for (Pair x : adj.get(n)) {
                int v = x.node;
                int weight = x.distance;

                // Relaxation step
                if (d + weight < dist[v]) {
                    dist[v] = d + weight;
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }

        return dist;
    }

    /**
     * Reconstructs the shortest path from `src` to `dest`.
     * 
     * @param V    Number of vertices (0 to V - 1)
     * @param adj  Adjacency list: adj.get(u) contains Pairs of (neighborNode, weight)
     * @param src  Starting source vertex
     * @param dest Target destination vertex
     * @return List where the first element is the total weight, followed by the nodes
     *         on the path: [totalWeight, src, ..., dest]. Returns [-1] if unreachable.
     */
    public static List<Integer> findShortestPath(int V, List<List<Pair>> adj, int src, int dest) {
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // parent[i] stores the predecessor of vertex i on the shortest path
        int[] parent = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
        }

        // Distance from source to itself is 0
        dist[src] = 0;
        minHeap.offer(new Pair(src, 0));

        while (!minHeap.isEmpty()) {
            Pair state = minHeap.poll();
            int u = state.node;
            int d = state.distance;

            if (d > dist[u])
                continue;

            for (Pair x : adj.get(u)) {
                int v = x.node;
                int weight = x.distance;

                // Relaxation step
                if (d + weight < dist[v]) {
                    dist[v] = d + weight;
                    parent[v] = u; // Record parent
                    minHeap.offer(new Pair(v, dist[v]));
                }
            }
        }

        // If destination is unreachable
        if (dist[dest] == Integer.MAX_VALUE) {
            return Arrays.asList(-1);
        }

        // Backtrack from `dest` back to `src` using parent array
        List<Integer> path = new ArrayList<>();
        int currNode = dest;
        while (parent[currNode] != currNode) {
            path.add(currNode);
            currNode = parent[currNode];
        }
        path.add(src); // Add source node

        // Reverse the path to get order: [src, ..., dest]
        Collections.reverse(path);

        // Result format: first element is total weight, then the vertices
        List<Integer> result = new ArrayList<>();
        result.add(dist[dest]);
        result.addAll(path);
        return result;
    }

    /**
     * Finds the shortest path in a binary matrix from source to destination.
     * Moves are allowed in 8 directions (horizontal, vertical, diagonal).
     * 0 represents an open/traversable cell, 1 represents an obstacle.
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

        // Source or destination is blocked.
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

            // Destination reached.
            if (row == destRow && col == destCol) {
                return dist[row][col];
            }

            for (int direction = 0; direction < 8; direction++) {
                int nextRow = row + dRow[direction];
                int nextCol = col + dCol[direction];

                // Check boundaries.
                if (nextRow < 0 || nextRow >= rows ||
                        nextCol < 0 || nextCol >= cols) {
                    continue;
                }

                // Skip blocked or visited cells.
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
        int V = 6;
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Sample Weighted Graph:
        // Edge (u, v, weight)
        // 0 -> 1 (wt 4), 0 -> 2 (wt 4)
        // 1 -> 2 (wt 2)
        // 2 -> 3 (wt 3), 2 -> 4 (wt 1), 2 -> 5 (wt 6)
        // 3 -> 5 (wt 2)
        // 4 -> 5 (wt 3)
        adj.get(0).add(new Pair(1, 4));
        adj.get(0).add(new Pair(2, 4));

        adj.get(1).add(new Pair(2, 2));

        adj.get(2).add(new Pair(3, 3));
        adj.get(2).add(new Pair(4, 1));
        adj.get(2).add(new Pair(5, 6));

        adj.get(3).add(new Pair(5, 2));

        adj.get(4).add(new Pair(5, 3));

        int src = 0;

        // 1. All-pairs shortest distances from src
        int[] result = dijkstra(V, adj, src);
        System.out.println("Shortest distances from source node " + src + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("Node " + i + " : " + (result[i] == Integer.MAX_VALUE ? "INF" : result[i]));
        }

        // 2. Reconstructing shortest path from 0 to 5
        System.out.println("\nShortest path from 0 to 5:");
        List<Integer> pathResult = findShortestPath(V, adj, 0, V - 1);
        System.out.println("Result (totalWeight, ...path): " + pathResult);

        // 3. Shortest path in binary matrix using int[] coordinates
        int[][] binaryGrid = {
            { 0, 0, 0 },
            { 1, 1, 0 },
            { 1, 1, 0 }
        };
        int[] start = { 0, 0 };
        int[] end = { 2, 2 };
        int pathLen = shortestPathBinaryMatrix(binaryGrid, start, end);
        System.out.println("\nShortest path in binary matrix from " + Arrays.toString(start) + " to " + Arrays.toString(end) + ": " + pathLen);
    }
}
