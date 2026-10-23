# Java Data Structures for DSA — Quick Reference Cheatsheet

## 1. Array (Primitive & Fixed-Size)
Use when: Size is known in advance, fixed memory, 2D grids/matrices.

- **Access / Update**: $O(1)$
- **Search**: $O(n)$ ($O(\log n)$ if sorted via binary search)

```java
// 1D Array
int[] arr = new int[5];                 // Default values: 0
int[] nums = {4, 2, 8, 1, 5};

int len = nums.length;                   // Property, not a method!
Arrays.sort(nums);                       // O(n log n) in-place primitive dual-pivot quicksort
Arrays.fill(arr, -1);                    // Fill all elements with -1
int idx = Arrays.binarySearch(nums, 4);  // O(log n) - requires sorted array
int[] copy = Arrays.copyOf(nums, nums.length);

// 2D Array (Matrix / Grid)
int[][] grid = new int[rows][cols];
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};
int r = matrix.length;       // Number of rows
int c = matrix[0].length;    // Number of columns
```

---

## 2. String & StringBuilder
Use when: Text processing, sliding window, palindrome, backtracking.
> **Note**: `String` is **immutable** in Java. Never do `str += ch` inside a loop (it creates a new object each time, leading to $O(n^2)$ time). Always use `StringBuilder`.

```java
// String
String s = "leetcode";
int len = s.length();
char ch = s.charAt(0);                   // O(1)
String sub = s.substring(1, 4);          // "eet" [start, end)
char[] chars = s.toCharArray();          // Convert to char array for fast manipulation
String fromChars = new String(chars);

// StringBuilder (Mutable - O(1) appends)
StringBuilder sb = new StringBuilder();
sb.append("a").append(10);               // Append char, string, int
sb.setCharAt(0, 'b');                   // Modify in-place O(1)
sb.deleteCharAt(sb.length() - 1);       // Backtracking backtrack step O(1)
sb.reverse();                           // In-place reverse O(n)
String res = sb.toString();             // Convert back to String
```

---

## 3. ArrayList (Dynamic Array)
Use when: Dynamic resizing, fast random indexing $O(1)$, list of results.

- **Add to end**: $O(1)$ amortized
- **Get / Set by index**: $O(1)$
- **Insert / Remove at index**: $O(n)$

```java
List<Integer> list = new ArrayList<>();
list.add(10);                           // Append to end
list.add(0, 5);                         // Insert at index O(n)
int val = list.get(0);                  // Get element at index
list.set(0, 99);                        // Update element
list.remove(list.size() - 1);           // Remove last element O(1)
int size = list.size();
boolean empty = list.isEmpty();

// Sorting
Collections.sort(list);                             // Ascending order
Collections.sort(list, Collections.reverseOrder()); // Descending order
Collections.sort(list, (a, b) -> b - a);            // Lambda comparator

// 2D List (Adjacency List for Graphs)
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
adj.get(u).add(v);                      // Add edge u -> v
```

> [!NOTE]
>
> - `List` is an **interface** that defines the operations a list should support.
> - `ArrayList` is a **class** that implements the `List` interface.
>
> ### 1. Using an Interface as the Reference Type
>
> ```java
> List<Integer> numbers = new ArrayList<>();
> ```
>
> **Advantages:**
> - Provides abstraction by depending on an interface rather than a specific implementation.
> - Makes it easier to switch implementations in the future.
>
> For example, to switch from `ArrayList` to `LinkedList`, you only need to change one line:
>
> ```java
> List<Integer> numbers = new LinkedList<>();
> ```
>
> The rest of your code can remain unchanged, provided it uses methods available through the `List` interface.
>
> ### 2. Using a Class as the Reference Type
>
> ```java
> ArrayList<Integer> numbers = new ArrayList<>();
> ```
>
> This is also valid Java. However, the reference is tied to the `ArrayList` class, so you cannot directly assign a `LinkedList` object to it.
>
> ```java
> ArrayList<Integer> numbers = new ArrayList<>();
>
> numbers = new LinkedList<>(); // Compilation error
> ```
>
> ### 3. Other Common Examples
>
> The same principle applies to other Java collections:
>
> ```java
> List<Integer> list = new ArrayList<>();
> Set<Integer> set = new HashSet<>();
> Map<String, Integer> map = new HashMap<>();
> Queue<Integer> queue = new LinkedList<>();
> ```
>
> ### KEY TAKEAWAY
>
> **Prefer interface references when you want flexibility, but using an interface is not mandatory.** You can use a concrete class as the reference type whenever appropriate.

---

## 4. Stack (via `ArrayDeque`)
Use when: LIFO (Last-In-First-Out), Monotonic Stack, Valid Parentheses, DFS, Backtracking.
> **Rule**: Do **NOT** use `java.util.Stack` (it is legacy, extends `Vector`, and is synchronized/slow). Use `ArrayDeque` as a Stack.

- **Push / Pop / Peek**: $O(1)$

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);                         // Push element to top
int top = stack.peek();                 // Look at top element without removing
int popped = stack.pop();               // Remove and return top element
boolean isEmpty = stack.isEmpty();
int size = stack.size();
```

---

## 5. Queue (via `ArrayDeque` or `LinkedList`)
Use when: FIFO (First-In-First-Out), BFS (Level-Order Traversal), Sliding Window.

- **Offer (Enqueue) / Poll (Dequeue) / Peek**: $O(1)$

```java
Queue<Integer> queue = new ArrayDeque<>(); // Or new LinkedList<>()

