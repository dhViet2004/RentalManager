package DAO;

import Classes.Tenant;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TenantDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "src/File/tenants.txt"; // Đường dẫn tệp lưu tenant
    private static final Scanner scanner = new Scanner(System.in);

    // Convert Tenant object to String for file writing
    private String convertTenantToString(Tenant tenant) {
        return String.join(",",
                tenant.getId(),
                tenant.getFullName(),
                tenant.getDateOfBirth() != null ? dateFormat.format(tenant.getDateOfBirth()) : "",
                tenant.getContactInfo()
        );
    }

    // Convert String from file to Tenant object
    private Tenant convertStringToTenant(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        return new Tenant(fullName, id, dateOfBirth, contactInfo, null, null);
    }

    // Write a list of tenants to a file (overwrite file content)
    public void writeToFile(List<Tenant> tenants, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Tenant tenant : tenants) {
                writer.write(convertTenantToString(tenant));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc tenant từ file và trả về danh sách tenants
    public List<Tenant> readFromFile() {
        List<Tenant> tenants = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Kiểm tra sự tồn tại của file
        if (!file.exists()) {
            System.out.println("File Tenant không tồn tại.");
            System.out.print("Bạn có muốn tạo file mới không? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();  // Tạo file mới nếu người dùng đồng ý
            } else {
                System.out.println("Quá trình dừng lại.");
                return tenants;  // Trả về danh sách rỗng nếu người dùng không muốn tạo file mới
            }
        }

        // Đọc dữ liệu từ file nếu file tồn tại
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Tenant tenant = convertStringToTenant(line);  // Chuyển đổi mỗi dòng thành một Tenant
                if (tenant != null) {
                    tenants.add(tenant);  // Thêm tenant vào danh sách
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tenants;  // Trả về danh sách tenants đã đọc từ file
    }

    // Tạo file mới nếu file không tồn tại
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

    // Update tenant information in the list and write it back to the file
    public boolean update(Tenant updatedTenant) {
        List<Tenant> tenants = readFromFile(); // Đọc danh sách tenant từ file

        boolean tenantFound = false;
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(updatedTenant.getId())) {
                // Cập nhật thông tin tenant
                tenants.set(i, updatedTenant);
                tenantFound = true;
                break;
            }
        }

        if (tenantFound) {
            // Ghi lại danh sách tenant đã cập nhật vào file
            writeToFile(tenants, FILE_PATH);
            System.out.println("Tenant updated successfully!");
            return true;
        } else {
            System.out.println("Tenant not found!");
            return false;
        }
    }

    // Delete tenant by tenantId and update the file
    public boolean delete(String tenantId) {
        List<Tenant> tenants = readFromFile(); // Đọc danh sách tenant từ file

        boolean tenantFound = false;
        Iterator<Tenant> iterator = tenants.iterator();
        while (iterator.hasNext()) {
            Tenant tenant = iterator.next();
            if (tenant.getId().equals(tenantId)) {
                iterator.remove(); // Xóa tenant khỏi danh sách
                tenantFound = true;
                break;
            }
        }

        if (tenantFound) {
            // Ghi lại danh sách tenant sau khi xóa vào file
            writeToFile(tenants, FILE_PATH);
            System.out.println("Tenant deleted successfully!");
            return true;
        } else {
            System.out.println("Tenant not found!");
            return false;
        }
    }
}
