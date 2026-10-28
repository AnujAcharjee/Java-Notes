package DSA_Practice_in_Java.Graph;

import java.util.Arrays;

/*
 * =====================================================================================
 * PROBLEM: Flood Fill Algorithm (LeetCode 733 / GFG)
 * =====================================================================================
 * 
 * WHAT THIS ALGORITHM DOES:
 * Flood Fill is the algorithm behind the "Bucket Fill / Paint Bucket" tool in paint programs.
 * 
 * Given:
 *   1. A 2D grid `image` where image[r][c] represents the color of pixel at (r, c).
 *   2. A starting coordinate (sr, sc).
 *   3. A `newColor`.
 * 
 * Goal:
 *   Change the color of the starting pixel and ALL 4-directionally adjacent pixels
 *   (up, down, left, right) that share the SAME original color as image[sr][sc]
 *   to `newColor`.
 * 
 * -------------------------------------------------------------------------------------
 * EXAMPLE:
 *   Input: 
 *     image = [
 *       [1, 1, 1],
 *       [1, 1, 0],
 *       [1, 0, 1]
 *     ]
 *     sr = 1, sc = 1, newColor = 2
 * 
 *   Explanation:
 *     - Starting pixel image[1][1] has original color = 1.
 *     - All 4-directionally connected pixels with color 1 are changed to 2:
 * 
 *   Output:
 *     [
 *       [2, 2, 2],
 *       [2, 2, 0],
 *       [2, 0, 1]
 *     ]
 *   (Note: The bottom-right pixel [2][2] is NOT 4-directionally connected to the filled area)
 * =====================================================================================
 */

public class FloodFill {

    int startingColor;

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // Can be solved using either of DFS / BFS

        this.startingColor = image[sr][sc];

        if (this.startingColor == newColor) {
            return image;
        }

        dfs(image, sr, sc, newColor);

        return image;
    }

    private void dfs(int[][] image, int row, int col, int newColor) {

        if (image[row][col] != startingColor)
            return;

        int m = image.length;
        int n = image[0].length;

        image[row][col] = newColor;

        /*
         * // Up
         * if (row > 0) {
         * dfs(image, row - 1, col, newColor);
         * }
         * 
         * // Down
         * if (row + 1 < m) {
         * dfs(image, row + 1, col, newColor);
         * }
         * 
         * // Left
         * if (col > 0) {
         * dfs(image, row, col - 1, newColor);
         * }
         * 
         * // Right
         * if (col + 1 < n) {
         * dfs(image, row, col + 1, newColor);
         * }
         */

        // Delta row and delta column concept for 4 directions (Up, Down, Left, Right)
        int[] dRow = { -1, 1, 0, 0 };
        int[] dCol = { 0, 0, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int nRow = row + dRow[i];
            int nCol = col + dCol[i];

            if (nRow >= 0 && nRow < m && nCol >= 0 && nCol < n) {
                dfs(image, nRow, nCol, newColor);
            }
        }
    }

    public static void main(String[] args) {
        FloodFill solver = new FloodFill();

        int[][] image = {
                { 1, 1, 1 },
                { 1, 1, 0 },
                { 1, 0, 1 }
        };

        int sr = 1, sc = 1, newColor = 2;

        System.out.println("Original Image:");
        printImage(image);

        int[][] result = solver.floodFill(image, sr, sc, newColor);

        System.out.println("\nAfter Flood Fill:");
        printImage(result);
    }

    private static void printImage(int[][] image) {
        for (int[] row : image) {
            System.out.println(Arrays.toString(row));
        }
    }
}
