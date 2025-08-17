package data;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataManager {
    private static final String FILE_PATH = "resources/users.dat";

    public static void save(List<User> users) {
        try {
            File dir = new File("resources");
            if (!dir.exists()) dir.mkdirs();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(users);
            }
        } catch (IOException e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<User> load() {
        File file = new File("resources/users.dat");
        if (!file.exists()) {
            return new java.util.ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load users: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

}
