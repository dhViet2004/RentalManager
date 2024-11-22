package DAO;

import Classes.Owner;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OwnerDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "src/File/owners.txt";
    private static final Scanner scanner = new Scanner(System.in);

    // Convert Owner object to String for file writing
    private String convertOwnerToString(Owner owner) {
        return String.join(",",
                owner.getId(),
                owner.getFullName(),
                owner.getDateOfBirth() != null ? dateFormat.format(owner.getDateOfBirth()) : "",
                owner.getContactInfo()
                // Hiện tại chưa xử lý danh sách ownedProperties và managingHosts trong file
        );
    }

    // Convert String from file to Owner object
    private Owner convertStringToOwner(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        // Hiện tại chưa khôi phục danh sách ownedProperties và managingHosts từ file
        return new Owner(fullName, id, dateOfBirth, contactInfo, new ArrayList<>(), new ArrayList<>());
    }

    // Write a list of owners to a file (overwrite file content)
    public void writeToFile(List<Owner> owners,String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Owner owner : owners) {
                writer.write(convertOwnerToString(owner));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read owners from file and return the list
    public List<Owner> readFromFile() {
        List<Owner> owners = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Owner không tồn tại.");
            System.out.print("Bạn có muốn tạo file mới không? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Quá trình dừng lại.");
                return owners;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Owner owner = convertStringToOwner(line);
                if (owner != null) {
                    owners.add(owner);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return owners;
    }

    // Create a new file if it does not exist
    private void createNewFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("File mới đã được tạo: " + file.getName());
            } else {
                System.out.println("File đã tồn tại.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update owner information in the list and write it back to the file
    public boolean update(Owner updatedOwner) {
        List<Owner> owners = readFromFile();

        boolean ownerFound = false;
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(updatedOwner.getId())) {
                owners.set(i, updatedOwner);
                ownerFound = true;
                break;
            }
        }

        if (ownerFound) {
            writeToFile(owners,FILE_PATH);
            System.out.println("Owner updated successfully!");
            return true;
        } else {
            System.out.println("Owner not found!");
            return false;
        }
    }

    // Delete owner by ownerId and update the file
    public boolean delete(String ownerId) {
        List<Owner> owners = readFromFile();

        boolean ownerFound = false;
        Iterator<Owner> iterator = owners.iterator();
        while (iterator.hasNext()) {
            Owner owner = iterator.next();
            if (owner.getId().equals(ownerId)) {
                iterator.remove();
                ownerFound = true;
                break;
            }
        }

        if (ownerFound) {
            writeToFile(owners,FILE_PATH);
            System.out.println("Owner deleted successfully!");
            return true;
        } else {
            System.out.println("Owner not found!");
            return false;
        }
    }
}
