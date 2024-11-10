package team;

// Req15.1 Sealed classes
// Forward class as a permitted subclass of PlayPosition
public final class Forward extends PlayPosition {
   
    @Override
    public String expertise() {
        return "Awesome at Scoring Goals";
    }
}
