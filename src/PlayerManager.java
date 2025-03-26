import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import team.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class PlayerManager implements MainHelper {

    Scanner scanner = new Scanner(System.in);
    // Req 8.2 Using the Array List to store the list of Player Records
    private static ArrayList<Player> teamMembers = new ArrayList<>();
    // 11.1b  Create an instance of the Settings record
    Settings settings = new Settings("teamMembers.dat", 25,14);

    public static void main(String[] args) {

        // Virtual Threads (Enhanced in Java 23
        //Thread.startVirtualThread(() -> System.out.println("Java 23 Running in a virtual thread! This would be awesome for handling large concurrent scale tasks"));
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
        System.out.print("Enter the age of the new team member: ");
        String playerAge = scanner.nextLine();
        teamMembers.add(new Player(firstName, lastName, playerAge));

        //String teamAgeGroup = switch (playerAge) {
        //    case 1, 2, 3, 4, 5 -> "Weekday";
        //    case 6, 7 -> "Weekend";
        //    default -> "Invalid Day";
        //};

        //System.out.println("Day Type: " + dayType); // Output: Weekday

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

            Player player = new Player(firstLastName[0],firstLastName[1], "30");
            teamMembers.add(player);
            System.out.println("Added " + playerName + " to the team.");
        }
    }

    // OOP2 Req 1.2 Predicate Lambda
    // Method to filter players using a Predicate (lambda)
    // A Predicate takes an input and returns a boolean value.
    // OOP2 Req 1.3 Supplier Lambda
    // A Supplier takes no input and returns a value.
    private static void getForwards()
    {
        // Define a Predicate to find players by position
        Predicate<Player> isForward = player -> player.getPosition().equals("General");

        // Use the Predicate to filter players who are Forwards
        List<String> forwards = filterPlayers(teamMembers, isForward);

        //System.out.println("Forwards: " + forwards);
        Supplier<String> loggingPredicate = () -> "Filtering on Forwards";
        System.out.println(loggingPredicate.get());
    }


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

        Player playerToRemove = new Player(firstName, lastName,"30");
        if (teamMembers.remove(playerToRemove)) {
            System.out.println("Team member removed successfully.");
        } else {
            System.out.println("Team member not found.");
        }
    }
     // OOP2 Req 5 Switch Expressions
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
        //choice = 99; //This is an example of changing a pass by value reference.
        //choices[0] = 11; //This is an example of being able to change this object
    }
    // OOP2 Req 1.1 Consumer Lambda
    // A Consumer accepts an input and performs an operation on it but does not return a value.
    // Using the consumer Lambda to print the menu options
    public void displayMenu()
    {
        System.out.flush();
        Consumer<String> printMenu= message -> System.out.println(" " + message);

        printMenu.accept("1. Manage Teams");

        if (teamMembers != null)
        {

            printMenu.accept("2. Manage Team Attendance");
            printMenu.accept("3. View Team Attendance");
            printMenu.accept("4. Print Team Statistics");
        }
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

        // Java 22 Feature - Improved String Templates

//        System.out.println("\nAttendance for " + date + " (" + type + "):");
        String message = STR."\nAttendance for \{date} , this was a \{type} :";
        System.out.println(message);


        for (Player player : teamMembers) {
            String attended = player.getAttendance(date, type);
            var addString = "";
            if (attended == null) {
               addString = player.getFullName() + ": No record for this date.\n";
            }
            else {
                addString = player.getFullName() + ": " + attended + "\n";
            }
            sb.append(addString);

        }
        String s = sb.toString();
        System.out.println(s);
    }
    // OOP2 Req 2: Streams Terminal Operations
    // This is a good function to demo the Stream operations on the Array List
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

        // Req 2: Streams Terminal Operations
        // Req 2.1: min() - Find youngest player
        Optional<Player> youngestPlayer = teamMembers.stream().min(Comparator.comparing(Player::getAge));
        System.out.println("Youngest Player: " + youngestPlayer.orElse(null));

        // Req 2.2: max() - Find oldest player
        Optional<Player> oldestPlayer = teamMembers.stream().max(Comparator.comparing(Player::getAge));
        System.out.println("Oldest Player: " + oldestPlayer.orElse(null));

        // Req 2.3: count() - Count total players
        long playerCount = teamMembers.stream().count();
        System.out.println("Total Players: " + playerCount);

        // Req 2.4: findAny() - Find any player
        Optional<Player> anyPlayer = teamMembers.stream().findAny();
        System.out.println("Find Any: " + anyPlayer.orElse(null));

        // Req 2.5: findFirst() - Find the first player in the list
        Optional<Player> firstPlayer = teamMembers.stream().findFirst();
        System.out.println("Find First: " + firstPlayer.orElse(null));

        // Req 2.6: allMatch() - Check if all players are older than 18
        boolean allAbove18 = teamMembers.stream().allMatch(p -> p.getAge() > 18);
        System.out.println("All Players Above 18: " + allAbove18);

        // Req 2.7: anyMatch() - Check if any player is above 30
        boolean anyAbove30 = teamMembers.stream().anyMatch(p -> p.getAge() > 30);
        System.out.println("Any Player Above 30: " + anyAbove30);

        // Req 2.8: noneMatch() - Check if no player is younger than 20
        boolean noBelow20 = teamMembers.stream().noneMatch(p -> p.getAge() < 20);
        System.out.println("No Player Below 20: " + noBelow20);

        // Req 2.9: forEach() - Print all players
        System.out.println("Team Members:");
        teamMembers.stream().forEach(System.out::println);

        // Req 3: collect() - Collect player ages into a List
        List<Integer> playerAges = teamMembers.stream()
                .map(Player::getAge)
                .collect(Collectors.toList());
        System.out.println("Collected Ages: " + playerAges);

        // Req 3.1: Collectors.toMap() - Convert Players to a Map (Last Name -> Age)
        Map<String, Integer> playerAgeMap = teamMembers.stream()
                .collect(Collectors.toMap(Player::getLastName, Player::getAge, (age1, age2) -> age1)); // Handle duplicate last names
        System.out.println("Player Age Map (Last Name -> Age): " + playerAgeMap);

        // Req 3.2: Collectors.groupingBy() - Group players by age group (<25, >=25)
        Map<Boolean, List<Player>> ageGroups = teamMembers.stream()
                .collect(Collectors.groupingBy(p -> p.getAge() >= 25));
        System.out.println("Grouped by Age (>=25 and <25): " + ageGroups);

        // Req 3.3: Collectors.partitioningBy() - Partition players into young (<26) and old (>=26)
        Map<Boolean, List<Player>> partitionedByAge = teamMembers.stream()
                .collect(Collectors.partitioningBy(p -> p.getAge() < 26));
        System.out.println("Partitioned by Age (<26 and >=26): " + partitionedByAge);


        // Req 4: Streams Intermediate Operations
        // Req 4.1: filter() - Get players older than 24
        List<Player> filteredByAge = teamMembers.stream()
                .filter(p -> p.getAge() > 24)
                .collect(Collectors.toList());
        System.out.println("Filtered Players (Age > 24): " + filteredByAge);

        // Req 4.2: distinct() - Get distinct ages
        List<Integer> distinctAges = teamMembers.stream()
                .map(Player::getAge)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Distinct Ages: " + distinctAges);

        // Req 4.3: limit() - Get first 3 oldest players
        List<Player> limitedPlayers = teamMembers.stream()
                .sorted(Comparator.comparing(Player::getAge).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 Oldest Players: " + limitedPlayers);

        // Req 4.4: map() - Convert players to age next year, can they still play u21 :)
        List<Integer> mappedAges = teamMembers.stream()
                .map(p -> p.getAge() +1)
                .collect(Collectors.toList());
        System.out.println("Mapped (Ages Next Year): " + mappedAges);

        // Req 4.5: sorted() - Sort players by age
        List<Player> sortedByAge = teamMembers.stream()
                .sorted(Comparator.comparing(Player::getAge))
                .collect(Collectors.toList());
        System.out.println("Sorted by Age: " + sortedByAge);

        scanner.nextLine();
        //String wait = scanner.nextLine();
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