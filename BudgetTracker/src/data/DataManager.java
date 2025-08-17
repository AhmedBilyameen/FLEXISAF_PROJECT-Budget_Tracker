package data;

import model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String BASE_PATH = "resources/";

    public static void save(List<Transaction> transactions, String username) {
        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists()) dir.mkdirs();

            String filePath = BASE_PATH + "transactions_" + username + ".dat";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(transactions);
            }
        } catch (IOException e) {
            System.err.println("Failed to save transactions: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Transaction> load(String username) {
        String filePath = BASE_PATH + "transactions_" + username + ".dat";
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Transaction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
