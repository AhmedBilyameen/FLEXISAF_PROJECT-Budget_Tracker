package service;

import data.UserDataManager;
import model.User;
import model.UserProfile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    private List<User> users;
    private User loggedInUser;
    private static final String PROFILE_FILE = "resources/userProfiles.dat";
    private Map<String, UserProfile> profiles = new HashMap<>();

    public UserManager() {
        users = UserDataManager.load();
        if (users == null) {
            users = new java.util.ArrayList<>();
        }
        loadProfiles();
    }

    public UserProfile getUserProfile(String username) {
        return profiles.computeIfAbsent(username, UserProfile::new);
    }
    public void saveProfiles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROFILE_FILE))) {
            oos.writeObject(profiles);
        } catch (IOException e) {
            System.err.println("Failed to save user profiles: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private void loadProfiles() {
        File file = new File(PROFILE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                profiles = (Map<String, UserProfile>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Failed to load user profiles: " + e.getMessage());
            }
        }
    }

    public boolean register(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false; // User already exists
            }
        }
        User newUser = new User(username, password);
        users.add(newUser);
        UserDataManager.save(users);
        return true;
    }

    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                loggedInUser = u;
                return true;
            }
        }
        return false;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
