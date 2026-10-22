// Explicit Production-Grade Imports (avoiding wildcard 'import java.util.*' for namespace clarity and build speed)
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

// Main Driver Class for DSA Data Structures
// All specialized data structure classes are defined as 'public static class' inside DSA
// so they can be public, properly encapsulated, and called directly or via DSA.<ClassOps>
public class DSA {

    public static void main(String[] args) {
        System.out.println("========== 1. ARRAY OPERATIONS ==========");
        ArrayOps.demo();

        System.out.println("\n========== 2. STRING & STRINGBUILDER ==========");
        StringOps.demo();

        System.out.println("\n========== 3. ARRAYLIST (DYNAMIC ARRAY) ==========");
        ArrayListOps.demo();

        System.out.println("\n========== 4. STACK (VIA ARRAYDEQUE) ==========");
        StackOps.demo();

        System.out.println("\n========== 5. QUEUE (FIFO FOR BFS) ==========");
        QueueOps.demo();

        System.out.println("\n========== 6. DEQUE (MONOTONIC / SLIDING WINDOW) ==========");
        DequeOps.demo();

        System.out.println("\n========== 7. PRIORITYQUEUE (MIN/MAX HEAP) ==========");
        HeapOps.demo();

        System.out.println("\n========== 8. HASHMAP (FREQUENCY & LOOKUP) ==========");
        MapOps.demo();

        System.out.println("\n========== 9. HASHSET (UNIQUE & VISITED) ==========");
        SetOps.demo();

        System.out.println("\n========== 10. TREEMAP & TREESET (ORDERED BST) ==========");
        TreeOps.demo();
    }

