package Interface;

import Classes.CommercialProperty;
import Classes.Property;
import DAO.CommercialPropertyDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommercialPropertyManager implements RentalManager<CommercialProperty> {
    private List<CommercialProperty> properties = new ArrayList<>();
    private CommercialPropertyDAO propertyDAO = new CommercialPropertyDAO(); // DAO để ghi và đọc từ file
    private static String FILE_NAME = "src/File/commercial_properties.txt";
    @Override
    public boolean add(CommercialProperty item) {
        if (properties.stream().anyMatch(p -> p.getPropertyId().equals(item.getPropertyId()))) {
            System.out.println("Error: Property ID already exists: " + item.getPropertyId());
            return false;
        }
        properties.add(item);
        System.out.println("Commercial Property successfully added: " + item);
        return true;
    }

    @Override
    public void update(CommercialProperty item) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(item.getPropertyId())) {
                properties.set(i, item);
                System.out.println("Commercial Property updated successfully!");
                saveToFile("src/File/commercial_properties.txt");
                return;
            }
        }
        System.out.println("No property found with ID: " + item.getPropertyId());
    }

    @Override
    public void remove(String id) {
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(id));
        if (removed) {
            System.out.println("Commercial Property with ID removed: " + id);
            saveToFile("src/File/commercial_properties.txt");
        } else {
            System.out.println("No property found with ID: " + id);
        }
    }

    @Override
    public CommercialProperty getOne(String id) {
        return properties.stream()
                .filter(property -> property.getPropertyId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CommercialProperty> getAll() {
        return new ArrayList<>(properties);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (CommercialProperty property : properties) {
            ids.add(property.getPropertyId());
        }
        return ids;
    }

    @Override
    public List<CommercialProperty> getAllByCustomerID(String customerID) {
        return new ArrayList<>();  // Chưa thực hiện, nếu cần, bạn có thể triển khai thêm
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            propertyDAO.writeToFile(properties,FILE_NAME);
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

    // Phương thức để nhập dữ liệu CommercialProperty từ người dùng
    public CommercialProperty inputCommercialPropertyData() {
        Scanner scanner = new Scanner(System.in);
        String propertyId;

        // Kiểm tra ID đã tồn tại chưa
        while (true) {
            System.out.print("Enter propertyId: ");
            propertyId = scanner.nextLine();

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

        System.out.print("Enter business type (e.g., Retail, Office): ");
        String businessType = scanner.nextLine();

        System.out.print("Enter number of parking spaces: ");
        int parkingSpaces = scanner.nextInt();

        System.out.print("Enter square footage: ");
        double squareFootage = scanner.nextDouble();

        // Tạo đối tượng CommercialProperty mới
        CommercialProperty property = new CommercialProperty(propertyId, address, pricing, status, businessType, parkingSpaces, squareFootage);

        return property;
    }
    // Sắp xếp commercial properties theo ID (tăng dần)
    public void sortPropertiesById() {
        properties.sort((p1, p2) -> {
            try {
                // Chuyển ID sang số để so sánh nếu có thể
                int id1 = Integer.parseInt(p1.getPropertyId());
                int id2 = Integer.parseInt(p2.getPropertyId());
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                // Nếu ID không phải số, sử dụng so sánh chuỗi
                return p1.getPropertyId().compareTo(p2.getPropertyId());
            }
        });
        System.out.println("Danh sách Commercial Properties đã được sắp xếp theo ID (tăng dần).");
    }
    // Lưu danh sách commercial properties vào file backup
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Tên file backup không hợp lệ.");
            return;
        }

        try {
            propertyDAO.writeToFile(properties,backupFileName);  // Ghi danh sách properties vào file
            System.out.println("Danh sách Commercial Properties đã được lưu vào file backup: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file backup: " + backupFileName);
            e.printStackTrace();
        }
    }

}
