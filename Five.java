// Polymorphism in Java (Core OOP Pillar)

// What is Polymorphism?
// - Greek: "Poly" = Many, "Morph" = Forms -> "Ability to take many forms"
// - Allows one interface/reference to be used for a general class of actions.

// Two Main Types of Polymorphism:
// -------------------------------------------------------------
// 1. Compile-Time Polymorphism (Static Binding / Early Binding)
//    - Mechanism: Method Overloading (and Constructor Overloading)
//    - How: Same method name with DIFFERENT parameter lists (count, type, or order) in same class.
//    - Note: Changing ONLY the return type is NOT valid method overloading.
//    - Resolved: By the compiler at compile-time based on argument types.

// 2. Runtime Polymorphism (Dynamic Binding / Late Binding)
//    - Mechanism: Method Overriding (@Override) & Upcasting
//    - How: Subclass provides a specific implementation of a method declared in its superclass.
//    - Resolved: At runtime by the JVM based on the ACTUAL OBJECT in heap memory,
//      NOT the reference type. This mechanism is called Dynamic Method Dispatch.

// Rules of Method Overriding:
// - Same method name, same parameters, and compatible return type (covariant).
// - Access modifier cannot be more restrictive (e.g., protected -> public is OK, public -> private is ERROR).
// - 'final', 'static', and 'private' methods CANNOT be overridden.

// Upcasting vs Downcasting:
// - Upcasting: Subclass object -> Superclass reference (Automatic & Safe).
// - Downcasting: Superclass reference -> Subclass reference (Requires explicit cast; check with 'instanceof').

public class Five {

    public static void main(String[] args) {

        // =====================================================
        // PART 1: Compile-Time Polymorphism (Method Overloading)
        // =====================================================
        System.out.println("=== 1. Compile-Time Polymorphism (Method Overloading) ===");
        Calculator calc = new Calculator();

        System.out.println("add(int, int):       " + calc.add(10, 20));          // calls add(int, int)
        System.out.println("add(int, int, int):  " + calc.add(10, 20, 30));      // calls add(int, int, int)
        System.out.println("add(double, double): " + calc.add(10.5, 20.2));      // calls add(double, double)


        // =====================================================
        // PART 2: Runtime Polymorphism (Dynamic Method Dispatch)
        // =====================================================
        System.out.println("\n=== 2. Runtime Polymorphism (Method Overriding) ===");

        // Upcasting: Parent reference pointing to Child objects
        Payment payment1 = new CreditCardPayment("1234-5678-9012-3456");
        Payment payment2 = new UpiPayment("anuj@upi");
        Payment payment3 = new CryptoPayment("0x71C...49A");

        // The exact same method call behaves differently depending on the actual object at runtime:
        processTransaction(payment1, 1500.0);
        processTransaction(payment2, 350.0);
        processTransaction(payment3, 5000.0);


        // =====================================================
        // PART 3: Upcasting, Downcasting & 'instanceof'
        // =====================================================
        System.out.println("\n=== 3. Downcasting and 'instanceof' Check ===");

        Payment p = new UpiPayment("user@oksbi"); // Upcasting (Automatic)

        // Downcasting back to child type:
        // Must check with 'instanceof' first to prevent ClassCastException at runtime
        if (p instanceof UpiPayment) {
            UpiPayment upi = (UpiPayment) p; // Explicit Downcasting
            upi.showUpiQrCode();              // Accessing child-specific method
        }
    }

    // Demonstrates the true power of Polymorphism:
    // This single method can accept ANY subclass of Payment without needing to change!
    public static void processTransaction(Payment paymentMethod, double amount) {
        paymentMethod.pay(amount); // JVM calls the child's overridden pay() at runtime
    }
}

// -------------------------------------------------------------
// Compile-Time Polymorphism Example: Method Overloading
// -------------------------------------------------------------
class Calculator {
    // Overloaded method 1: Two integer parameters
    public int add(int a, int b) {
        return a + b;
    }

    // Overloaded method 2: Three integer parameters (different parameter count)
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    // Overloaded method 3: Two double parameters (different parameter types)
    public double add(double a, double b) {
        return a + b;
    }
}

// -------------------------------------------------------------
// Runtime Polymorphism Example: Hierarchy & Method Overriding
// -------------------------------------------------------------
class Payment {
    public void pay(double amount) {
        System.out.println("Processing generic payment of Rs." + amount);
    }
}

class CreditCardPayment extends Payment {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using Credit Card [ends with " 
            + cardNumber.substring(cardNumber.length() - 4) + "]");
    }
}

class UpiPayment extends Payment {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " via UPI ID: " + upiId);
    }

    // Child-specific method
    public void showUpiQrCode() {
        System.out.println("Displaying dynamic QR code for: " + upiId);
    }
}

class CryptoPayment extends Payment {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Transferred Rs." + amount + " equivalent in crypto to wallet: " + walletAddress);
    }
}
