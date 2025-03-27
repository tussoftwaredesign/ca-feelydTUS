package team;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "teamMembers.dat";

    // OOP2 Req 12 Using NIO2
    public static void saveTeamMembers(ArrayList<Player> teamMembers)    {

        Path path = Paths.get(FILE_NAME);

        // Create the file if it doesn't exist
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException("Error creating file: " + e.getMessage(), e);
            }
        }

        if(Files.exists(path)) {
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

    // OOP2 Req 12 Using NIO2
    @SuppressWarnings("unchecked")
    public static ArrayList<Player> loadTeamMembers(String fileName)
    {
        ArrayList<Player> teamMembers = new ArrayList<>();
        //File file = new File(fileName);
        Path path = Paths.get(fileName);
        // This will create a file if it doesn't exist
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (Files.exists(path)) {
            try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
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
