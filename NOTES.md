# Java Core Concepts — Quick Reference Notes

---

## 1. JDK vs JRE vs JVM

```
+-------------------------------------------------------+
| JDK (Java Development Kit)                            |
|  +-------------------------------------------------+  |
|  | JRE (Java Runtime Environment)                  |  |
|  |  +---------------------+  +------------------+  |  |
|  |  | JVM                 |  | Core Libraries   |  |  |
|  |  | (Executes Bytecode) |  | (rt.jar, APIs)   |  |  |
|  |  +---------------------+  +------------------+  |  |
|  +-------------------------------------------------+  |
|  Development Tools (javac, jdb, javap, jar, etc.)     |
+-------------------------------------------------------+
```

- **JVM (Java Virtual Machine)**: Platform-dependent runtime engine that converts bytecode (`.class`) into machine code via interpreter + JIT (Just-In-Time) compiler. Manages memory (Garbage Collector).
- **JRE (Java Runtime Environment)**: JVM + standard runtime class libraries. Minimum requirement to **run** Java programs.
- **JDK (Java Development Kit)**: JRE + developer tools (compiler `javac`, debugger, packagers). Required to **develop and compile** Java programs.

---

## 2. Class and File Naming Rules

- **Matching Rule**: If a class is declared `public`, the file name must match the class name exactly (case-sensitive) with `.java` extension (`public class UserProfile` $\rightarrow$ `UserProfile.java`).
- **Single Public Class**: A single `.java` file can have at most **one** `public` top-level class, but can contain multiple non-public (package-private) classes.
- **Naming Conventions**:
  - **Classes / Interfaces**: `PascalCase` (`OrderService`, `Runnable`)
  - **Methods / Variables**: `camelCase` (`calculateTotal()`, `itemCount`)
  - **Constants**: `UPPER_SNAKE_CASE` (`MAX_RETRY_COUNT`, `PI`)
  - **Packages**: all lowercase, reverse domain notation (`com.example.app.service`)

---

## 3. Packages and Access Modifiers

### Packages
- Act as namespaces to organize related classes and avoid name collisions (`package com.myapp.util;`).
- Map directly to physical directory structures.

### Access Modifiers Matrix

| Modifier | Same Class | Same Package | Subclass (diff package) | Everywhere |
| :--- | :---: | :---: | :---: | :---: |
| `private` | Yes | No | No | No |
| *default* (package-private) | Yes | Yes | No | No |
| `protected` | Yes | Yes | Yes | No |
| `public` | Yes | Yes | Yes | Yes |

---

## 4. `static`, `final`, `this`, and `super`

### `static`
- Belongs to the **class**, not to any specific instance (shared across all instances).
- Accessible via `ClassName.member` without creating an object.
- Used with: variables (class variables), methods (cannot use `this`/`super`), static blocks (runs once when class is loaded), and nested static classes.

### `final`
- **Variable**: Becomes a constant; value cannot be reassigned once initialized.
- **Method**: Cannot be overridden by subclasses.
- **Class**: Cannot be inherited/extended (e.g., `java.lang.String` is `final`).

### `this`
- Reference to the **current object instance**.
- Used to resolve shadowing (`this.id = id`) and chain constructors (`this(name, 0)`).

### `super`
- Reference to the **immediate parent class**.
- Used to invoke parent constructors (`super(args)`) or call overridden parent methods (`super.doWork()`).

---

## 5. Abstract Classes vs Interfaces

| Feature | Abstract Class | Interface (Java 8+) |
| :--- | :--- | :--- |
| **Inheritance** | Single inheritance (`extends`) | Multiple inheritance (`implements`) |
| **State / Fields** | Can have instance variables with state | Fields are implicitly `public static final` (constants) |
| **Constructors** | Yes (called during subclass instantiation) | No constructors allowed |
| **Methods** | Abstract, concrete, final, static | Abstract, `default`, `static` (and `private` since Java 9) |
| **Intent** | "Is-a" relationship; shared base code & state | "Can-do" contract; common behavior capability |

---

## 6. `==` vs `.equals()`

- **`==` Operator**:
  - **Primitives**: Compares literal values (`5 == 5` is `true`).
  - **Objects**: Compares **references / memory addresses** (checks if both variables point to the exact same object in heap).
- **`.equals()` Method**:
  - Defined in `Object` class (default implementation uses `==`).
  - Intended to be overridden to compare **logical / content equality** (e.g., `String`, `Integer`, `Date`).

```java
String s1 = new String("hello");
String s2 = new String("hello");

System.out.println(s1 == s2);      // false (different heap addresses)
System.out.println(s1.equals(s2));  // true  (identical characters)
```

---

## 7. `equals()` and `hashCode()` Contract

When overriding `equals()`, you **must** also override `hashCode()`.

### The Contract Rules:
1. If `a.equals(b) == true`, then `a.hashCode() == b.hashCode()` **must** be true.
2. If `a.hashCode() == b.hashCode()`, `a.equals(b)` is **not necessarily** true (known as a *hash collision*).
3. If `equals()` is not modified, multiple invocations of `hashCode()` on the same object must return the same integer.