    // -------------------------------------------------------------
    // 1. Array Operations (Fixed-Size Contiguous Memory)
    // -------------------------------------------------------------
    public static class ArrayOps {
        public static void demo() {
            int[] nums = {5, 2, 9, 1, 3};

            // Length is a property, not a method
            System.out.println("Array length: " + nums.length);

            // Sorting: O(n log n) Dual-Pivot Quicksort
            Arrays.sort(nums);
            System.out.println("Sorted: " + Arrays.toString(nums)); // [1, 2, 3, 5, 9]

            // Binary Search: O(log n) - Array must be sorted first!
            int idx = Arrays.binarySearch(nums, 3);
            System.out.println("Index of 3: " + idx);

            // Fill array with a default value
            int[] dp = new int[5];
            Arrays.fill(dp, -1);
            System.out.println("Initialized DP array: " + Arrays.toString(dp));

            // 2D Matrix Grid: rows x cols
            int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
            };
            int rows = matrix.length;
            int cols = matrix[0].length;
            System.out.println("Matrix Dimensions: " + rows + "x" + cols);
        }
    }

    // -------------------------------------------------------------
    // 2. String & StringBuilder (Immutable vs Mutable)
    // -------------------------------------------------------------
    public static class StringOps {
        public static void demo() {
            String s = "leetcode";

            // Char access: O(1)
            char first = s.charAt(0);
            System.out.println("First char: " + first);

            // Substring [start, end)
            String sub = s.substring(0, 4); // "leet"
            System.out.println("Substring: " + sub);

            // Fast char array manipulation
            char[] chars = s.toCharArray();
            chars[0] = 'L';
            String modified = new String(chars);
            System.out.println("Modified: " + modified);

            // StringBuilder: Essential for O(1) appends and Backtracking
            StringBuilder sb = new StringBuilder();
            sb.append("abc").append(123);
            sb.reverse(); // In-place reverse
            System.out.println("Reversed SB: " + sb);

            // Backtracking pop idiom:
            sb.deleteCharAt(sb.length() - 1); // Removes last character in O(1)
            System.out.println("After Backtrack Pop: " + sb.toString());
        }
    }

    // -------------------------------------------------------------
    // 3. ArrayList (Dynamic Resizable Array)
    // -------------------------------------------------------------
    public static class ArrayListOps {
        public static void demo() {
            List<Integer> list = new ArrayList<>();
            list.add(40);
            list.add(10);
            list.add(30);

            // O(1) indexing
            System.out.println("Element at index 1: " + list.get(1));

            // Sorting: Ascending & Descending (via Lambda)
            Collections.sort(list); // [10, 30, 40]
            System.out.println("Sorted list: " + list);

            list.sort((a, b) -> b - a); // Descending order
            System.out.println("Descending:  " + list);

            // Graph Adjacency List: List of Lists
            int vertices = 3;
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < vertices; i++) adj.add(new ArrayList<>());
            adj.get(0).add(1); // Edge 0 -> 1
            adj.get(0).add(2); // Edge 0 -> 2
            System.out.println("Neighbors of Node 0: " + adj.get(0));
        }
    }

    // -------------------------------------------------------------
    // 4. Stack (LIFO - Using ArrayDeque instead of legacy Stack)
    // -------------------------------------------------------------
    public static class StackOps {
        public static void demo() {
            // Preferred over java.util.Stack (which has synchronized overhead)
            Deque<Integer> stack = new ArrayDeque<>();

            // Push: O(1)
            stack.push(10);
            stack.push(20);
            stack.push(30);

            // Peek top element without removing
            System.out.println("Stack Top: " + stack.peek()); // 30

            // Pop top element: O(1)
            int popped = stack.pop();
            System.out.println("Popped: " + popped); // 30
            System.out.println("Stack empty? " + stack.isEmpty());
        }
    }

    // -------------------------------------------------------------
    // 5. Queue (FIFO - Breadth-First Search / Level-Order)
    // -------------------------------------------------------------
    public static class QueueOps {
        public static void demo() {
            Queue<String> queue = new ArrayDeque<>();

            // Enqueue: offer()
            queue.offer("A");
            queue.offer("B");
            queue.offer("C");

            // Peek front
            System.out.println("Front: " + queue.peek()); // A

            // Dequeue: poll()
            while (!queue.isEmpty()) {
                System.out.println("Processing: " + queue.poll());
            }
        }
    }

    // -------------------------------------------------------------
    // 6. Deque (Double-Ended Queue for Monotonic Queue / Sliding Window)
    // -------------------------------------------------------------
    public static class DequeOps {
        public static void demo() {
            Deque<Integer> dq = new ArrayDeque<>();

            // Add to both ends: O(1)
            dq.offerFirst(10); // Front: [10]
            dq.offerLast(20);  // Back:  [10, 20]
            dq.offerFirst(5);  // Front: [5, 10, 20]

            System.out.println("Peek First: " + dq.peekFirst()); // 5
            System.out.println("Peek Last:  " + dq.peekLast());  // 20

            // Remove from ends: O(1)
            System.out.println("Poll First: " + dq.pollFirst()); // 5
            System.out.println("Poll Last:  " + dq.pollLast());  // 20
        }
    }

    // -------------------------------------------------------------
    // 7. PriorityQueue (Min-Heap and Max-Heap)
    // -------------------------------------------------------------
    public static class HeapOps {
        public static void demo() {
            // 1. Min-Heap (Default in Java)
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            minHeap.offer(50);
            minHeap.offer(10);
            minHeap.offer(30);
            System.out.println("Min-Heap root (smallest): " + minHeap.peek()); // 10

            // 2. Max-Heap (Reverse Order)
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            maxHeap.offer(50);
            maxHeap.offer(10);
            maxHeap.offer(30);
            System.out.println("Max-Heap root (largest):  " + maxHeap.poll()); // 50

            // 3. Custom Pair Heap (e.g., [node, distance] for Dijkstra)
            // Sort by distance ascending: (a, b) -> Integer.compare(a[1], b[1])
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
            pq.offer(new int[]{1, 100});
            pq.offer(new int[]{2, 25});
            int[] nearest = pq.poll();
            System.out.println("Nearest Node: " + nearest[0] + " with distance: " + nearest[1]);
        }
    }

    // -------------------------------------------------------------
    // 8. HashMap (O(1) Key-Value Lookup & Frequency Map)
    // -------------------------------------------------------------
    public static class MapOps {
        public static void demo() {
            Map<String, Integer> freq = new HashMap<>();
            String[] words = {"apple", "banana", "apple", "orange", "apple"};

            // The essential DSA frequency counting idiom:
            for (String w : words) {
                freq.put(w, freq.getOrDefault(w, 0) + 1);
            }

            System.out.println("Frequency of 'apple':  " + freq.get("apple"));
            System.out.println("Contains 'grapes'?      " + freq.containsKey("grapes"));

            // Iterating over Map entries
            System.out.println("All entries:");
            for (Map.Entry<String, Integer> entry : freq.entrySet()) {
                System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
            }
        }
    }

    // -------------------------------------------------------------
    // 9. HashSet (O(1) Visited Tracker & Uniqueness)
    // -------------------------------------------------------------
    public static class SetOps {
        public static void demo() {
            Set<Integer> visited = new HashSet<>();

            // Add returns false if element was already present
            System.out.println("Add 1: " + visited.add(1)); // true
            System.out.println("Add 2: " + visited.add(2)); // true
            System.out.println("Add 1 (duplicate): " + visited.add(1)); // false

            // Fast O(1) lookup
            if (visited.contains(2)) {
                System.out.println("Node 2 has been visited.");
            }
            System.out.println("Unique nodes count: " + visited.size());
        }
    }

    // -------------------------------------------------------------
    // 10. TreeMap & TreeSet (Balanced BST - O(log n) Range Queries)
    // -------------------------------------------------------------
    public static class TreeOps {
        public static void demo() {
            // TreeSet: Ordered Set
            TreeSet<Integer> set = new TreeSet<>(Arrays.asList(10, 20, 30, 40));

            System.out.println("Smallest (first): " + set.first()); // 10
            System.out.println("Largest (last):   " + set.last());  // 40

            // Floor: greatest element <= key
            System.out.println("Floor(25):   " + set.floor(25));   // 20
            // Ceiling: smallest element >= key
            System.out.println("Ceiling(25): " + set.ceiling(25)); // 30

            // TreeMap: Ordered Map
            TreeMap<Integer, String> map = new TreeMap<>();
            map.put(100, "User A");
            map.put(200, "User B");
            System.out.println("Smallest Key: " + map.firstKey()); // 100
        }
    }
}
