import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import team.*;
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class PlayerManager implements MainHelper {

    Scanner scanner = new Scanner(System.in);
    // Req 8.2 Using the Array List to store the list of Player Records
    private static ArrayList<Player> teamMembers = new ArrayList<>();
    // 11.1b  Create an instance of the Settings record
    Settings settings = new Settings("c:\\AIDATA\\teamMembers.dat", 25,14);
   // private final FileManager fileManager = new FileManager();

    public static void main(String[] args) {

        PlayerManager  playerManger = new PlayerManager();
        playerManger.start();
    }

    // Req 1.4 LVTI
    // Req 7.1a Array  Creation and Manipulation
    public void start() {

        // Calling default greeting
        this.showGreeting();
        teamMembers = FileManager.loadTeamMembers(settings.fileName());

        var choice = 0;
        int[] choices = {1,2,3,4,5};
        while (true) {
            displayMenu();
            // Req 5.1 Unchecked Exceptions
            try {
                choice = scanner.nextInt();
                handleMenuChoice(choice,choices);
                System.out.println("Your last choice " + choice);
                System.out.println("Avaliable choices " + Arrays.toString(choices));
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("Error: Please enter a valid number using these options" + Arrays.toString(choices));
                scanner.next();  // Clear the invalid input
            }
        }
    }

    // Req 7.1 Array Creations and Manipulation
    // I amd using the Array List to add / remove the list of Player Records
    // Method to add a new team member
    private static void addTeamMember() {
        Scanner scanner = new Scanner(System.in);
        if (teamMembers == null)
        {
            teamMembers = new ArrayList<>();
        }
        System.out.print("Enter the first name of the new team member: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter the last name of the new team member: ");
        String lastName = scanner.nextLine();
        teamMembers.add(new Player(firstName, lastName));
        System.out.println("Team member added successfully.");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    // Method to add a new team member
    private static void addMultipleTeamMember() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter multiple names of the new team member, firstname lastname , seperated by commas :");
        String name = scanner.nextLine();
        String[] playerNames = name.split(",");

        addMultipleMembers(playerNames);
    }

    // Req 1.3 VarArgs
   private static void addMultipleMembers(String... playerNames) {
        for (String playerName : playerNames) {
            // We will assume the first name is provided; last name will be optional
            String[] firstLastName = playerName.split(" ");

            Player player = new Player(firstLastName[0],firstLastName[1]);
            teamMembers.add(player);
            System.out.println("Added " + playerName + " to the team.");
        }
    }

    // Req 13.1 Method to filter players using a Predicate (lambda)
    private static void getForwards()
    {
        // Define a Predicate to find players by position
        Predicate<Player> isForward = player -> player.getPosition().equals("General");

        // Use the Predicate to filter players who are Forwards
        List<String> forwards = filterPlayers(teamMembers, isForward);

        System.out.println("Forwards: " + forwards);
    }

    // Req 13.1 Method to filter players using a Predicate (lambda)
    public static List<String> filterPlayers(List<Player> players, Predicate<Player> condition) {
        List<String> result = new ArrayList<>();
        for (Player player : players) {
            if (condition.test(player)) {
                result.add(player.getFullName());
            }
        }
        return result;
    }

    // Method to view all team members
    private static void viewTeamMembers() {
        Scanner scanner = new Scanner(System.in);

        if (teamMembers.isEmpty()) {
            System.out.println("No team members found.");
        } else {
            System.out.println("\nTeam Members:");
            for (int i = 0; i < teamMembers.size(); i++) {
                Player playerrecord = teamMembers.get(i);
                List<AttendanceRecord> playerattendanceRecords = playerrecord.getAttendanceRecords();
                var counter = playerattendanceRecords.stream().count();
                System.out.println((i + 1) + ". " + teamMembers.get(i).getProfile(true) + " Records: " + counter);
            }

        }
        scanner.nextLine();
    }

    // Method to remove a team member
    private static void removeTeamMember() {
        Scanner scanner = new Scanner(System.in);

        if (teamMembers.isEmpty()) {
            System.out.println("No team members to remove.");
            return;
        }

        System.out.print("Enter the first name of the team member to remove: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter the last name of the team member to remove: ");
        String lastName = scanner.nextLine();

        Player playerToRemove = new Player(firstName, lastName);
        if (teamMembers.remove(playerToRemove)) {
            System.out.println("Team member removed successfully.");
        } else {
            System.out.println("Team member not found.");
        }
    }
     // Req 14.1 Switch Expressions
    // Below is an example of a switch expression, used for branching depending on the input variable
    public void handleSubMenuChoice(int choice) {

        switch (choice) {
            case 1:
                System.out.println("1. Add a player");
                addTeamMember();
                break;
            case 2:
                System.out.println("2. View all players");
                viewTeamMembers();
                getForwards();
                break;
            case 3:
                System.out.println("3. Remove a player");
                removeTeamMember();
                break;
            case 4:
                System.out.println("4. Add multiple Player Records");
                addMultipleTeamMember();
                break;
            default:
                break;

        }
    }
    // Req 9.1 Call-by-Value
    public void handleMenuChoice(int choice, int[] choices)
    {
        switch (choice) {
            case 1:
                System.out.println("Manage Players");
                System.out.println("1. Add a Player Record");
                System.out.println("2. View all Player Record");
                System.out.println("3. Remove a Player Record");
                System.out.println("4. Add multiple Player Records");
                int subchoice = scanner.nextInt();
                handleSubMenuChoice(subchoice);
                break;
            case 2:
                System.out.println("2. Manage Training Attendance");
                markAttendance(scanner);
                break;
            case 3:
                System.out.println("3. View Team Attendance");
                viewAttendance(scanner);
                break;
            case 4:
                System.out.println("4. Print Team Statistics");
                viewStatistics(scanner);
                break;
            case 5:
                System.out.println("5. Exiting System .... ");
                FileManager.saveTeamMembers(teamMembers);
                // Calling say goodbye
                MainHelper.showFarewell();

                System.exit(0);
        }
        // choice = 99; This is an example of changing a pass by value reference.
        // choices[0] = 11; This is an example of being able to change this object
    }

    public void displayMenu()
    {
        System.out.flush();

        System.out.println("1. Manage Teams");
        System.out.println("2. Manage Team Attendance");
        System.out.println("3. View Team Attendance");
        System.out.println("4. Print Team Statistics");
        System.out.println("5. Exit System");
    }

    // Req 8.3 Using the Date API to display todays date and also format it to the right pattern
    private static void markAttendance(Scanner scanner) {
        if (teamMembers.isEmpty()) {
            System.out.println("No team members found.");
            return;
        }
        scanner.nextLine(); // this moves onto next line after a number was input
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        System.out.print("Enter the date of the attendance record YYYY-MM-DD ( Default " + dtf.format(now) + " ): ");

        String date = scanner.nextLine();
        // Check if the user entered an empty string
        if (date.isEmpty()) {
            date = dtf.format(now);  // Use default if input is empty
        }

        AttendanceType type = getAttendanceType(scanner);
        if (type == null) return; // If the user entered an invalid type
        System.out.print("Enter the location for training or rivals in the case of a match");
        String information = scanner.nextLine();


        for (Player player : teamMembers) {
            System.out.print("Did " + player.getFullName() + " attend on " + date + "? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            boolean attended = response.equals("yes") || response.equals("y") ;
            player.markAttendance(date, attended, type, information);
        }
        System.out.println("Attendance marked for all players.");
    }

    // Req 8.1 Using the String Builder Object to create attendance report
    private static void viewAttendance(Scanner scanner) {
        if (teamMembers.isEmpty()) {
            System.out.println("No team members found.");
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        System.out.print("Enter the date to view attendance YYYY-MM-DD ( Default " + dtf.format(now) + " ): ");

        String date = scanner.nextLine();
        // Check if the user entered an empty string
        if (date.isEmpty()) {
            date = dtf.format(now);  // Use default if input is empty
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to the View Player Stats Report\n");
        sb.append("_______________________________________\n\n ");

        scanner.nextLine();

        AttendanceType type = getAttendanceType(scanner);
        if (type == null) return;

        System.out.println("\nAttendance for " + date + " (" + type + "):");
        for (Player player : teamMembers) {
            String attended = player.getAttendance(date, type);
            if (attended == null) {
                //System.out.println(player.getFullName() + ": No record for this date.");
                sb.append(player.getFullName() + ": No record for this date.\n");
            } else {
                sb.append(player.getFullName() + ": " + attended + "\n");
                //System.out.println(player.getFullName() + ": " + (attended ? "Present" : "Absent"));
            }

        }
        String s = sb.toString();
        System.out.println(s);
    }

    private static void viewStatistics(Scanner scanner) {
        if (teamMembers.isEmpty()) {
            System.out.println("No team members found.");
            return;
        }

        System.out.println("\nAttendance for Players");
        for (Player player : teamMembers) {
            int attendedTraining = player.getAttendance(AttendanceType.TRAINING);
            int attendedMatches = player.getAttendance(AttendanceType.MATCH);

            System.out.println(player.getFullName() + " Matches : " + attendedMatches + " TRAINING: " + attendedTraining);

        }
        scanner.nextLine();
        String wait = scanner.nextLine();
    }

    private static AttendanceType getAttendanceType(Scanner scanner) {
        System.out.print("Enter attendance type (MATCH/TRAINING): ");
        String typeInput = scanner.nextLine().trim().toUpperCase();

        try {
            return AttendanceType.valueOf(typeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid attendance type. Please enter either MATCH or TRAINING.");
            return null;
        }
    }


}