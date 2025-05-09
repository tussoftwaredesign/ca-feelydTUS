package team;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Req 2.2 Use of Private access Modifiers Encapsulation
// Req 3.1a Private access Modifiers Also Interface Implemented
// Req 7.1 Array List to store the list of Attendance Records
public final class Player implements Serializable , PlayerReport {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String teamPosition;
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    private PlayPosition position;

    // Declare the Lamda Expression
    private transient PlayerReport printFullName = (firstname, lastname, teamPosition) -> firstName + " " + lastName ;
    private transient PlayerReport printFullNamePosition = (firstname, lastname, teamPosition) -> firstName + " " + lastName + " " + teamPosition;

    // Req 1.1 this() and this.
    public Player(String firstName, String lastName) {

        // Assigns the value of local firstname and a last name variable
        // to the firstname and lastname (instance variable).
        this("General");
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Player(String teamPosition) {

        // Assigns the value of local firstname and a last name variable
        // to the firstname and lastname (instance variable).
        this.teamPosition = teamPosition;
    }

    // Req 2.1 Use of Gettings and Setters
    // Getter for full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getter for first name
    public String getFirstName() {
        return this.firstName;
    }

    // Getter for first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for Last name
    public String getLastName() {
        return this.lastName;
    }

    // Getter for Last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter for Position
    public String getPosition() {
        return this.teamPosition;
    }

    // Setter for Position
    public void setPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }


    // Req 1.2 Method Overloading.
    public String getProfile() {
        // use the Lambda Express
        return printFullName.playerDetails(firstName, lastName, teamPosition);
    }
    // Overloaded method for player profile to get full name and team position if required
    public String getProfile(boolean withPosition) {
        return firstName + " " + lastName+ " " + teamPosition;
    }
    // Overloaded method for player profile to get full name , team position if required
    public String getProfile(boolean withPosition, boolean withStatistics) {
        int matches = this.getAttendance(AttendanceType.MATCH);
        int training = this.getAttendance(AttendanceType.MATCH);

        return firstName + " " + lastName+ " " + teamPosition + " Matches: " + matches + " Training:" + training;
    }

    public void markAttendance(String date, boolean attended, AttendanceType type, String information)
    {
        if (attendanceRecords == null)
        {
            attendanceRecords = new ArrayList<>();
        }
        if (type == AttendanceType.TRAINING) {
            attendanceRecords.add(new TrainingAttendance(date, attended,information));
        }
        if (type == AttendanceType.MATCH) {
            attendanceRecords.add(new MatchAttendance(date, attended, information));
        }
    }
    // Req 4.1b
    // When iterating through attendanceRecords, calling displayAttendance triggers the overridden
    // method appropriate for each object’s actual type (either MatchAttendance or TrainingAttendance).
    public String getAttendance(String date, AttendanceType type)
    {
        if (attendanceRecords == null)
        {
            attendanceRecords = new ArrayList<>();
        }
        for(AttendanceRecord record : attendanceRecords)
        {
            if (record.getDate().equals(date) && record.getType()==type)
            {
                return record.displayAttendance();
            }
            // Req 14.2 Pattern Matching
            // Pattern matching with instanceof
            if (record instanceof MatchAttendance attendanceType) { // automatically casts to Match Attendance
                String logging = "Getting Match Attendance"; // Log at a later time
            }
            // Pattern matching with instanceof
            if (record instanceof TrainingAttendance attendanceType) { // automatically casts to Match Attendance
                String logging = "Getting Training Attendance"; // Log at a later time
            }
        }

        return null;
    }

    // operator overloading
    public int getAttendance(AttendanceType type)
    {
        int counter = 0;

        if (attendanceRecords == null)
        {
            attendanceRecords = new ArrayList<>();
        }
        for(AttendanceRecord record : attendanceRecords)
        {
            if (record.getType()==type)
            {
                counter = counter + 1;
            }
        }

        return counter;
    }

    // Reporting
    // Getter for full name
    public String getReportHeader() {
        return firstName + " " + lastName;
    }


    // Req 9.2 Defensive copying
    public List<AttendanceRecord> getAttendanceRecords()
    {
        // Defensive copy of the array to prevent external modification
        return List.copyOf(attendanceRecords);
    }
    // Override equals method to compare Player objects by first and last name
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return firstName.equals(player.firstName) && lastName.equals(player.lastName);
    }

    // Req 3.1c PLayer Report Interface method playerDetails implements
    @Override
    public String playerDetails(String firstname, String lastname, String playerPosition) {
        return "";
    }
}
