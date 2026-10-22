// Access Modifiers and their types
// Encapsulation

// What is Encapsulation?
// 1. Bundling data (variables) and methods operating on that data into a single unit (class).
// 2. Data Hiding: Declaring fields as 'private' so external classes cannot directly access or corrupt them.
// 3. Controlled Access: Providing 'public' getters (read) and setters (write with validation).
// 4. Benefits: Protects data integrity (e.g., no negative balance), allows read-only fields,
//    and decouples internal implementation from external code.

// Access Levels in Java:
// 1. private   - accessible ONLY inside the same class (strongest restriction / Data Hiding)
// 2. default   - (no keyword) accessible within the SAME PACKAGE (package-private)
// 3. protected - accessible in the SAME PACKAGE + SUBCLASSES (even in different packages)
// 4. public    - accessible from EVERYWHERE (any class, any package)

// Quick Visibility Matrix:
// Modifier    | Same Class | Same Package | Subclass (diff pkg) | World
// private     |    Yes     |      No      |         No          |  No
// default     |    Yes     |      Yes     |         No          |  No
// protected   |    Yes     |      Yes     |         Yes         |  No
// public      |    Yes     |      Yes     |         Yes         |  Yes

public class Three {

    public static void main(String[] args) {
        BankAccount account = new BankAccount("Anuj", 101, 50000.0, "SEC12345");

        // 1. public field: accessible directly from anywhere
        System.out.println("Holder Name (public): " + account.accountHolder);

        // 2. default field: accessible because Three and BankAccount are in the same package
        System.out.println("Account Number (default): " + account.accountNumber);

        // 3. protected field: accessible because Three is in the same package
        System.out.println("Routing Code (protected): " + account.routingCode);

        // 4. private field: NOT directly accessible from outside BankAccount
        // account.balance = 100000.0; // COMPILE ERROR: balance has private access in BankAccount

        // Encapsulation in action:
        // 1. Safe read access via getter
        System.out.println("Initial Balance (Getter): " + account.getBalance());

        // 2. Safe write access via setter/business method with validation
        account.deposit(5000.0);
        System.out.println("Balance after deposit: " + account.getBalance());

        // 3. Encapsulation protects against invalid state changes (validation logic):
        account.withdraw(100000.0); // Rejected: exceeds balance!
        account.withdraw(-500.0);   // Rejected: negative amount!
        account.withdraw(15000.0);  // Allowed
        System.out.println("Final Balance: " + account.getBalance());

        // Methods with different access modifiers
        account.displayPublicInfo();
        account.displayPackageInfo();
        // account.logTransaction("test"); // COMPILE ERROR: logTransaction() is private
    }
}

class BankAccount {
    public String accountHolder;

    int accountNumber;

    protected String routingCode;

    // Encapsulated field: private prevents direct tampering (e.g., account.balance = -9999)
    private double balance;

    // Constructor
    public BankAccount(String accountHolder, int accountNumber, double balance, String routingCode) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.routingCode = routingCode;
    }

    // Getter: Controlled read-only access to private data
    public double getBalance() {
        return this.balance;
    }

    // Business method with validation: Protects data integrity
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            logTransaction("Deposited: " + amount);
        } else {
            System.out.println("[ERROR] Deposit amount must be positive!");
        }
    }

    // Business method with validation: Prevents unauthorized overdrafts
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be positive!");
        } else if (amount > this.balance) {
            System.out.println("[ERROR] Insufficient funds! Current balance: " + this.balance);
        } else {
            this.balance -= amount;
            logTransaction("Withdrew: " + amount);
        }
    }

    // private helper method: only callable within this class
    private void logTransaction(String message) {
        System.out.println("[LOG] " + message);
    }

    // public method: callable from anywhere
    public void displayPublicInfo() {
        System.out.println("Account Holder: " + accountHolder);
    }

    // default (package-private) method: callable only within the same package
    void displayPackageInfo() {
        System.out.println("Account: " + accountNumber + " | Routing: " + routingCode);
    }
}
