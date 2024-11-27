package Interface;

import Classes.Property;
import Classes.ResidentialProperty;
import DAO.ResidentialPropertyDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResidentialPropertyManager implements RentalManager<ResidentialProperty> {
    private List<ResidentialProperty> properties = new ArrayList<>();
    private ResidentialPropertyDAO propertyDAO = new ResidentialPropertyDAO(); // DAO để ghi và đọc từ file
    private static String FILE_PATH = "src/File/residential_properties.txt";
    @Override
    public boolean add(ResidentialProperty item) {
        if (properties.stream().anyMatch(p -> p.getPropertyId().equals(item.getPropertyId()))) {
            System.out.println("Error: Property ID already exists: " + item.getPropertyId());
            return false;
        }
        properties.add(item);
        System.out.println("Residential Property successfully added: " + item);
        return true;
    }

    @Override
    public void update(ResidentialProperty item) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(item.getPropertyId())) {
                properties.set(i, item);
                System.out.println("Residential Property updated successfully!");
                saveToFile("src/File/residential_properties.txt");
                return;
            }
        }
        System.out.println("No property found with ID: " + item.getPropertyId());
    }

    @Override
    public void remove(String id) {
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(id));
        if (removed) {
            System.out.println("Residential Property with ID removed: " + id);
            saveToFile("src/File/residential_properties.txt");
        } else {
            System.out.println("No property found with ID: " + id);
        }
    }

    @Override
    public ResidentialProperty getOne(String id) {
        return properties.stream()
                .filter(property -> property.getPropertyId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ResidentialProperty> getAll() {
        return new ArrayList<>(properties);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (ResidentialProperty property : properties) {
            ids.add(property.getPropertyId());
        }
        return ids;
    }

    @Override
    public List<ResidentialProperty> getAllByCustomerID(String customerID) {
        // Implement this method if necessary
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            propertyDAO.writeToFile(properties,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            properties = propertyDAO.readFromFile();
            if (properties.isEmpty()) {
                System.out.println("No data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while loading from file: " + fileName);
            e.printStackTrace();
        }
    }

    // Phương thức để nhập dữ liệu ResidentialProperty từ người dùng
    public ResidentialProperty inputResidentialPropertyData() {
        Scanner scanner = new Scanner(System.in);
        String propertyId;

        // Kiểm tra ID đã tồn tại chưa
        while (true) {
            System.out.print("Enter propertyId: ");
            propertyId = scanner.nextLine();
            // Kiểm tra định dạng ID phải bắt đầu bằng "RP" và theo sau là các số
            if (!propertyId.matches("^RP\\d+$")) {
                System.out.println("Error: Property ID must start with 'RP' followed by numbers. Please re-enter.");
                continue;
            }
            // Kiểm tra ID đã tồn tại trong danh sách không
            String finalPropertyId = propertyId;
            boolean exists = properties.stream()
                    .anyMatch(property -> property.getPropertyId().equals(finalPropertyId));

            if (exists) {
                System.out.println("Error: Property ID already exists. Please re-enter.");
            } else {
                break;
            }
        }

        System.out.print("Enter property address: ");
        String address = scanner.nextLine();

        System.out.print("Enter property price: ");
        double pricing = scanner.nextDouble();

        scanner.nextLine();  // Đọc dòng dư thừa

        System.out.print("Enter property status (AVAILABLE, RENTED, UNDER_MAINTENANCE): ");
        String statusInput = scanner.nextLine();
        Property.PropertyStatus status = Property.PropertyStatus.valueOf(statusInput.toUpperCase());

        System.out.print("Enter number of bedrooms: ");
        int numBedrooms = scanner.nextInt();

        System.out.print("Does the property have a garden (true/false): ");
        boolean gardenAvailability = scanner.nextBoolean();

        System.out.print("Is the property pet-friendly (true/false): ");
        boolean petFriendliness = scanner.nextBoolean();

        // Tạo đối tượng ResidentialProperty mới
        ResidentialProperty property = new ResidentialProperty(propertyId, address, pricing, status, numBedrooms, gardenAvailability, petFriendliness);

        return property;
    }
    // Sắp xếp Residential Properties theo propertyId (tăng dần)
    public void sortPropertiesById() {
        properties.sort((p1, p2) -> {
            try {
                // Lấy phần số của propertyId sau "RP" và chuyển đổi sang số nguyên
                int id1 = Integer.parseInt(p1.getPropertyId().substring(2)); // Bỏ "RP" và chuyển phần còn lại thành số
                int id2 = Integer.parseInt(p2.getPropertyId().substring(2)); // Tương tự cho p2

                return Integer.compare(id1, id2); // So sánh các số nguyên
            } catch (NumberFormatException e) {
                // Nếu không thể chuyển đổi thành số (ví dụ "RP001"), so sánh chuỗi
                return p1.getPropertyId().compareTo(p2.getPropertyId());
            }
        });
        System.out.println("Danh sách Residential Properties đã được sắp xếp theo propertyId (tăng dần).");
    }

    // Lưu danh sách Residential Properties vào file backup
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Tên file backup không hợp lệ.");
            return;
        }

        try {
            propertyDAO.writeToFile(properties, backupFileName);  // Ghi danh sách properties vào file
            System.out.println("Danh sách Residential Properties đã được lưu vào file backup: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file backup: " + backupFileName);
            e.printStackTrace();
        }
    }

}
