package team;

// OOP2 Req 6 - Private Interface Example only visible within the Interface
// OOP2 Req 6 - Default Interface Method - provide a way to add new functionality to an interface without breaking the existing implementations.
// OOP2 Req 6 - Static Inteface Method - accessed directly in the interface, which can be accessed without creating an instance of the implementing clas
public interface MainHelper {

    // Default method that uses private method
    default void showGreeting() {
        printMessage("Welcome to the Team Management App!");
    }

    // Static method that also uses private method
    static void showFarewell() {
        printMessage("Goodbye from Team Management App!");
    }

    // Private method - only accessible within this interface
    private static void printMessage(String message) {
        System.out.println(message);
    }
}
