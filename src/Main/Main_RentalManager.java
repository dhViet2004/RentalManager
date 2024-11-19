package Main;

import Classes.*;
import Interface.*;
import java.util.Scanner;

public class Main_RentalManager {
    private static final RentalAgreementManager rentalAgreementManager = new RentalAgreementManager(); // Khởi tạo đối tượng RentalAgreementManager
    private static final Scanner scanner = new Scanner(System.in); // Khởi tạo Scanner

    public static void main(String[] args) {
        rentalAgreementManager.loadFromFile("src/File/rental_agreements.txt");
        int choice = 0;
        do {
            try {
                displayMenu();
                System.out.print("Nhập lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine()); // Xử lý ngoại lệ khi nhập không phải số
                switch (choice) {
                    case 1 -> addRentalAgreement();
                    case 2 -> removeRentalAgreement();
                    case 3 -> updateRentalAgreement();
                    case 4 -> displayRentalAgreements();
                    case 5 -> System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        } while (choice != 5);

        scanner.close(); // Đóng scanner sau khi sử dụng
    }

    private static void displayMenu() {
        System.out.println("\n-------- MENU --------");
        System.out.println("1. Thêm hợp đồng thuê mới");
        System.out.println("2. Xóa hợp đồng thuê");
        System.out.println("3. Cập nhật hợp đồng thuê");
        System.out.println("4. Hiển thị tất cả hợp đồng thuê");
        System.out.println("5. Thoát");
    }

    private static void addRentalAgreement() {
        RentalAgreement newAgreement = rentalAgreementManager.inputRentalAgreementData(); // Nhập thông tin hợp đồng mới
        if (newAgreement != null) {
            rentalAgreementManager.add(newAgreement); // Thêm hợp đồng thuê vào danh sách
            rentalAgreementManager.saveToFile("src/File/rental_agreements.txt"); // Lưu danh sách hợp đồng thuê vào file
            System.out.println("Thêm hợp đồng thuê thành công!");
        } else {
            System.out.println("Không thể thêm hợp đồng thuê.");
        }
    }

    private static void removeRentalAgreement() {
        System.out.print("\nNhập contractId cần xóa: ");
        String contractId = scanner.nextLine();
        rentalAgreementManager.remove(contractId); // Xóa hợp đồng thuê
    }

    private static void updateRentalAgreement() {
        System.out.print("\nNhập contractId cần cập nhật: ");
        String contractId = scanner.nextLine();

        // Lấy đối tượng RentalAgreement cần cập nhật
        RentalAgreement existingAgreement = rentalAgreementManager.getOne(contractId);
        if (existingAgreement == null) {
            System.out.println("Không tìm thấy hợp đồng thuê với contractId: " + contractId);
            return;
        }

        System.out.println("Cập nhật hợp đồng thuê với contractId: " + contractId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập thông tin mới cho RentalAgreement (tương tự như trong inputRentalAgreementData)
        // Giả sử bạn chỉ muốn cập nhật thông tin như trạng thái hoặc phí thuê.

        System.out.print("Trạng thái hiện tại: " + existingAgreement.getStatus() + " -> ");
        String newStatus = scanner.nextLine();
        if (!newStatus.isEmpty()) {
            try {
                existingAgreement.setStatus(RentalAgreement.RentalAgreementStatus.valueOf(newStatus.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Trạng thái không hợp lệ. Cập nhật không thành công.");
                return;
            }
        }

        System.out.print("Phí thuê hiện tại: " + existingAgreement.getRentalFee() + " -> ");
        String newRentalFee = scanner.nextLine();
        if (!newRentalFee.isEmpty()) {
            try {
                existingAgreement.setRentalFee(Double.parseDouble(newRentalFee));
            } catch (NumberFormatException e) {
                System.out.println("Phí thuê không hợp lệ. Cập nhật không thành công.");
                return;
            }
        }

        // Thực hiện cập nhật
        rentalAgreementManager.update(existingAgreement);
        rentalAgreementManager.saveToFile("src/File/rental_agreements.txt");
        System.out.println("Cập nhật hợp đồng thuê thành công!");
    }

    private static void displayRentalAgreements() {
        System.out.println("\nDanh sách hợp đồng thuê hiện tại:");
        for (RentalAgreement agreement : rentalAgreementManager.getAll()) {
            System.out.println(agreement.toString());
        }
    }
}
