package DAO;

import Classes.ResidentialProperty;
import Classes.Property.PropertyStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResidentialPropertyDAO {

    private static final String FILE_PATH = "src/File/residential_properties.txt";
    private static final Scanner scanner = new Scanner(System.in); // Scanner để nhập từ người dùng

    // Convert a ResidentialProperty object to String
    private String convertToString(ResidentialProperty property) {
        return String.join(",",
                property.getPropertyId(),
                property.getAddress(),
                String.valueOf(property.getPricing()),
                property.getStatus().name(),
                String.valueOf(property.getNumBedrooms()),
                String.valueOf(property.isGardenAvailability()),
                String.valueOf(property.isPetFriendliness())
        );
    }

    // Convert String to a ResidentialProperty object
    private ResidentialProperty convertToProperty(String line) {
        String[] parts = line.split(",");
        if (parts.length < 7) return null;

        String propertyId = parts[0];
        String address = parts[1];
        double pricing = Double.parseDouble(parts[2]);
        PropertyStatus status = PropertyStatus.valueOf(parts[3]);
        int numBedrooms = Integer.parseInt(parts[4]);
        boolean gardenAvailability = Boolean.parseBoolean(parts[5]);
        boolean petFriendliness = Boolean.parseBoolean(parts[6]);

        return new ResidentialProperty(propertyId, address, pricing, status, numBedrooms, gardenAvailability, petFriendliness);
    }

    public List<ResidentialProperty> readFromFile() {
        List<ResidentialProperty> properties = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Residential_properties không tồn tại.");
            System.out.print("Bạn có muốn tạo file mới không? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Quá trình dừng lại.");
                return properties;  // Trả về danh sách rỗng nếu người dùng không muốn tạo file mới
            }
        }

        // Đọc dữ liệu từ file nếu file tồn tại
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ResidentialProperty property = convertToProperty(line);
                if (property != null) properties.add(property);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // Tạo file mới nếu không tìm thấy
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

    public void writeToFile(List<ResidentialProperty> properties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ResidentialProperty property : properties) {
                writer.write(convertToString(property));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ResidentialProperty property) {
        List<ResidentialProperty> properties = readFromFile();
        properties.add(property);
        writeToFile(properties);
        System.out.println("Residential property added successfully.");
    }

    public boolean updateProperty(ResidentialProperty updatedProperty) {
        List<ResidentialProperty> properties = readFromFile();
        boolean updated = false;

        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(updatedProperty.getPropertyId())) {
                properties.set(i, updatedProperty);
                updated = true;
                break;
            }
        }

        if (updated) {
            writeToFile(properties);
            System.out.println("Residential property updated successfully.");
        } else {
            System.out.println("Property not found.");
        }
        return updated;
    }

    public boolean deleteProperty(String propertyId) {
        List<ResidentialProperty> properties = readFromFile();
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(propertyId));

        if (removed) {
            writeToFile(properties);
            System.out.println("Residential property deleted successfully.");
        } else {
            System.out.println("Property not found.");
        }
        return removed;
    }
}
