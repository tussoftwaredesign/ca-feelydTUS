package team;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "c:\\AIDATA\\teamMembers.dat";

    public static void saveTeamMembers(ArrayList<Player> teamMembers)    {
        File file = new File(FILE_NAME);

        if(file.exists()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
                out.writeObject(teamMembers);
                System.out.println("Saving team information .... ");
            }
            catch (IOException e)
            {
                System.out.println("Error saving team members:" + e.getMessage());
            }
        }
    }

    // Req 5.2 Handling Checked Exceptions
    public static ArrayList<Player> loadTeamMembers()
    {
        ArrayList<Player> teamMembers = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                teamMembers = (ArrayList<Player>) in.readObject();
                System.out.println("Loading team information .... ");
                return teamMembers;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading team members:" + e.getMessage());
            }
        }
        return null;
    }
}
