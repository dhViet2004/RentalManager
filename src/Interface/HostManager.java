package Interface;

import Classes.Host;
import DAO.HostDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostManager implements RentalManager<Host> {
    private List<Host> hosts = new ArrayList<>();
    private HostDAO hostDAO = new HostDAO(); // DAO cho việc lưu và tải dữ liệu từ file

    @Override
    public boolean add(Host item) {
        // Kiểm tra xem hostId đã tồn tại chưa
        Host tempHost = new Host(item.getFullName(), item.getId(), item.getDateOfBirth(), item.getContactInfo(), item.getManagedProperties(), item.getCooperatingOwners());
        if (hosts.contains(tempHost)) {
            System.out.println("Host with hostId: " + item.getId() + " already exists.");
            return false;
        }
        hosts.add(item);
        System.out.println("Host successfully added: " + item);
        return true;
    }

    @Override
    public void update(Host item) {
        boolean updated = false;
        for (int i = 0; i < hosts.size(); i++) {
            if (hosts.get(i).getId().equals(item.getId())) {
                hosts.set(i, item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Host updated successfully!");
        } else {
            System.out.println("No host found with hostId: " + item.getId());
        }
    }

    @Override
    public void remove(String id) {
        boolean removed = hosts.removeIf(host -> host.getId().equals(id));
        if (removed) {
            System.out.println("Host with hostId removed: " + id);
            saveToFile("src/File/hosts.txt"); // Cập nhật dữ liệu vào file sau khi xóa
        } else {
            System.out.println("No host found with hostId: " + id);
        }
    }

    @Override
    public Host getOne(String id) {
        for (Host host : hosts) {
            if (host.getId().equals(id)) {
                return host;
            }
        }
        return null;
    }

    @Override
    public List<Host> getAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Host host : hosts) {
            ids.add(host.getId());
        }
        return ids;
    }

    @Override
    public List<Host> getAllByCustomerID(String customerID) {
        // Giả sử không có khái niệm customerID trong Host, trả về danh sách trống
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            // Gọi HostDAO để lưu danh sách Host vào file
            hostDAO.writeToFile(hosts);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            // Gọi HostDAO để tải danh sách Host từ file
            hosts = hostDAO.readFromFile();
            if (hosts.isEmpty()) {
                System.out.println("No host data found in file: " + fileName);
            } else {
                System.out.println("Successfully loaded from file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    public Host inputHostData() {
        Scanner scanner = new Scanner(System.in);
        String hostId;

        // Yêu cầu nhập ID và kiểm tra trùng lặp
        while (true) {
            System.out.print("Enter hostId: ");
            hostId = scanner.nextLine();

            if (getOne(hostId) != null) {
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

        Host host = new Host(fullName, hostId, null, contactInfo, null, null);

        // Chuyển đổi dateOfBirth từ String sang Date nếu cần thiết
        try {
            host.setDateOfBirth(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirthStr));
        } catch (Exception e) {
            System.out.println("Invalid date format! Setting dateOfBirth to null.");
        }

        return host;
    }
}