### Why It Matters:
Hash-based collections (`HashMap`, `HashSet`, `Hashtable`) use `hashCode()` to find the bucket index and `equals()` to find the exact entry. If the contract is broken, objects become "lost" or appear as duplicates.

---

## 8. Garbage Collection (GC)

Automatic memory reclamation in the JVM heap for objects that are no longer reachable from any GC root (thread stacks, static variables, JNI references).

### Generational Heap Structure
1. **Young Generation**:
   - **Eden Space**: New objects are allocated here.
   - **Survivor Spaces (S0 / S1)**: Objects that survive Minor GC get copied between S0 and S1.
2. **Old (Tenured) Generation**:
   - Objects surviving a threshold number of GC cycles (tenuring threshold) are promoted here. Cleaned by Major / Full GC.
3. **Metaspace (Off-heap since Java 8)**:
   - Stores class metadata (replaces PermGen).

### Key Takeaways:
- `System.gc()` is only a hint/request to JVM, not a guarantee.
- Common collectors: **G1GC** (default in modern Java), **ZGC** / **Shenandoah** (ultra-low latency), **Parallel GC** (throughput).

---

## 9. String Pool and String Immutability

### String Immutability
- Once created, a `String` object’s content cannot change. Any modification (`concat`, `replace`, `substring`) creates a new `String` object.
- **Benefits**: Thread-safety, security (caching sensitive data like network URLs, passwords), and safe `hashCode()` caching.

### String Constant Pool (SCP)
- A special memory region inside the Heap for storing string literals to conserve memory.

```java
String a = "java";                  // Placed in String Constant Pool
String b = "java";                  // Reuses reference from Pool (a == b is true)
String c = new String("java");       // Creates new object in regular Heap (a == c is false)
String d = c.intern();              // Returns pooled reference (a == d is true)
```

---

## 10. Checked vs Unchecked Exceptions

```
              Throwable
              /       \
          Error      Exception
                       /     \
    Checked Exceptions        RuntimeException (Unchecked)
```

- **Checked Exceptions**:
  - Subclasses of `Exception` (except `RuntimeException`).
  - Checked at **compile time**. Compiler forces you to handle via `try-catch` or declare via `throws`.
  - Represent recoverable external conditions (e.g., `IOException`, `SQLException`, `ClassNotFoundException`).
- **Unchecked Exceptions**:
  - Subclasses of `RuntimeException` and `Error`.
  - Not enforced at compile time.
  - Represent programming errors, bugs, or fatal conditions (e.g., `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ArithmeticException`, `OutOfMemoryError`).

---

## 11. Collections Framework

### Hierarchy Overview
- `Iterable<T>` $\rightarrow$ `Collection<T>` $\rightarrow$ `List`, `Set`, `Queue`
- `Map<K, V>` is a separate root hierarchy (stores key-value pairs).

### Core Interfaces & Implementations

| Interface | Characteristics | Common Implementations |
| :--- | :--- | :--- |
| **`List`** | Ordered, allows duplicates, positional index access. | `ArrayList` (dynamic array, fast $O(1)$ random read), `LinkedList` (doubly linked list, fast insert/delete at ends). |
| **`Set`** | No duplicates. | `HashSet` (backed by hash table, $O(1)$ lookup, unordered), `LinkedHashSet` (insertion-ordered), `TreeSet` (sorted via `Comparable`/`Comparator`, $O(\log n)$). |
| **`Queue` / `Deque`** | FIFO or double-ended queues. | `ArrayDeque` (resizable array deque, faster than `Stack`), `PriorityQueue` (heap-based priority order). |
| **`Map`** | Key-Value pairs; keys are unique. | `HashMap` ($O(1)$ hash table, non-synchronized), `LinkedHashMap` (insertion/access order), `TreeMap` (sorted keys), `ConcurrentHashMap` (thread-safe with segment/bucket locking). |

---

## 12. Generics vs Templates

| Feature | Java Generics | C++ Templates |
| :--- | :--- | :--- |
| **Mechanism** | **Type Erasure**: Type parameters are erased at compile time to their bounds (or `Object`). | **Code Expansion (Monomorphization)**: Generates distinct machine code for every type specialization. |
| **Binary Size** | Minimal footprint (one shared class file). | Can lead to code bloat (duplicate code per type). |
| **Primitive Support** | No direct primitives (`List<int>` is invalid; requires `List<Integer>`). | Direct support for primitives (`std::vector<int>`). |
| **Runtime Information** | Type parameters unavailable at runtime (`new T()` is illegal). | Full type info preserved at runtime. |

---

## 13. Wrapper Classes and Autoboxing

Primitives (`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`) have corresponding wrapper classes (`Byte`, `Short`, `Integer`, `Long`, `Float`, `Double`, `Character`, `Boolean`).

