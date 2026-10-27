package DSA_Practice_in_Java;

// AVLTree: Self-Balancing Binary Search Tree
// Height invariant: (height(left) - height(right)) <= 1 for all nodes


public class AVLTree extends BinaryTree {

    public AVLTree() {
        this.root = null;
    }

    // Construct AVL Tree from array
    public AVLTree(int[] arr) {
        if (arr != null) {
            for (int val : arr) {
                insert(val);
            }
        }
    }

    // Safely get height of a node
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    // Balance factor = height(left) - height(right)
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // ==========================================
    // Rotations
    // ==========================================

    /*
     * Right Rotation (LL Case):
     *        y                               x
     *       / \     Right Rotation          /  \
     *      x   T3   -------------->        T1   y
     *     / \                                  / \
     *    T1  T2                               T2  T3
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights (order matters: children first, then parent)
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    /*
     * Left Rotation (RR Case):
     *      x                                 y
     *     / \      Left Rotation            / \
     *    T1  y     ------------->          x  T3
     *       / \                           / \
     *      T2  T3                        T1  T2
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    // ==========================================
    // Insertion with Auto-Balancing
    // ==========================================

    public void insert(int val) {
        this.root = insert(this.root, val);
    }

    private Node insert(Node node, int val) {
        // Step 1: Standard BST insertion
        if (node == null) {
            return new Node(val);
        }

        if (val < node.data) {
            node.left = insert(node.left, val);
        } else if (val > node.data) {
            node.right = insert(node.right, val);
        } else {
            return node; // Duplicate values not allowed in AVL
        }

        // Step 2: Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Step 3: Get balance factor to check if it became unbalanced
        int balance = getBalance(node);

        // Step 4: If unbalanced, there are 4 cases:

        // Case 1: Left-Left (LL) -> Single Right Rotation
        if (balance > 1 && val < node.left.data) {
            return rightRotate(node);
        }

        // Case 2: Right-Right (RR) -> Single Left Rotation
        if (balance < -1 && val > node.right.data) {
            return leftRotate(node);
        }

        // Case 3: Left-Right (LR) -> Left Rotate on child, then Right Rotate on node
        if (balance > 1 && val > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Case 4: Right-Left (RL) -> Right Rotate on child, then Left Rotate on node
        if (balance < -1 && val < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // ==========================================
    // Deletion with Auto-Balancing
    // ==========================================

    public void delete(int val) {
        this.root = deleteNode(this.root, val);
    }

    private Node deleteNode(Node root, int val) {
        // Step 1: Standard BST delete
        if (root == null) {
            return null;
        }

        if (val < root.data) {
            root.left = deleteNode(root.left, val);
        } else if (val > root.data) {
            root.right = deleteNode(root.right, val);
        } else {
            // Node found: 0 or 1 child
            if (root.left == null || root.right == null) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    // No child case
                    root = null;
                } else {
                    // One child case
                    root = temp;
                }
            } else {
                // Node with 2 children: get in-order successor (min in right subtree)
                Node temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = deleteNode(root.right, temp.data);
            }
        }

        // If tree had only 1 node and it was deleted
        if (root == null) {
            return null;
        }

        // Step 2: Update height of current node
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // Step 3: Check balance factor
        int balance = getBalance(root);

        // Step 4: Rebalance if needed (4 cases)

        // Case 1: Left-Left (LL)
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // Case 2: Left-Right (LR)
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Case 3: Right-Right (RR)
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // Case 4: Right-Left (RL)
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node curr = node;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    // ==========================================
    // Search
    // ==========================================

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

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        int[] values = { 10, 20, 30, 40, 50, 25 };
        System.out.println("Inserting elements: 10, 20, 30, 40, 50, 25");
        for (int v : values) {
            tree.insert(v);
        }

        System.out.print("In-order Traversal (Sorted): ");
        InOrderTraversal(tree.root);
        System.out.println();

        System.out.print("Pre-order Traversal (Structure): ");
        PreOrderTraversal(tree.root);
        System.out.println();

        System.out.print("Level-order Traversal (BFS): ");
        LevelOrderTraversal(tree.root);
        System.out.println();

        System.out.println("Tree Root: " + tree.root.data);
        System.out.println("Tree Root Height: " + tree.root.height);

        // Deletion test
        System.out.println("\nDeleting 40 from AVL Tree...");
        tree.delete(40);

        System.out.print("Pre-order after deleting 40: ");
        PreOrderTraversal(tree.root);
        System.out.println();

        System.out.print("In-order after deleting 40: ");
        InOrderTraversal(tree.root);
        System.out.println();
    }
}
