package team;
// Req 15.1 Sealed classes
// Define the sealed class Shape with specific permitted subclasses
public sealed abstract class PlayPosition
        permits Forward, Midfielder, Defender {

    public abstract String expertise();
}

