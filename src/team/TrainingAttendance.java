package team;

// Req 4.1a
// Inheritance: MatchAttendance and TrainingAttendance classes inherit from the Base class AttendanceRecord superclass
// acquiring common properties (date, attended type) and methods (e.g., displayAttendance).
// Method Overriding: Both subclasses override getRecordType and displayAttendance.
// getRecordType provides a specific attendance type ("Match Attendance" or "Training Attendance").
// displayAttendance displays details specific to each attendance type, either mentioning the opponent (for matches) or the training location (for training).
// Req 12.1 create an immutable Training Attendance class
// this class will hold the teams opponent

public  final  class TrainingAttendance extends AttendanceRecord {
    private final  String trainingLocation;

    public TrainingAttendance(String date, boolean attended, String trainingLocation) {
        super(date, attended , AttendanceType.TRAINING);  // Call to superclass constructor
        this.trainingLocation = trainingLocation;
    }

    @Override
    public AttendanceType getType() {
        return AttendanceType.TRAINING;
    }

    @Override
    public String displayAttendance() {
        return "Training in " + trainingLocation + " on " + date + ": " + (attended ? "Attended" : "Did not attend");
    }
}