### Autoboxing & Unboxing
- **Autoboxing**: Automatic conversion of primitive to wrapper by the compiler.
  ```java
  Integer num = 10; // Internally: Integer.valueOf(10)
  ```
- **Unboxing**: Automatic conversion of wrapper to primitive.
  ```java
  int n = num;      // Internally: num.intValue()
  ```

### Key Gotchas:
- **`NullPointerException`**: Unboxing a `null` wrapper reference throws an NPE at runtime.
- **Integer Cache**: JVM caches values between **-128 to 127** via `Integer.valueOf()`. Comparing cached objects with `==` returns `true`, but beyond 127 returns `false`. Always use `.equals()`!

---

## 14. Lambda Expressions and Stream API

### Lambda Expressions (Java 8+)
- Concise way to provide the implementation of a **Functional Interface** (interface with exactly one abstract method, e.g., `Runnable`, `Comparator`, `Predicate<T>`, `Function<T, R>`).
- Syntax: `(parameters) -> { body }`

### Stream API
- A sequence of elements supporting functional-style pipeline operations (does not mutate original source).
- **Two Operation Types**:
  1. **Intermediate** (Lazy, return new Stream): `.filter()`, `.map()`, `.sorted()`, `.distinct()`, `.limit()`
  2. **Terminal** (Eager, executes pipeline and ends stream): `.collect()`, `.forEach()`, `.reduce()`, `.count()`, `.anyMatch()`

```java
List<String> names = List.of("Anna", "Bob", "Alice", "Charlie");

List<String> result = names.stream()
    .filter(name -> name.startsWith("A"))
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList()); // ["ALICE", "ANNA"]
```

---

## 15. Basic Multithreading

### Creating a Thread
1. **Implement `Runnable`** (Preferred over extending `Thread`):
   ```java
   Thread t = new Thread(() -> System.out.println("Running in: " + Thread.currentThread().getName()));
   t.start(); // Always call start(), not run()!
   ```
2. **Implement `Callable<V>` & `Future`**: Can return values and throw checked exceptions.

### Thread Lifecycle
`New` $\rightarrow$ `Runnable` $\leftrightarrow$ `Blocked` / `Waiting` / `Timed Waiting` $\rightarrow$ `Terminated`

### Core Concurrency Concepts
- **`synchronized`**: Mutual exclusion mechanism using an object's intrinsic monitor lock (prevents race conditions).
- **`volatile`**: Ensures variable reads and writes go directly to main memory (visibility guarantee across threads; does **not** ensure atomicity).
- **`ExecutorService`**: Thread pool manager (`Executors.newFixedThreadPool(4)`) to reuse threads rather than creating expensive raw threads manually.

---

## 16. Method Overriding and the `@Override` Annotation

### What is Method Overriding?
Method Overriding occurs when a subclass defines its own specific implementation of a method that is already declared in its superclass or interface.
- **Enables Runtime Polymorphism (Dynamic Method Dispatch)**: At runtime, JVM calls the version belonging to the actual object in the heap, not the reference type.

### What Does the `@Override` Annotation Do?
`@Override` is a **compiler-checked marker annotation**. It does not change runtime behavior, but provides three crucial safeguards:

1. **Compile-Time Safety & Typo Detection**:
   - Forces the compiler to verify that the method actually exists in the superclass/interface with the **exact same signature**.
   - If you make a typo in the method name or parameters, the compiler immediately throws an error instead of silently treating it as a new overloaded method.
   ```java
   // Without @Override (SILENT BUG: accidental overloading!):
   public boolean equal(Object obj) { ... } // Compiler accepts this, but equals() was NOT overridden!

   // With @Override:
   @Override
   public boolean equal(Object obj) { ... } // COMPILE ERROR: method does not override or implement a method from a supertype
   ```
2. **Protection Against Superclass Refactoring**:
   - If the parent class method signature is changed or deleted, any subclass using `@Override` will fail to compile immediately, alerting you to update the subclass.
3. **Readability & Intent**:
   - Signals explicitly to other developers that this method is fulfilling or modifying a parent/interface contract.

### Rules of Method Overriding:
- **Same Signature**: Same method name, same parameter count, types, and order.
- **Covariant Return Type**: Return type must be identical or a subtype of parent's return type.
- **Access Modifier**: Cannot be more restrictive than parent (`protected` $\rightarrow$ `public` is allowed; `public` $\rightarrow$ `private` is **illegal**).
- **Exceptions**: Can throw fewer or subclass exceptions, but **cannot** throw broader or new checked exceptions.
- **Cannot Override**: `private` (not visible), `static` (method hiding, not overriding), or `final` methods.

### Overriding vs Overloading

| Feature | Method Overriding | Method Overloading |
| :--- | :--- | :--- |
| **Location** | Across inheritance (Parent & Child classes) | Within the same class |
| **Signature** | Must be **identical** | Must have **different parameters** |
| **Return Type** | Must be same or covariant (subtype) | Can be anything |
| **Polymorphism** | **Runtime** (Dynamic Binding) | **Compile-Time** (Static Binding) |

