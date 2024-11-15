package team;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "teamMembers.dat";

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
    @SuppressWarnings("unchecked")
    public static ArrayList<Player> loadTeamMembers(String fileName)
    {
        ArrayList<Player> teamMembers = new ArrayList<>();
        File file = new File(fileName);

        // This will create a file if it doesn't exist
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //try {
        //    System.out.println(file.getCanonicalPath());
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
                teamMembers = (ArrayList<Player>) in.readObject();
                System.out.println("Loading team information .... ");
                return teamMembers;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Empty team members application, start by adding players:" + e.getMessage());
            }
        }
        return null;
    }
}
