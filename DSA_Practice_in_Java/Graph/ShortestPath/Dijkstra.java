package DSA_Practice_in_Java.Graph.ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
 * =====================================================================================
 * ALGORITHM: Dijkstra's Algorithm (Single-Source Shortest Path)
 * =====================================================================================
 * 
 * WHEN TO USE:
 *   - Use Dijkstra when edges have DIFFERENT / NON-NEGATIVE weights.
 *   - Uses a Greedy approach with a Min-Heap (PriorityQueue) to always process the 
 *     vertex with the smallest accumulated distance.
 *   - Cannot handle negative weight edges (use Bellman-Ford instead).
 * 
 * RELAXATION:
 *   if (dist[u] + weight < dist[v]) {
 *       dist[v] = dist[u] + weight;
 *       pq.offer(new Pair(v, dist[v]));
 *   }
 * 
 * TIME COMPLEXITY:  O((V + E) * log V)
 * SPACE COMPLEXITY: O(V + E)
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

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    /**
     * Problem 1: Finds shortest distances from source node to all other nodes.
     * 
     * @param V   Number of vertices (0 to V - 1)
     * @param adj Adjacency list: adj.get(u) contains Pairs of (neighborNode, weight)
     * @param src Starting source vertex
     * @return int[] array where result[i] is shortest distance from src to i
     */
    public static int[] dijkstra(int V, List<List<Pair>> adj, int src) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[src] = 0;
        pq.offer(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair state = pq.poll();
            int n = state.node;
            int d = state.distance;

            // If we found a shorter path to n already, skip this stale entry
            if (d > dist[n])
                continue;

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
     * Problem 2: Reconstructs the shortest path from `src` to `dest`.
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

        Collections.reverse(path);

        List<Integer> result = new ArrayList<>();
        result.add(dist[dest]);
        result.addAll(path);
        return result;
    }

    /**
     * Problem 3: Path with Minimum Effort (LeetCode 1631).
     * Effort is the maximum absolute difference in heights between two consecutive cells.
     * 
     * @param heights 2D grid representing heights
     * @return Minimum effort required to travel from start (0, 0) to end (rows-1, cols-1)
     */
    public static int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[0], b[0]));

        int[][] diff = new int[rows][cols];
        for (int[] row : diff) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        diff[0][0] = 0;
        minHeap.offer(new int[] { 0, 0, 0 });

        int[] dRow = { 0, 0, 1, -1 };
        int[] dCol = { 1, -1, 0, 0 };

        while (!minHeap.isEmpty()) {
            int[] state = minHeap.poll();
            int curDiff = state[0];
            int curRow = state[1];
            int curCol = state[2];

            if (curDiff > diff[curRow][curCol]) {
                continue;
            }

            if (curRow == rows - 1 && curCol == cols - 1) {
                return curDiff;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = curRow + dRow[i];
                int newCol = curCol + dCol[i];

                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                    continue;
                }

                int edgeDiff = Math.abs(heights[newRow][newCol] - heights[curRow][curCol]);
                int newDiff = Math.max(curDiff, edgeDiff);

                if (newDiff < diff[newRow][newCol]) {
                    diff[newRow][newCol] = newDiff;
                    minHeap.offer(new int[] { newDiff, newRow, newCol });
                }
            }
        }

        return 0;
    }

    /**
     * Problem 4: Number of Ways to Arrive at Destination (LeetCode 1976).
     * Counts shortest paths modulo 10^9 + 7.
     * 
     * @param n     Number of intersections (nodes 0 to n - 1)
     * @param roads 2D array [u, v, time]
     * @return Number of shortest paths modulo 10^9 + 7
     */
    public static int numberOfWaysToArriveAtDestination(int n, int[][] roads) {
        int MOD = 1000000007; // Prevents overflow as path counts grow exponentially
        List<List<int[]>> adj = new ArrayList<>();

        for (int node = 0; node < n; node++) {
            adj.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int travelTime = road[2];
            adj.get(u).add(new int[] { v, travelTime });
            adj.get(v).add(new int[] { u, travelTime });
        }

        // Divided by 4 so (INF + travelTime) never overflows Long.MAX_VALUE into negative numbers
        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        int[] ways = new int[n];
        Arrays.fill(dist, INF);

        // Sorts primarily by distance (entry[0]); uses node ID (entry[1]) as tie-breaker
        PriorityQueue<long[]> minHeap = new PriorityQueue<>(
                Comparator.<long[]>comparingLong(entry -> entry[0])
                        .thenComparingLong(entry -> entry[1]));

        dist[0] = 0;
        ways[0] = 1;
        minHeap.offer(new long[] { 0, 0 });

        while (!minHeap.isEmpty()) {
            long[] entry = minHeap.poll();
            long currentDistance = entry[0];
            int node = (int) entry[1];

            if (currentDistance != dist[node]) {
                continue;
            }

            for (int[] road : adj.get(node)) {
                int neighbor = road[0];
                int travelTime = road[1];
                long candidate = currentDistance + travelTime;

                if (candidate < dist[neighbor]) {
                    dist[neighbor] = candidate;
                    ways[neighbor] = ways[node];
                    minHeap.offer(new long[] { candidate, neighbor });
                } else if (candidate == dist[neighbor]) {
                    ways[neighbor] = (int) (((long) ways[neighbor] + ways[node]) % MOD);
                }
            }
        }

        return ways[n - 1];
    }

    public static void main(String[] args) {
        // 1. Single-source shortest path demo
        int V = 6;
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        adj.get(0).add(new Pair(1, 4));
        adj.get(0).add(new Pair(2, 4));
        adj.get(1).add(new Pair(2, 2));
        adj.get(2).add(new Pair(3, 3));
        adj.get(2).add(new Pair(4, 1));
        adj.get(2).add(new Pair(5, 6));
        adj.get(3).add(new Pair(5, 2));
        adj.get(4).add(new Pair(5, 3));

        int[] result = dijkstra(V, adj, 0);
        System.out.println("Shortest distances from source node 0: " + Arrays.toString(result));

        // 2. Path reconstruction demo
        List<Integer> pathResult = findShortestPath(V, adj, 0, V - 1);
        System.out.println("Shortest path from 0 to 5: " + pathResult);

        // 3. Minimum Effort Path demo
        int[][] heights = {
                { 1, 2, 2 },
                { 3, 8, 2 },
                { 5, 3, 5 }
        };
        System.out.println("Minimum Effort Path: " + minimumEffortPath(heights));

        // 4. Number of Ways to Arrive at Destination demo
        int n = 7;
        int[][] roads = {
                { 0, 6, 7 }, { 0, 1, 2 }, { 1, 2, 3 }, { 1, 3, 3 }, { 6, 3, 3 },
                { 3, 5, 1 }, { 6, 5, 1 }, { 2, 5, 1 }, { 0, 4, 5 }, { 4, 6, 2 }
        };
        System.out.println("Number of Ways to Arrive at Destination: " + numberOfWaysToArriveAtDestination(n, roads));
    }
}
