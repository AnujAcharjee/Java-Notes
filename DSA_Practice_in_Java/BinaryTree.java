package DSA_Practice_in_Java;

import java.util.ArrayDeque;
import java.util.Queue;

// Binary Tree 
// PreOrderTraversal
// InOrderTraversal
// PostOrderTraversal / DFS
// LevelOrderTraversal / BFS

class Node {
    int data;
    Node left, right;
    int height;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1; // for AVL Tree implementation
    }
}

public class BinaryTree {

    Node root;

    // Default constructor
    public BinaryTree() {
        this.root = null;
    }

    // Constructor that builds a Binary Tree from an array
    public BinaryTree(int[] arr) {
        this.root = buildTree(arr, 0);
    }

    // Level-order construction: left child at 2*i + 1, right child at 2*i + 2
    // (-1 represents null / empty node)
    public static Node buildTree(int[] arr, int index) {
        if (arr == null || index >= arr.length || arr[index] == -1) {
            return null;
        }

        Node node = new Node(arr[index]);
        node.left = buildTree(arr, 2 * index + 1);
        node.right = buildTree(arr, 2 * index + 2);

        return node;
    }

    public static void PreOrderTraversal(Node node) {
        if (node == null)
            return;

        System.out.print(node.data + " ");
        PreOrderTraversal(node.left);
        PreOrderTraversal(node.right);
    }

    public static void InOrderTraversal(Node node) {
        if (node == null)
            return;

        InOrderTraversal(node.left);
        System.out.print(node.data + " ");
        InOrderTraversal(node.right);
    }

    public static void PostOrderTraversal(Node node) {
        if (node == null)
            return;

        PostOrderTraversal(node.left);
        PostOrderTraversal(node.right);

        System.out.print(node.data + " ");
    }

    public static void LevelOrderTraversal(Node root) {
        if (root == null)
            return;

        Queue<Node> que = new ArrayDeque<>();

        que.offer(root);

        while (!que.isEmpty()) {
            Node n = que.poll();

            System.out.print(n.data + " ");

            if (n.left != null) {
                que.offer(n.left);
            }
            if (n.right != null) {
                que.offer(n.right);
            }
        }

    }

    public static void main(String[] args) {

        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
        BinaryTree tree = new BinaryTree(arr);

        System.out.print("Pre-order Traversal: ");
        PreOrderTraversal(tree.root);
        System.out.println();

        System.out.print("In-order Traversal: ");
        InOrderTraversal(tree.root);
        System.out.println();

        System.out.print("Post-order Traversal: ");
        PostOrderTraversal(tree.root);
        System.out.println();

        System.out.print("Level-order (BFS) Traversal: ");
        LevelOrderTraversal(tree.root);
        System.out.println();
    }
}