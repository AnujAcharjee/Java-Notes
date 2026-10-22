// Interfaces in Java

// What is an Interface?
// - A 100% abstract contract specifying WHAT a class must do, not HOW it does it.
// - Declared with the 'interface' keyword; classes use 'implements'.
// - Solves the Multiple Inheritance problem in Java (a class can implement MULTIPLE interfaces).

// Rules & Features of Interfaces:
// 1. Fields: Implicitly 'public static final' (constants). Must be initialized.
// 2. Abstract Methods: Implicitly 'public abstract' (until Java 7, only abstract methods allowed).
// 3. Default Methods (Java 8+): Concrete methods with a default implementation using 'default' keyword.
//    - Allows adding new methods to interfaces without breaking existing implementing classes!
// 4. Static Methods (Java 8+): Utility methods belonging to the interface itself (InterfaceName.method()).
// 5. Private Methods (Java 9+): Helper methods shared among default methods (hidden internally).
// 6. No Constructors: Interfaces CANNOT be instantiated directly (cannot do 'new Interface()').

// Types of Interfaces:
// - Normal Interface: Two or more abstract methods.
// - Functional Interface (SAM): Exactly ONE abstract method; marked with @FunctionalInterface (powers Lambdas).
// - Marker Interface: Zero methods; used to tag classes for JVM/frameworks (e.g., Serializable, Cloneable).

public class Six {

    public static void main(String[] args) {
        System.out.println("=== 1. Multiple Interface Implementation ===");
        SmartWatch watch = new SmartWatch("Apple Watch Ultra");

        // Methods from AudioPlayer interface
        watch.play();
        watch.pause();
        watch.stop(); // Uses the default method from interface!

        // Method from GpsTracker interface
        watch.trackLocation();

        System.out.println("\n=== 2. Interface Constants & Static Methods ===");
        // Accessing implicit 'public static final' constant
        System.out.println("Default Volume: " + AudioPlayer.DEFAULT_VOLUME);

        // Calling static utility method directly from Interface
        AudioPlayer.displayAudioInfo();

        System.out.println("\n=== 3. Polymorphism via Interface Reference ===");
        // "Program to an interface, not an implementation"
        AudioPlayer player = new SmartWatch("Galaxy Watch");
        player.play();
        // player.trackLocation(); // COMPILE ERROR: AudioPlayer reference does not know about GpsTracker!

        System.out.println("\n=== 4. Functional Interface with Lambda Expression ===");
        // Functional interface with exactly 1 abstract method can use lambda:
        Notifier notifier = (message) -> System.out.println("[ALERT] " + message);
        notifier.notifyUser("Battery is low at 15%!");
    }
}

// -------------------------------------------------------------
// Interface 1: AudioPlayer
// -------------------------------------------------------------
interface AudioPlayer {
    // 1. Constant (implicitly: public static final)
    int DEFAULT_VOLUME = 50;

    // 2. Abstract Methods (implicitly: public abstract)
    void play();
    void pause();

    // 3. Default Method (Java 8+): Optional to override in child classes
    default void stop() {
        logAction("Stopping playback."); // calls private helper
        System.out.println("Playback stopped (Default Interface Implementation).");
    }

    // 4. Static Method (Java 8+): Belongs to interface itself
    static void displayAudioInfo() {
        System.out.println("Audio Standard: Lossless Hi-Fi 24-bit/192kHz supported.");
    }

    // 5. Private Helper Method (Java 9+): Used to reduce code duplication inside default methods
    private void logAction(String action) {
        System.out.println("[AUDIOPLAYER LOG] " + action);
    }
}

// -------------------------------------------------------------
// Interface 2: GpsTracker
// -------------------------------------------------------------
interface GpsTracker {
    void trackLocation();
}

// -------------------------------------------------------------
// Multiple Inheritance via Interfaces
// A class can implement as many interfaces as needed!
// -------------------------------------------------------------
class SmartWatch implements AudioPlayer, GpsTracker {
    private String model;

    public SmartWatch(String model) {
        this.model = model;
    }

    // Implementing AudioPlayer's abstract methods
    @Override
    public void play() {
        System.out.println(model + " is streaming music...");
    }

    @Override
    public void pause() {
        System.out.println(model + " paused music playback.");
    }

    // Implementing GpsTracker's abstract method
    @Override
    public void trackLocation() {
        System.out.println(model + " GPS: 28.6139° N, 77.2090° E (Connected).");
    }

    // Note: stop() from AudioPlayer does not HAVE to be overridden because it has a default implementation!
}

// -------------------------------------------------------------
// Functional Interface (SAM = Single Abstract Method)
// -------------------------------------------------------------
@FunctionalInterface
interface Notifier {
    void notifyUser(String message); // Exactly 1 abstract method
}
