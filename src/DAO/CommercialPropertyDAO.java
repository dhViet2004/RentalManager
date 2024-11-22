package DAO;

import Classes.CommercialProperty;
import Classes.Property.PropertyStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommercialPropertyDAO {

    private static final String FILE_PATH = "src/File/commercial_properties.txt";
    private static final Scanner scanner = new Scanner(System.in); // Scanner để nhập từ người dùng

    // Convert a CommercialProperty object to String
    private String convertToString(CommercialProperty property) {
        return String.join(",",
                property.getPropertyId(),
                property.getAddress(),
                String.valueOf(property.getPricing()),
                property.getStatus().name(),
                property.getBusinessType(),
                String.valueOf(property.getParkingSpaces()),
                String.valueOf(property.getSquareFootage())
        );
    }

    // Convert String to a CommercialProperty object
    private CommercialProperty convertToProperty(String line) {
        String[] parts = line.split(",");
        if (parts.length < 7) return null;

        String propertyId = parts[0];
        String address = parts[1];
        double pricing = Double.parseDouble(parts[2]);
        PropertyStatus status = PropertyStatus.valueOf(parts[3]);
        String businessType = parts[4];
        int parkingSpaces = Integer.parseInt(parts[5]);
        double squareFootage = Double.parseDouble(parts[6]);

        return new CommercialProperty(propertyId, address, pricing, status, businessType, parkingSpaces, squareFootage);
    }

    public List<CommercialProperty> readFromFile() {
        List<CommercialProperty> properties = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Commercial_properties không tồn tại.");
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
                CommercialProperty property = convertToProperty(line);
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

    public void writeToFile(List<CommercialProperty> properties, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (CommercialProperty property : properties) {
                writer.write(convertToString(property));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProperty(CommercialProperty property) {
        List<CommercialProperty> properties = readFromFile();
        properties.add(property);
        writeToFile(properties,FILE_PATH);
        System.out.println("Commercial property added successfully.");
    }

    public boolean updateProperty(CommercialProperty updatedProperty) {
        List<CommercialProperty> properties = readFromFile();
        boolean updated = false;

        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(updatedProperty.getPropertyId())) {
                properties.set(i, updatedProperty);
                updated = true;
                break;
            }
        }

        if (updated) {
            writeToFile(properties,FILE_PATH);
            System.out.println("Commercial property updated successfully.");
        } else {
            System.out.println("Property not found.");
        }
        return updated;
    }

    public boolean deleteProperty(String propertyId) {
        List<CommercialProperty> properties = readFromFile();
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(propertyId));

        if (removed) {
            writeToFile(properties,FILE_PATH);
            System.out.println("Commercial property deleted successfully.");
        } else {
            System.out.println("Property not found.");
        }
        return removed;
    }
}
