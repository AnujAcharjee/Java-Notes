package DSA_Practice_in_Java;

// In-order Traversal of BST return sorted list

// BinarySearchTree extends BinaryTree to inherit root, Node, and traversals
public class BinarySearchTree extends BinaryTree {

    public BinarySearchTree() {
        this.root = null;
    }

    // Construct BST from an array
    public BinarySearchTree(int[] arr) {
        if (arr != null) {
            for (int val : arr) {
                insert(val);
            }
        }
    }

    // TC: Avg (log N) , Worst (N)
    public void insert(int val) {
        Node newNode = new Node(val);
        if (this.root == null) {
            this.root = newNode;
            return;
        }

        Node curr = this.root;
        while (true) {
            if (val < curr.data) {
                if (curr.left == null) {
                    curr.left = newNode;
                    return;
                }
                curr = curr.left;
            } else if (val > curr.data) {
                if (curr.right == null) {
                    curr.right = newNode;
                    return;
                }
                curr = curr.right;
            } else {
                return; // Duplicate value, no need to insert
            }
        }
    }

    // Single search function (iterative)
    public boolean search(int val) {
        Node curr = this.root;
        while (curr != null) {
            if (val == curr.data) {
                return true;
            }
            curr = (val < curr.data) ? curr.left : curr.right;
        }
        return false;
    }

    // Delete a value from the BST
    public void delete(int val) {
        this.root = deleteNode(this.root, val);
    }

    private Node deleteNode(Node root, int val) {
        if (root == null) {
            return null;
        }

        if (val < root.data) {
            root.left = deleteNode(root.left, val);
        } else if (val > root.data) {
            root.right = deleteNode(root.right, val);
        } else {
            // Node found! Handle the 3 cases:

            // Case 1 & 2: Node with 0 children (leaf) or 1 child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Case 3: Node with 2 children
            // Find in-order successor (smallest value in the right subtree)
            root.data = findMin(root.right);

            // Delete the in-order successor
            root.right = deleteNode(root.right, root.data);
        }

        return root;
    }

    // Helper to find the minimum value in a subtree
    private int findMin(Node node) {
        int min = node.data;
        while (node.left != null) {
            min = node.left.data;
            node = node.left;
        }
        return min;
    }

    // *** Range approach (Construct BST from Preorder in O(N) time) ***
    public void bstFromPreOrder(int[] preorder) {
        if (preorder == null || preorder.length == 0)
            return;

        int[] index = { 0 }; // Use array reference so index persists across recursive calls
        this.root = builder(index, preorder, Integer.MAX_VALUE);
    }

    private Node builder(int[] index, int[] preorder, int upperBound) {
        if (index[0] == preorder.length || preorder[index[0]] > upperBound) {
            return null;
        }

        Node root = new Node(preorder[index[0]++]);

        root.left = builder(index, preorder, root.data);
        root.right = builder(index, preorder, upperBound);

        return root;
    }

    public static void main(String[] args) {
        int[] values = { 5, 1, 3, 4, 2, 7 };
        BinarySearchTree bst = new BinarySearchTree(values);

        System.out.print("In-order Traversal (Sorted): ");
        InOrderTraversal(bst.root); // Sorted: 1 2 3 4 5 7
        System.out.println();

        System.out.println("Search 4: " + bst.search(4));
        System.out.println("Search 9: " + bst.search(9));

        System.out.println("\n--- Deletion Tests ---");
        // Case 1: Delete a leaf node (7)
        System.out.println("Deleting leaf node 7...");
        bst.delete(7);
        System.out.print("In-order: ");
        InOrderTraversal(bst.root);
        System.out.println();

        // Case 2: Delete node with 1 child (1, whose right child is 3)
        System.out.println("Deleting node 1 (has 1 child)...");
        bst.delete(1);
        System.out.print("In-order: ");
        InOrderTraversal(bst.root);
        System.out.println();

        // Case 3: Delete node with 2 children (3, whose left is 2 and right is 4)
        System.out.println("Deleting node 3 (has 2 children)...");
        bst.delete(3);
        System.out.print("In-order: ");
        InOrderTraversal(bst.root);
        System.out.println();

        // Testing bstFromPreOrder
        System.out.println("\n--- BST from Preorder Test ---");
        int[] preorder = { 8, 5, 1, 7, 10, 12 };
        BinarySearchTree preTree = new BinarySearchTree();
        preTree.bstFromPreOrder(preorder);

        System.out.print("In-order Traversal (Sorted): ");
        InOrderTraversal(preTree.root);
        System.out.println();

        System.out.print("Level-order Traversal: ");
        LevelOrderTraversal(preTree.root);
        System.out.println();
    }
}