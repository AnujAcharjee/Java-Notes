package DSA_Practice_in_Java.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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
    }
}
