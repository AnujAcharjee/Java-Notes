// Enums and Records in Java

// =============================================================
// 1. ENUMS (Enumerations - Java 5+)
// =============================================================
// - Special data type used to define a FIXED set of named constants.
// - Type-safe: Prevents passing invalid values (unlike raw int/String constants).
// - Implicitly extends 'java.lang.Enum' (cannot extend any other class, but CAN implement interfaces).
// - Enum constants are implicitly 'public static final'.
// - Can have fields, constructors (MUST be private/package-private), and methods.
// - Built-in methods:
//     - .values()   -> Returns an array of all enum constants.
//     - .ordinal()  -> Returns the zero-based index of the constant.
//     - .valueOf()  -> Converts a String to the matching enum constant.
//     - .name()     -> Returns constant name as String.

// =============================================================
// 2. RECORDS (Java 14/16+)
// =============================================================
// - Concise syntax to create IMMUTABLE data carrier classes (DTOs / POJOs).
// - Eliminates massive boilerplate code! The compiler automatically generates:
//     1. 'private final' fields for each component.
//     2. Canonical constructor (initializes all fields).
//     3. Public accessor methods (named fieldName(), NOT getFieldName()).
//     4. 'equals()' and 'hashCode()' implementations based on all fields.
//     5. Meaningful 'toString()' method.
// - Records are implicitly 'final' (cannot be inherited, and cannot extend other classes).
// - Implicitly extends 'java.lang.Record' (can implement interfaces).
// - Supports "Compact Constructors" for validation and normalization.

public class Seven {

    public static void main(String[] args) {

        // =====================================================
        // PART 1: ENUMS DEMONSTRATION
        // =====================================================
        System.out.println("=== 1. Enums in Action ===");

        OrderStatus currentStatus = OrderStatus.SHIPPED;

        // Using Enum in Switch Statement
        checkStatus(currentStatus);

        // Accessing custom fields and methods on Enum
        System.out.println("Status Description: " + currentStatus.getDescription());
        System.out.println("Status Code:        " + currentStatus.getStatusCode());
        System.out.println("Is order complete?  " + currentStatus.isCompleted());

        // Iterating over all enum constants using .values()
        System.out.println("\nAll Order Statuses:");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(" - " + status.name() + " [Ordinal: " + status.ordinal() + "]");
        }


        // =====================================================
        // PART 2: RECORDS DEMONSTRATION
        // =====================================================
        System.out.println("\n=== 2. Records in Action (Boilerplate-free Data Carrier) ===");

        // 1. Creating Record Instances
        User user1 = new User("anuj_dev", "anuj@example.com", 21);
        User user2 = new User("anuj_dev", "anuj@example.com", 21);
        User user3 = new User("john_doe", "john@example.com", 30);

        // 2. Accessor methods (note: name(), not getName())
        System.out.println("Username: " + user1.username());
        System.out.println("Email:    " + user1.email());
        System.out.println("Age:      " + user1.age());

        // 3. Auto-generated toString()
        System.out.println("\nAuto-generated toString():");
        System.out.println(user1);

        // 4. Auto-generated equals() and hashCode() (Value-based equality!)
        System.out.println("\nValue-based Equality Check:");
        System.out.println("user1.equals(user2): " + user1.equals(user2)); // true (same data)
        System.out.println("user1.equals(user3): " + user1.equals(user3)); // false
        System.out.println("user1 == user2:      " + (user1 == user2));     // false (different heap references)

        // 5. Compact Constructor Validation
        System.out.println("\nCompact Constructor Validation:");
        try {
            User invalidUser = new User("bad_user", "bad@test.com", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("[CAUGHT EXPECTED ERROR] " + e.getMessage());
        }

        // 6. Record with custom methods
        System.out.println("Custom method call: " + user1.getDisplayBadge());
    }

    // Helper method demonstrating switch with enums
    public static void checkStatus(OrderStatus status) {
        switch (status) {
            case PENDING:
                System.out.println("Order received. Awaiting payment.");
                break;
            case PROCESSING:
                System.out.println("Payment confirmed. Packing order.");
                break;
            case SHIPPED:
                System.out.println("Package handed over to courier!");
                break;
            case DELIVERED:
                System.out.println("Package safely delivered to customer.");
                break;
            case CANCELLED:
                System.out.println("Order has been cancelled.");
                break;
        }
    }
}

// -------------------------------------------------------------
// ENUM WITH FIELDS, CONSTRUCTOR, AND METHODS
// -------------------------------------------------------------
enum OrderStatus {
    // Enum constants (calling the private constructor)
    PENDING(100, "Order Placed"),
    PROCESSING(200, "In Fulfillment"),
    SHIPPED(300, "Dispatched & On the Way"),
    DELIVERED(400, "Successfully Delivered"),
    CANCELLED(500, "Cancelled by User/System");

    // Custom fields
    private final int statusCode;
    private final String description;

    // Enum constructors are ALWAYS private (compiler enforces this)
    private OrderStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return this == DELIVERED || this == CANCELLED;
    }
}

// -------------------------------------------------------------
// RECORD DEFINITION (Java 16+)
// -------------------------------------------------------------
// One line generates: private final fields, constructor, getters,
// equals(), hashCode(), and toString()!
record User(String username, String email, int age) {

    // Compact Constructor: Used for validation & data cleaning.
    // Notice: No parameter list or 'this.x = x' needed!
    public User {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        // Normalization (e.g., store email in lowercase)
        email = email.toLowerCase().trim();
    }

    // Custom instance method inside a Record
    public String getDisplayBadge() {
        return "@" + username + " (" + age + " y/o)";
    }

    // Records can also have static fields and static methods
    public static String getEntityName() {
        return "USER_RECORD";
    }
}
