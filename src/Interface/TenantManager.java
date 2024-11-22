package Interface;

import Classes.Tenant;
import DAO.TenantDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TenantManager implements RentalManager<Tenant> {
    private List<Tenant> tenants = new ArrayList<>();
    private TenantDAO tenantDAO = new TenantDAO(); // DAO cho việc lưu và tải dữ liệu từ file
    private static String FilePath = "src/File/tenants.txt";
    @Override
    public boolean add(Tenant item) {
        // Kiểm tra xem tenantId đã tồn tại chưa
        Tenant tempTenant = new Tenant(item.getFullName(),item.getId(),item.getDateOfBirth(),item.getContactInfo(),item.getRentalAgreements(),item.getPaymentRecords());
        if(tenants.contains(tempTenant)) {
            System.out.println("Tenant with tenantId: " + item.getId());
            return false;
        }
        tenants.add(item);
        System.out.println("Tenant successfully added: " + item);
        return true;
    }

    @Override
    public void update(Tenant item) {
        // Cập nhật thông tin Tenant trong danh sách
        boolean updated = false;
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(item.getId())) {
                tenants.set(i, item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Tenant updated successfully!");
        } else {
            System.out.println("No tenant found with tenantId: " + item.getId());
        }
    }

    @Override
    public void remove(String id) {
        // Xóa Tenant khỏi danh sách
        boolean removed = tenants.removeIf(tenant -> tenant.getId().equals(id));
        if (removed) {
            System.out.println("Tenant with tenantId removed: " + id);
            saveToFile("src/File/tenants.txt"); // Cập nhật dữ liệu vào file sau khi xóa
        } else {
            System.out.println("No tenant found with tenantId: " + id);
        }
    }

    @Override
    public Tenant getOne(String id) {
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(id)) {
                return tenant;
            }
        }
        return null;
    }

    @Override
    public List<Tenant> getAll() {
        return new ArrayList<>(tenants);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Tenant tenant : tenants) {
            ids.add(tenant.getId());
        }
        return ids;
    }

    @Override
    public List<Tenant> getAllByCustomerID(String customerID) {
        // Giả sử không có khái niệm customerID trong Tenant, trả về danh sách trống
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            // Gọi TenantDAO để lưu danh sách Tenant vào file
            tenantDAO.writeToFile(tenants,FilePath);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            // Gọi TenantDAO để tải danh sách Tenant từ file
            tenants = tenantDAO.readFromFile();
            if (tenants.isEmpty()) {
                System.out.println("No tenant data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    public Tenant inputTenantData() {
        Scanner scanner = new Scanner(System.in);
        String tenantId;

        // Yêu cầu nhập ID và kiểm tra trùng lặp
        while (true) {
            System.out.print("Enter tenantId: ");
            tenantId = scanner.nextLine();

            if (getOne(tenantId) != null) {
                System.out.println("ID này đã tồn tại, vui lòng nhập lại ID khác.");
            } else {
                break; // Thoát khỏi vòng lặp nếu ID không trùng
            }
        }

        // Tiếp tục nhập các thông tin khác
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter date of birth (dd-MM-yyyy): ");
        String dateOfBirthStr = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Tenant tenant = new Tenant(fullName, tenantId, null, contactInfo, null, null);

        // Chuyển đổi dateOfBirth từ String sang Date nếu cần thiết
        try {
            tenant.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirthStr));
        } catch (Exception e) {
            System.out.println("Invalid date format! Setting dateOfBirth to null.");
        }

        return tenant;
    }
    public void sortTenantsById() {
        tenants.sort((t1, t2) -> {
            try {
                int id1 = Integer.parseInt(t1.getId());
                int id2 = Integer.parseInt(t2.getId());
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                // Nếu không thể chuyển đổi, so sánh chuỗi (phòng ngừa lỗi dữ liệu)
                return t1.getId().compareTo(t2.getId());
            }
        });
        System.out.println("Tenants sorted by ID in ascending order.");
    }

    public void saveBackupToFile(String backupFileName) {
        try {
            tenantDAO.writeToFile(tenants, backupFileName);
            System.out.println("Backup saved successfully to file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving backup to file: " + backupFileName);
            e.printStackTrace();
        }
    }


}
