package DSA_Practice_in_Java.Graph;

// Topological Sort (DFS approach)

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class TopoSort {

    // Function that performs topological sort and returns the result as an array
    public static int[] topoSort(int v, List<List<Integer>> adj) {
        if (v <= 0 || adj == null) {
            return new int[0];
        }

        boolean[] visited = new boolean[v];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited, stack);
            }
        }

        // Convert stack to result array
        int[] result = new int[v];
        int idx = 0;
        while (!stack.isEmpty()) {
            result[idx++] = stack.pop();
        }

        return result;
    }

    private static void dfs(int node, List<List<Integer>> adj, boolean[] visited, Deque<Integer> stack) {
        visited[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, stack);
            }
        }

        stack.push(node);
    }

    public static void main(String[] args) {
        int v = 6;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        // Directed Acyclic Graph (DAG):
        // 5 -> 0, 5 -> 2
        // 4 -> 0, 4 -> 1
        // 2 -> 3
        // 3 -> 1
        adj.get(5).add(0);
        adj.get(5).add(2);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        // Calling the function directly
        int[] result = topoSort(v, adj);

        System.out.println("Topological Sort: " + Arrays.toString(result));
    }
}
