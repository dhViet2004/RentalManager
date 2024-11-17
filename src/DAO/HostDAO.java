package DAO;

import Classes.Host;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HostDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "src/File/hosts.txt";
    private static final Scanner scanner = new Scanner(System.in);

    // Convert Host object to String for file writing
    private String convertHostToString(Host host) {
        return String.join(",",
                host.getId(),
                host.getFullName(),
                host.getDateOfBirth() != null ? dateFormat.format(host.getDateOfBirth()) : "",
                host.getContactInfo()
                // Bạn có thể thêm thông tin về danh sách managedProperties và cooperatingOwners nếu cần lưu
        );
    }

    // Convert String from file to Host object
    private Host convertStringToHost(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        // Hiện tại chưa khôi phục được danh sách managedProperties và cooperatingOwners từ file
        return new Host(fullName, id, dateOfBirth, contactInfo, null, null);
    }

    // Write a list of hosts to a file (overwrite file content)
    public void writeToFile(List<Host> hosts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Host host : hosts) {
                writer.write(convertHostToString(host));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read hosts from file and return the list
    public List<Host> readFromFile() {
        List<Host> hosts = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Host không tồn tại.");
            System.out.print("Bạn có muốn tạo file mới không? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Quá trình dừng lại.");
                return hosts;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Host host = convertStringToHost(line);
                if (host != null) {
                    hosts.add(host);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return hosts;
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

    // Update host information in the list and write it back to the file
    public boolean update(Host updatedHost) {
        List<Host> hosts = readFromFile();

        boolean hostFound = false;
        for (int i = 0; i < hosts.size(); i++) {
            if (hosts.get(i).getId().equals(updatedHost.getId())) {
                hosts.set(i, updatedHost);
                hostFound = true;
                break;
            }
        }

        if (hostFound) {
            writeToFile(hosts);
            System.out.println("Host updated successfully!");
            return true;
        } else {
            System.out.println("Host not found!");
            return false;
        }
    }

    // Delete host by hostId and update the file
    public boolean delete(String hostId) {
        List<Host> hosts = readFromFile();

        boolean hostFound = false;
        Iterator<Host> iterator = hosts.iterator();
        while (iterator.hasNext()) {
            Host host = iterator.next();
            if (host.getId().equals(hostId)) {
                iterator.remove();
                hostFound = true;
                break;
            }
        }

        if (hostFound) {
            writeToFile(hosts);
            System.out.println("Host deleted successfully!");
            return true;
        } else {
            System.out.println("Host not found!");
            return false;
        }
    }
}
