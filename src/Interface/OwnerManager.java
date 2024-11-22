package Interface;

import Classes.Owner;
import DAO.OwnerDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwnerManager implements RentalManager<Owner> {
    private List<Owner> owners = new ArrayList<>();
    private OwnerDAO ownerDAO = new OwnerDAO(); // DAO để lưu và tải dữ liệu
    private static String FILE_PATH = "src/File/owners.txt";
    @Override
    public boolean add(Owner item) {
        // Kiểm tra xem ownerId đã tồn tại chưa
        if (owners.contains(item)) {
            System.out.println("Owner with ownerId: " + item.getId() + " already exists.");
            return false;
        }
        owners.add(item);
        System.out.println("Owner successfully added: " + item);
        return true;
    }

    @Override
    public void update(Owner item) {
        boolean updated = false;
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(item.getId())) {
                owners.set(i, item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Owner updated successfully!");
        } else {
            System.out.println("No owner found with ownerId: " + item.getId());
        }
    }

    @Override
    public void remove(String id) {
        boolean removed = owners.removeIf(owner -> owner.getId().equals(id));
        if (removed) {
            System.out.println("Owner with ownerId removed: " + id);
            saveToFile("src/File/owners.txt"); // Cập nhật file sau khi xóa
        } else {
            System.out.println("No owner found with ownerId: " + id);
        }
    }

    @Override
    public Owner getOne(String id) {
        for (Owner owner : owners) {
            if (owner.getId().equals(id)) {
                return owner;
            }
        }
        return null;
    }

    @Override
    public List<Owner> getAll() {
        return new ArrayList<>(owners);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Owner owner : owners) {
            ids.add(owner.getId());
        }
        return ids;
    }

    @Override
    public List<Owner> getAllByCustomerID(String customerID) {
        // Giả sử Owner không liên quan đến CustomerID, trả về danh sách trống
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            // Gọi OwnerDAO để lưu danh sách Owner vào file
            ownerDAO.writeToFile(owners,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            // Gọi OwnerDAO để tải danh sách Owner từ file
            owners = ownerDAO.readFromFile();
            if (owners.isEmpty()) {
                System.out.println("No owner data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    public Owner inputOwnerData() {
        Scanner scanner = new Scanner(System.in);
        String ownerId;

        // Yêu cầu nhập ID và kiểm tra trùng lặp
        while (true) {
            System.out.print("Enter ownerId: ");
            ownerId = scanner.nextLine();

            if (getOne(ownerId) != null) {
                System.out.println("ID này đã tồn tại, vui lòng nhập lại ID khác.");
            } else {
                break; // Thoát vòng lặp nếu ID không trùng
            }
        }

        // Nhập các thông tin khác
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter date of birth (dd-MM-yyyy): ");
        String dateOfBirthStr = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Owner owner = new Owner(fullName, ownerId, null, contactInfo, null, null);

        // Chuyển đổi dateOfBirth từ String sang Date nếu cần
        try {
            owner.setDateOfBirth(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirthStr));
        } catch (Exception e) {
            System.out.println("Invalid date format! Setting dateOfBirth to null.");
        }

        return owner;
    }
    // Sort owners by ID in ascending order
    public void sortOwnersById() {
        owners.sort((o1, o2) -> {
            try {
                // Chuyển đổi ID sang số nếu có thể để sắp xếp
                int id1 = Integer.parseInt(o1.getId());
                int id2 = Integer.parseInt(o2.getId());
                return Integer.compare(id1, id2);
            } catch (NumberFormatException e) {
                // Nếu không phải số, so sánh chuỗi
                return o1.getId().compareTo(o2.getId());
            }
        });
        System.out.println("Danh sách Owners đã được sắp xếp theo ID (tăng dần).");
    }
    // Save owners to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Tên file backup không hợp lệ.");
            return;
        }

        try {
            ownerDAO.writeToFile(owners, backupFileName);
            System.out.println("Danh sách Owners đã được lưu vào file backup: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file backup: " + backupFileName);
            e.printStackTrace();
        }
    }

}
