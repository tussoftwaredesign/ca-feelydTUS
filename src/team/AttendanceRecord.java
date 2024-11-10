package team;
import java.io.Serializable;

// Req 6.1 Enum Definition and Usage
// Req 12.1 •	Creating Immutable Objects
// Once the Attendance records has been created it cannot be changed , it's final
// only setters methods exist for the variables and the variables are marked final
public class AttendanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    final String date;
    final boolean attended;
    private final AttendanceType type;

// Constructor
public AttendanceRecord(String date, boolean attended, AttendanceType type) {
    this.date = date;
    this.attended = attended;
    this.type = type;
}

// Getter for Date
public String getDate() {
    return this.date;
}

public String displayAttendance() {
    return "Attendance on " + this.date + ": " + this.attended;
}

// Getter for Match Record
public boolean getAttended() {
    return this.attended;
}

public AttendanceType getType() {
    return this.type;
}

}
