// Miscellaneous Core Java: Sealed Classes & Pattern Matching

// =============================================================
// 1. SEALED CLASSES & INTERFACES (Java 17 LTS)
// =============================================================
// - Problem solved: Previously, inheritance had only two extremes:
//     - Wide open: Any class could extend it.
//     - Completely closed: 'final' keyword blocked ALL inheritance.
// - Solution: 'sealed' classes allow RESTRICTED INHERITANCE.
//     A superclass explicitly specifies which classes are PERMITTED to extend it.
// - Syntax:
//     sealed class Shape permits Circle, Rectangle, Triangle { }
//
// Subclass Constraint Rule:
// Every permitted subclass MUST explicitly specify ONE of three modifiers:
// 1. 'final'      -> Cannot be extended further (closes that branch).
// 2. 'sealed'     -> Continues sealing, must specify its own 'permits' list.
// 3. 'non-sealed' -> Re-opens that specific branch to any class.
//
// Key Benefit -> Exhaustiveness in Switch (Pattern Matching):
// Because the compiler knows ALL possible subtypes, you do NOT need a 'default' case!

// =============================================================
// 2. PATTERN MATCHING FOR 'instanceof' (Java 16+)
// =============================================================
// Eliminates the redundant cast boilerplate:
// Old way:
//    if (obj instanceof String) {
//        String s = (String) obj; // Manual casting
//        System.out.println(s.length());
//    }
// Modern way:
//    if (obj instanceof String s) {
//        System.out.println(s.length()); // Auto-bound variable 's'
//    }

public class Eight {

    public static void main(String[] args) {
        System.out.println("=== 1. Sealed Classes & Hierarchy ===");

        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape square = new Square(4.0); // Extends non-sealed Rectangle

        System.out.println("Circle area:    " + circle.calculateArea());
        System.out.println("Rectangle area: " + rectangle.calculateArea());
        System.out.println("Square area:    " + square.calculateArea());


        System.out.println("\n=== 2. Exhaustive Switch with Sealed Classes ===");
        printShapeDescription(circle);
        printShapeDescription(rectangle);
        printShapeDescription(square);


        System.out.println("\n=== 3. Pattern Matching for 'instanceof' ===");
        processEntity("Antigravity IDE");
        processEntity(100);
        processEntity(circle);


        System.out.println("\n=== 4. Sealed Interfaces with Records ===");
        ServiceResponse success = new SuccessResponse("Data loaded successfully!");
        ServiceResponse failure = new FailureResponse(404, "Resource not found");

        handleResponse(success);
        handleResponse(failure);
    }

    // Modern Switch Expression with Sealed Hierarchy
    // Notice: Compiler knows Circle and Rectangle are the ONLY direct subtypes of Shape!
    public static void printShapeDescription(Shape shape) {
        switch (shape) {
            case Circle c -> System.out.println("It's a Circle with radius: " + c.getRadius());
            case Rectangle r -> System.out.println("It's a Rectangle of width: " + r.getWidth() + ", height: " + r.getHeight());
            // No 'default' branch needed because Shape is sealed and exhaustive!
        }
    }

    // Pattern Matching for instanceof
    public static void processEntity(Object obj) {
        if (obj instanceof String str) {
            // 'str' is automatically in scope and casted!
            System.out.println("Found a String: \"" + str.toUpperCase() + "\" (Length: " + str.length() + ")");
        } else if (obj instanceof Integer num) {
            System.out.println("Found an Integer: " + (num * 2));
        } else if (obj instanceof Shape s) {
            System.out.println("Found a Shape with area: " + s.calculateArea());
        } else {
            System.out.println("Unknown entity type: " + obj);
        }
    }

    // Handling Sealed Interface
    public static void handleResponse(ServiceResponse response) {
        switch (response) {
            case SuccessResponse s -> System.out.println("[SUCCESS 200] " + s.message());
            case FailureResponse f -> System.out.println("[ERROR " + f.errorCode() + "] " + f.errorMessage());
        }
    }
}

// -------------------------------------------------------------
// SEALED CLASS HIERARCHY
// -------------------------------------------------------------
// Base sealed class: Only Circle and Rectangle are permitted to extend it.
sealed abstract class Shape permits Circle, Rectangle {
    public abstract double calculateArea();
}

// Subclass Option 1: 'final' (Closes inheritance for Circle)
final class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Subclass Option 2: 'non-sealed' (Re-opens inheritance for Rectangle)
non-sealed class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

// Since Rectangle is 'non-sealed', Square is allowed to extend it freely!
class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}

// -------------------------------------------------------------
// SEALED INTERFACES (Commonly paired with Records!)
// -------------------------------------------------------------
// Records are implicitly final, making them perfect permitted subtypes!
sealed interface ServiceResponse permits SuccessResponse, FailureResponse {
}

record SuccessResponse(String message) implements ServiceResponse {
}

record FailureResponse(int errorCode, String errorMessage) implements ServiceResponse {
}