queue.offer(10);                        // Add to rear
int front = queue.peek();               // Inspect front element
int removed = queue.poll();             // Remove and return front
boolean isEmpty = queue.isEmpty();
int size = queue.size();
```

---

## 6. Deque (Double-Ended Queue via `ArrayDeque`)
Use when: Monotonic Queue (e.g., Sliding Window Maximum), adding/removing from both ends.

- **All ends operations**: $O(1)$

```java
Deque<Integer> dq = new ArrayDeque<>();

dq.offerFirst(1);                       // Insert at front
dq.offerLast(2);                        // Insert at back
int first = dq.peekFirst();             // Inspect front
int last = dq.peekLast();               // Inspect back
int remFirst = dq.pollFirst();          // Remove front
int remLast = dq.pollLast();            // Remove back
```

---

## 7. PriorityQueue (Min-Heap & Max-Heap)
Use when: Top-K elements, Dijkstra's algorithm, Median in stream, Merge K sorted lists.

- **Offer / Poll**: $O(\log n)$
- **Peek**: $O(1)$

```java
// 1. Min-Heap (Default: smallest element at the top)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(1);
int smallest = minHeap.poll();          // Returns 1

// 2. Max-Heap (Largest element at top)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// Or using lambda: (a, b) -> b - a (Watch out for integer overflow with subtraction!)
PriorityQueue<Integer> maxHeap2 = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

// 3. Custom Object Heap (e.g., sort pairs by frequency, then value)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // [val, freq]
pq.offer(new int[]{num, count});
```

---

## 8. HashMap (Hash Table)
Use when: Frequency counting, two-sum lookups, caching, graph adjacency with weights.

- **Put / Get / ContainsKey / Remove**: $O(1)$ average

```java
Map<String, Integer> map = new HashMap<>();

map.put("apple", 3);
int count = map.get("apple");                     // Returns 3
int safe = map.getOrDefault("banana", 0);         // Returns default value 0 if key not found
boolean hasKey = map.containsKey("apple");        // O(1)
map.remove("apple");                              // O(1)

// Frequency counter shorthand (Most common DSA idiom):
map.put(key, map.getOrDefault(key, 0) + 1);

// Iterating over Map
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    int val = entry.getValue();
}
for (String key : map.keySet()) { ... }
```

---

## 9. HashSet (Hash Set)
Use when: Tracking visited nodes in graphs/BFS, duplicate detection, $O(1)$ existence check.

- **Add / Contains / Remove**: $O(1)$ average

```java
Set<Integer> set = new HashSet<>();

set.add(10);                            // Returns true if added, false if already present
boolean exists = set.contains(10);      // O(1) lookup
set.remove(10);
int size = set.size();
set.clear();
```

---

## 10. TreeMap & TreeSet (Red-Black BST — Ordered)
Use when: You need ordered keys, dynamic sorting, or finding closest ceiling/floor elements.

- **All operations**: $O(\log n)$

```java
// TreeSet: Ordered Set
TreeSet<Integer> tSet = new TreeSet<>();
tSet.add(10);
tSet.add(20);
tSet.add(30);

int smallest = tSet.first();            // 10
int largest = tSet.last();              // 30
Integer floor = tSet.floor(25);         // Greatest element <= 25 (returns 20)
Integer ceil = tSet.ceiling(25);        // Smallest element >= 25 (returns 30)
Integer lower = tSet.lower(20);         // Greatest element < 20 (returns 10)
Integer higher = tSet.higher(20);       // Smallest element > 20 (returns 30)

// TreeMap: Ordered Map by Keys
TreeMap<Integer, String> tMap = new TreeMap<>();
tMap.put(10, "Ten");
tMap.firstKey();                        // Smallest key
tMap.floorKey(15);                      // Largest key <= 15
```

---

## 11. Custom Nodes for DSA (Linked List & Binary Tree)

```java
// Singly Linked List Node
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

// Binary Tree Node
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}

// Graph / BFS State Tuple (Modern Java Record)
record State(int node, int distance) {}
// Or simply int[] {node, distance}
```

---

## Quick Summary Decision Table

| DSA Pattern | Best Data Structure in Java | Implementation |
| :--- | :--- | :--- |
| **Random access / Fixed lookup** | Array | `int[]` |
| **Dynamic list / Resizable array** | ArrayList | `List<T> = new ArrayList<>()` |
| **LIFO / Bracket matching / Monotonic stack** | Stack | `Deque<T> = new ArrayDeque<>()` |
| **FIFO / BFS level traversal** | Queue | `Queue<T> = new ArrayDeque<>()` |
| **Sliding Window Max / Min** | Monotonic Deque | `Deque<T> = new ArrayDeque<>()` |
| **Top K / Min / Max in dynamic stream** | Heap | `PriorityQueue<T>` |
| **Key-Value lookup / Frequency map** | Hash Table | `Map<K, V> = new HashMap<>()` |
| **Unique elements / Visited set** | Hash Set | `Set<T> = new HashSet<>()` |
| **Floor / Ceiling / Range queries** | Balanced BST | `TreeSet<T>` / `TreeMap<K, V>` |
| **String building / Backtracking** | Mutable String | `StringBuilder` |
