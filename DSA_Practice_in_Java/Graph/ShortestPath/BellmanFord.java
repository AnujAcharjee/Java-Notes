                                                                                                                                                                                                                                                                                        package DSA_Practice_in_Java.Graph.ShortestPath;

import java.util.Arrays;

/*
 * =====================================================================================
 * ALGORITHM: Bellman-Ford Algorithm (Single-Source Shortest Path with Negative Weights)
 * =====================================================================================
 * 
 * WHY USE BELLMAN-FORD OVER DIJKSTRA?
 *   1. Dijkstra FAILS when there are negative edge weights (or infinite loops in negative cycles).
 *   2. Bellman-Ford works with NEGATIVE WEIGHTS and can DETECT NEGATIVE CYCLES.
 * 
 * CORE CONCEPT:
 *   - In a graph of V vertices, any simple shortest path has AT MOST (V - 1) edges.
 *   - Step 1: Relax ALL edges (V - 1) times.
 *   - Step 2 (Negative Cycle Detection):
 *       Relax all edges one more time (V-th time). If any distance is still reduced,
 *       a NEGATIVE WEIGHT CYCLE exists (shortest path is undefined / -infinity).
 * 
 * TIME COMPLEXITY:  O(V * E)
 * SPACE COMPLEXITY: O(V)
 * =====================================================================================
 */

public class BellmanFord {

    /**
     * Computes the shortest distance from src to all vertices using Bellman-Ford.
     * Detects negative weight cycles.
     * 
     * @param V     Number of vertices (0 to V - 1)
     * @param edges 2D array where each element is [u, v, weight] (directed edge u -> v)
     * @param src   Starting source vertex
     * @return int[] of shortest distances, or an array containing [-1] if a negative cycle is detected
     */
    public static int[] bellmanFord(int V, int[][] edges, int src) {
        int[] dist = new int[V];
        // Use 1e8 / large value to avoid integer overflow when adding edge weight
        int INF = (int) 1e8;
        Arrays.fill(dist, INF);
        dist[src] = 0;

        // Step 1: Relax all edges (V - 1) times
        for (int i = 0; i < V - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];

                if (dist[u] != INF && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }

        // Step 2: N-th relaxation to check for negative weight cycles
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            if (dist[u] != INF && dist[u] + weight < dist[v]) {
                // Negative cycle detected!
                return new int[] { -1 };
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        // Graph with negative weights (No negative cycle)
        int V1 = 6;
        int[][] edges1 = {
                { 0, 1, 5 },
                { 1, 2, -2 },
                { 1, 5, -3 },
                { 2, 4, 3 },
                { 3, 2, 6 },
                { 3, 4, -2 },
                { 5, 3, 1 }
        };
        int[] result1 = bellmanFord(V1, edges1, 0);
        System.out.println("Bellman-Ford Shortest Distances from 0: " + Arrays.toString(result1));

        // Graph WITH a negative cycle (1 -> 2 -> 0 -> 1 with sum < 0)
        int V2 = 3;
        int[][] edges2 = {
                { 0, 1, 1 },
                { 1, 2, -4 },
                { 2, 0, 2 } // Cycle sum: 1 + (-4) + 2 = -1 < 0
        };
        int[] result2 = bellmanFord(V2, edges2, 0);
        System.out.println("Negative cycle test (Expected [-1]): " + Arrays.toString(result2));
    }
}

