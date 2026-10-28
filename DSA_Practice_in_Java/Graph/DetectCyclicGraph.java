package DSA_Practice_in_Java.Graph;

import java.util.ArrayList;
import java.util.List;

public class DetectCyclicGraph {

    // --- Undirected Graph Cycle Detection ---

    public boolean nonDirectedGraph(int v, List<List<Integer>> adj) {
        boolean[] visited = new boolean[v];

        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                if (dfsUndirected(i, -1, visited, adj)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsUndirected(int node, int parent, boolean[] visited, List<List<Integer>> adj) {
        visited[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                if (dfsUndirected(neighbor, node, visited, adj)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }

    // --- Directed Graph Cycle Detection ---

    public boolean directedGraph(int v, List<List<Integer>> adj) {
        boolean[] visited = new boolean[v];
        boolean[] pathVisited = new boolean[v];

        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                if (dfsDirected(i, visited, pathVisited, adj)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsDirected(int node, boolean[] visited, boolean[] pathVisited, List<List<Integer>> adj) {
        visited[node] = true;
        pathVisited[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                if (dfsDirected(neighbor, visited, pathVisited, adj)) {
                    return true;
                }
            } else if (pathVisited[neighbor]) {
                return true;
            }
        }

        pathVisited[node] = false;
        return false;
    }

    // --- Main & Tests ---

    private static void printGraph(List<List<Integer>> adj, boolean isDirected) {
        String arrow = isDirected ? " -> " : " -- ";
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("  Node " + i + arrow + adj.get(i));
        }
    }

    public static void main(String[] args) {
        DetectCyclicGraph detector = new DetectCyclicGraph();

        System.out.println("==================================================");
        System.out.println("1. UNDIRECTED GRAPH TESTS");
        System.out.println("==================================================");

        // Test 1A: Undirected with cycle (1-2-3-1)
        int v1 = 4;
        List<List<Integer>> uCycle = new ArrayList<>();
        for (int i = 0; i < v1; i++) uCycle.add(new ArrayList<>());
        uCycle.get(1).add(2); uCycle.get(2).add(1);
        uCycle.get(2).add(3); uCycle.get(3).add(2);
        uCycle.get(3).add(1); uCycle.get(1).add(3);

        System.out.println("Graph 1A (Has Cycle 1-2-3-1):");
        printGraph(uCycle, false);
        System.out.println("Has Cycle? Expected: true  | Actual: " + detector.nonDirectedGraph(v1, uCycle));
        System.out.println();

        // Test 1B: Undirected tree without cycle (0-1, 1-2, 1-3)
        int v2 = 4;
        List<List<Integer>> uAcyclic = new ArrayList<>();
        for (int i = 0; i < v2; i++) uAcyclic.add(new ArrayList<>());
        uAcyclic.get(0).add(1); uAcyclic.get(1).add(0);
        uAcyclic.get(1).add(2); uAcyclic.get(2).add(1);
        uAcyclic.get(1).add(3); uAcyclic.get(3).add(1);

        System.out.println("Graph 1B (Tree - No Cycle):");
        printGraph(uAcyclic, false);
        System.out.println("Has Cycle? Expected: false | Actual: " + detector.nonDirectedGraph(v2, uAcyclic));

        System.out.println("\n==================================================");
        System.out.println("2. DIRECTED GRAPH TESTS");
        System.out.println("==================================================");

        // Test 2A: Directed with cycle (1 -> 2 -> 3 -> 1)
        int v3 = 4;
        List<List<Integer>> dCycle = new ArrayList<>();
        for (int i = 0; i < v3; i++) dCycle.add(new ArrayList<>());
        dCycle.get(1).add(2);
        dCycle.get(2).add(3);
        dCycle.get(3).add(1);

        System.out.println("Graph 2A (Has Cycle 1->2->3->1):");
        printGraph(dCycle, true);
        System.out.println("Has Cycle? Expected: true  | Actual: " + detector.directedGraph(v3, dCycle));
        System.out.println();

        // Test 2B: Directed Acyclic Graph / DAG (1 -> 2, 1 -> 3, 2 -> 3)
        int v4 = 4;
        List<List<Integer>> dDAG = new ArrayList<>();
        for (int i = 0; i < v4; i++) dDAG.add(new ArrayList<>());
        dDAG.get(1).add(2);
        dDAG.get(1).add(3);
        dDAG.get(2).add(3);

        System.out.println("Graph 2B (DAG - No Cycle):");
        printGraph(dDAG, true);
        System.out.println("Has Cycle? Expected: false | Actual: " + detector.directedGraph(v4, dDAG));
        System.out.println("==================================================");
    }
}
