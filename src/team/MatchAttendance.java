package team;
// Req 4.1a Inheritance
// Req 13.2 final and final()
public  final class MatchAttendance extends AttendanceRecord {
    private  final String opponent;
    private int effectiveFinal;
    // Req 4.2 Super() and Super. Super()
    public MatchAttendance(String date, boolean attended, String opponent) {
        super(date, attended , AttendanceType.MATCH);  // Call to superclass constructor
        this.opponent = opponent;
        effectiveFinal = 42; // Only time this variable is changed. Not marked as final but behaviing like it is.
    }

    @Override
    public AttendanceType getType() {
        // Req 4.2 Super() and Super. Super.
        // Accesses superclass methods or fields , called the parents method in this case , even though we;ve overridden it
        return super.getType();  // Calls the superclass's getType
    }

    @Override
    public String displayAttendance() {
        return "Match against " + opponent + " on " + date + ": " + (attended ? "Attended" : "Did not attend");
    }
}
