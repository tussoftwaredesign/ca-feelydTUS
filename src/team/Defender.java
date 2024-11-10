package team;

// Forward class as a permitted subclass of PlayPosition
public final class Defender extends PlayPosition {

    @Override
    public String expertise() {
        return "Awesome at stopping goals";
    }
}
