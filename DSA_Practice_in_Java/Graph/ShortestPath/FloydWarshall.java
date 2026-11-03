package DSA_Practice_in_Java.Graph.ShortestPath;

/*
 * =====================================================================================
 * ALGORITHM: Floyd-Warshall Algorithm (All-Pairs Shortest Path / Multi-Source)
 * =====================================================================================
 * 
 * WHEN TO USE:
 *   - Use Floyd-Warshall to find shortest paths between ALL pairs of vertices (i -> j).
 *   - Works on directed and weighted graphs (including negative weights).
 *   - Detects negative cycles: if distance from a vertex to itself becomes negative 
 *     (matrix[i][i] < 0), a negative weight cycle exists.
 * 
 * DYNAMIC PROGRAMMING RECURRENCE:
 *   For every intermediate node k from 0 to V - 1:
 *       matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j])
 * 
 * TIME COMPLEXITY:  O(V^3)
 * SPACE COMPLEXITY: O(1) in-place (O(V^2) input matrix)
 * =====================================================================================
 */

public class FloydWarshall {

    /**
     * In-place computes all-pairs shortest paths on adjacency matrix.
     * Matrix convention:
     *   - matrix[i][j] is the weight of edge i -> j.
     *   - matrix[i][j] = -1 (or 1e8) if there is no direct edge.
     *   - matrix[i][i] = 0.
     * 
     * @param matrix 2D adjacency matrix of size V x V
     */
    public static void floydWarshall(int[][] matrix) {
        int n = matrix.length;
        int INF = (int) 1e8;

        // Step 1: Replace -1 (unreachable) with a large sentinel INF
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1 && i != j) {
                    matrix[i][j] = INF;
                }
            }
        }

        // Step 2: Try every node k as an intermediate vertex
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][k] != INF && matrix[k][j] != INF) {
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
            }
        }

        // Step 3: Check for negative weight cycles (if cost from i to i < 0)
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] < 0) {
                System.out.println("Negative weight cycle detected involving node " + i);
            }
        }

        // Step 4: Revert unreachable cells back to -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= INF) {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
        // -1 represents infinity / no direct edge
        int[][] matrix = {
                { 0,   2,  -1,  -1 },
                { 1,   0,   3,  -1 },
                { -1, -1,   0,  -1 },
                { 3,   5,   4,   0 }
        };

        System.out.println("Original Cost Matrix:");
        printMatrix(matrix);

        floydWarshall(matrix);

        System.out.println("\nAll-Pairs Shortest Path Matrix:");
        printMatrix(matrix);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%5d", val);
            }
            System.out.println();
        }
    }
}

