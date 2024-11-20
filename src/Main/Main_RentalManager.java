package Main;

import Classes.*;
import Interface.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_RentalManager {
    private static final RentalAgreementManager rentalAgreementManager = new RentalAgreementManager(); // Khởi tạo đối tượng RentalAgreementManager
    private static final OwnerManager ownerManager = new OwnerManager();
    private static final HostManager hostManager = new HostManager();
    private static final TenantManager tenantManager = new TenantManager();
    private static final CommercialPropertyManager commercialPropertyManager = new CommercialPropertyManager(); // Khởi tạo đối tượng CommercialPropertyManager
    private static final ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager(); // Khởi tạo đối tượng ResidentialPropertyManager
    private static final PaymentManager paymentManager = new PaymentManager();

    private static final Scanner scanner = new Scanner(System.in); // Khởi tạo Scanner

    public static void main(String[] args) {
        ownerManager.loadFromFile("src/File/owner.txt");
        tenantManager.loadFromFile("src/File/tenant.txt");
        hostManager.loadFromFile("src/File/host.txt");
        commercialPropertyManager.loadFromFile("src/File/commercial_properties.txt");
        residentialPropertyManager.loadFromFile("src/File/residential_properties.txt"); // Tải dữ liệu ResidentialProperty
        paymentManager.loadFromFile("src/File/payments.txt");
        rentalAgreementManager.loadFromFile("src/File/rental_agreements.txt");

        int choice = 0;
        do {
            try {
                displayMainMenu();
                System.out.print("Nhập lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> rentalAgreementMenu();
                    case 2 -> tenantMenu();
                    case 3 -> ownerMenu();
                    case 4 -> hostMenu();
                    case 5 -> paymentMenu();
                    case 6 -> commercialPropertyMenu();
                    case 7 -> residentialPropertyMenu();
                    case 8 -> System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        } while (choice != 8);

        scanner.close(); // Đóng scanner sau khi sử dụng
    }
    private static void commercialPropertyMenu() {
        int choice;
        do {
            displaySubMenu("Commercial Property");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewCommercialProperty();
                case 2 -> removeCommercialProperty();
                case 3 -> updateCommercialProperty();
                case 4 -> displayCommercialProperties();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }

    private static void residentialPropertyMenu() {
        int choice;
        do {
            displaySubMenu("Residential Property");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewResidentialProperty();
                case 2 -> removeResidentialProperty();
                case 3 -> updateResidentialProperty();
                case 4 -> displayResidentialProperties();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }
    private static void paymentMenu() {
        int choice;
        do {
            displaySubMenu("Payment");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewPayment();
                case 2 -> removePayment();
                case 3 -> updatePayment();
                case 4 -> displayPayments();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }

    private static void hostMenu() {
        int choice;
        do {
            displaySubMenu("Host");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewHost();
                case 2 -> removeHost();
                case 3 -> updateHost();
                case 4 -> displayHosts();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }

    private static void ownerMenu() {
        int choice;
        do {
            displaySubMenu("Owner");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewOwner();
                case 2 -> removeOwner();
                case 3 -> updateOwner();
                case 4 -> displayOwners();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }

    private static void tenantMenu() {
        int choice;
        do {
            displaySubMenu("Tenant");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewTenant();
                case 2 -> removeTenant();
                case 3 -> updateTenant();
                case 4 -> displayTenants();
                case 5 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 5);
    }

    private static void rentalAgreementMenu() {
        int choice;
        do {
            displaySubMenuRA("Hợp đồng thuê");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addRentalAgreement();
                case 2 -> removeRentalAgreement();
                case 3 -> updateRentalAgreement();
                case 4 -> displayRentalAgreements();
                case 5 -> getByOwnerName();
                case 6 -> getByPropertyAddress();
                case 7 -> getByStatus();
                case 8 -> System.out.println("Quay lại menu chính.");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 8);
    }
    private static void displaySubMenuRA(String entity) {
        System.out.printf("\n-------- MENU QUẢN LÝ %s --------\n", entity.toUpperCase());
        System.out.println("    1. Thêm " + entity);
        System.out.println("    2. Xóa " + entity);
        System.out.println("    3. Cập nhật " + entity);
        System.out.println("    4. Hiển thị danh sách " + entity);
        System.out.println("    5. Get by Owner " + entity);
        System.out.println("    6. Get by Property Address " + entity);
        System.out.println("    7. Get by Status " + entity);
        System.out.println("    8. Quay lại menu chính");
    }
    private static void displaySubMenu(String entity) {
        System.out.printf("\n-------- MENU QUẢN LÝ %s --------\n", entity.toUpperCase());
        System.out.println("    1. Thêm " + entity);
        System.out.println("    2. Xóa " + entity);
        System.out.println("    3. Cập nhật " + entity);
        System.out.println("    4. Hiển thị danh sách " + entity);
        System.out.println("    5. Quay lại menu chính");
    }

    private static void displayMainMenu() {
        System.out.println("\n-------- MENU CHÍNH --------");
        System.out.println("1. Quản lý hợp đồng thuê");
        System.out.println("2. Quản lý Tenant");
        System.out.println("3. Quản lý Owner");
        System.out.println("4. Quản lý Host");
        System.out.println("5. Quản lý Payment");
        System.out.println("6. Quản lý Commercial Property");
        System.out.println("7. Quản lý Residential Property");
        System.out.println("8. Thoát");
    }

    private static void addRentalAgreement() {
        RentalAgreement newAgreement = rentalAgreementManager.inputRentalAgreementData(); // Nhập thông tin hợp đồng mới
        if (newAgreement != null) {
            // In thông tin hợp đồng mới ra
            System.out.println("Thông tin hợp đồng mới: ");
            System.out.println(newAgreement);

            // Hỏi người dùng có muốn lưu không
            System.out.print("Bạn có muốn lưu hợp đồng này không? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                rentalAgreementManager.add(newAgreement); // Thêm hợp đồng thuê vào danh sách
                rentalAgreementManager.saveToFile("src/File/rental_agreements.txt"); // Lưu danh sách hợp đồng thuê vào file
                System.out.println("Thêm hợp đồng thuê thành công!");
                displayRentalAgreements();
            } else if ("n".equals(confirmation)) {
                System.out.println("Hành động thêm hợp đồng đã bị hủy.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận lưu hoặc 'n' để hủy.");
            }
        } else {
            System.out.println("Không thể thêm hợp đồng thuê.");
        }
    }

    private static void removeRentalAgreement() {
        System.out.print("\nNhập contractId cần xóa: ");
        String contractId = scanner.nextLine();

        // Xác nhận người dùng có muốn xóa hợp đồng này không
        System.out.print("Bạn có chắc chắn muốn xóa hợp đồng này không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            rentalAgreementManager.remove(contractId); // Xóa hợp đồng thuê
            System.out.println("Hợp đồng thuê với contractId " + contractId + " đã được xóa thành công.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động xóa hợp đồng đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận xóa hoặc 'n' để hủy.");
        }
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

        System.out.println("Owner hiện tại: " + existingAgreement.getOwner());
        Owner owner = null;
        String newOwner;

        do {
            System.out.print("Nhập ID của Owner mới (hoặc nhấn Enter để giữ nguyên): ");
            newOwner = scanner.nextLine();
            if (!newOwner.isEmpty()) {
                // Kiểm tra Owner theo ID
                owner = ownerManager.getOne(newOwner);
                if (owner == null) {
                    System.out.println("Không tìm thấy Owner theo ID: " + newOwner + ". Vui lòng thử lại.");
                } else {
                    System.out.println("Thông tin Owner tìm thấy: " + owner);
                }
            } else {
                owner = existingAgreement.getOwner(); // Giữ nguyên Owner cũ
            }
        } while (owner == null);

        existingAgreement.setOwner(owner);
        System.out.println("Cập nhật Owner thành công!");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        System.out.println("Tenant hiện tại: " + existingAgreement.getMainTenant());
        Tenant tenant = null;
        String newTenant;
        do {
            System.out.print("Nhập ID của Tenant mới (hoặc nhấn Enter để giữ nguyên): ");
            newTenant = scanner.nextLine();
            if (!newTenant.isEmpty()) {
                // Kiểm tra Tenant theo ID
                tenant = tenantManager.getOne(newTenant);
                if (tenant == null) {
                    System.out.println("Không tìm thấy Tenant theo ID: " + newTenant + ". Vui lòng thử lại.");
                } else {
                    System.out.println("Thông tin Tenant tìm thấy: " + tenant);
                }
            } else {
                tenant = existingAgreement.getMainTenant(); // Giữ nguyên Tenant cũ
            }
        } while (tenant == null);

        existingAgreement.setMainTenant(tenant);
        System.out.println("Cập nhật Tenant thành công!");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Cập nhật SubTenant
        System.out.println("Sub Tenant hiện tại: " + existingAgreement.getSubTenants());
        List<Tenant> subTenants = existingAgreement.getSubTenants();


        System.out.print("Nhập số lượng SubTenant mới muốn cập nhật (0 để giữ nguyên): ");
        int newSubTenantCount = 0;
        try {
            newSubTenantCount = Integer.parseInt(scanner.nextLine());
            if (newSubTenantCount < 0) {
                System.out.println("Số lượng không thể nhỏ hơn 0. Vui lòng thử lại.");
                newSubTenantCount = 0; // Nếu nhập số âm, set lại về 0 để không cập nhật
            }
        } catch (NumberFormatException e) {
            System.out.println("Số lượng không hợp lệ. Vui lòng nhập lại.");
        }

        if (newSubTenantCount == 0) {
            System.out.println("Giữ nguyên danh sách SubTenants cũ.");
        } else {
            subTenants.clear(); // Xóa hết các SubTenant cũ

            List<Tenant> updatedSubTenants = new ArrayList<>();
            String newSubTenantId;
            for (int i = 1; i <= newSubTenantCount; i++) {
                Tenant subTenant = null;
                do {
                    System.out.print("Nhập ID của SubTenant thứ " + i + ": ");
                    newSubTenantId = scanner.nextLine();
                    subTenant = tenantManager.getOne(newSubTenantId); // Kiểm tra SubTenant theo ID
                    if (subTenant == null) {
                        System.out.println("Không tìm thấy SubTenant theo ID: " + newSubTenantId + ". Vui lòng thử lại.");
                    }
                } while (subTenant == null);

                updatedSubTenants.add(subTenant); // Thêm SubTenant hợp lệ vào danh sách
                System.out.println("Thông tin SubTenant thứ " + i + " tìm thấy: " + subTenant);
            }

            existingAgreement.setSubTenants(updatedSubTenants); // Cập nhật danh sách SubTenant mới
            System.out.println("Cập nhật SubTenant thành công!");
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Kiểm tra số lượng Host mới
        System.out.print("Nhập số lượng Host mới muốn cập nhật (0 để giữ nguyên): ");
        int newHostCount = Integer.parseInt(scanner.nextLine());

        if (newHostCount == 0) {
            System.out.println("Giữ nguyên danh sách Hosts cũ.");
        } else {
            List<Host> updatedHosts = new ArrayList<>();
            String newHostId;

            for (int i = 1; i <= newHostCount; i++) {
                Host host = null;
                do {
                    System.out.print("Nhập ID của Host thứ " + i + ": ");
                    newHostId = scanner.nextLine();
                    host = hostManager.getOne(newHostId); // Kiểm tra Host theo ID

                    if (host == null) {
                        System.out.println("Không tìm thấy Host theo ID: " + newHostId + ". Vui lòng thử lại.");
                    }
                } while (host == null);

                updatedHosts.add(host); // Thêm Host hợp lệ vào danh sách
                System.out.println("Thông tin Host thứ " + i + " tìm thấy: " + host);
            }

            existingAgreement.setHosts(updatedHosts); // Cập nhật danh sách hosts mới
            System.out.println("Cập nhật Host thành công!");
        }


        System.out.println("-----------------------------------------------------------------------------------------------------------");

        System.out.println("Rental Cycle hiện tại: " + existingAgreement.getRentalCycle() + " -> :");
        String newRentalCycle = scanner.nextLine();
        if (!newRentalCycle.isEmpty()) {
            try {
                existingAgreement.setRentalCycle(RentalAgreement.RentalCycleType.valueOf(newRentalCycle.toUpperCase()));
                System.out.println("Cập nhật Rental Cycle thành công!");
            } catch (IllegalArgumentException e) {
                System.out.println("Rental Cycle không hợp lệ. Cập nhật không thành công.");
                return;
            }
        } else {
            System.out.println("Giữ nguyên Rental Cycle cũ.");
        }

        System.out.print("Thời gian hợp đồng hiện tại: " + existingAgreement.getDuration() + " tháng -> ");
        String newDuration = scanner.nextLine();


        if (!newDuration.isEmpty()) {
            try {
                int duration = Integer.parseInt(newDuration);
                if (duration <= 0) {
                    System.out.println("Thời gian hợp đồng không hợp lệ. Phải là số nguyên dương.");
                    return;
                }
                existingAgreement.setDuration(duration); // Cập nhật Duration
                System.out.println("Thời gian hợp đồng đã được cập nhật thành công: " + duration + " tháng.");
            } catch (NumberFormatException e) {
                System.out.println("Thời gian hợp đồng không hợp lệ. Cập nhật không thành công. Vui lòng nhập một số nguyên dương.");
            }
        } else {
            System.out.println("Không có thay đổi về thời gian hợp đồng.");
        }

        // Cập nhật Contract Terms (Điều khoản hợp đồng)
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.print("Điều khoản hợp đồng hiện tại: " + existingAgreement.getContractTerms() + " -> ");
        String newContractTerms = scanner.nextLine();
        if (!newContractTerms.isEmpty()) {
            existingAgreement.setContractTerms(newContractTerms); // Cập nhật Contract Terms
        }

        // Nhập thông tin mới cho RentalAgreement
        System.out.println("-----------------------------------------------------------------------------------------------------------");
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
        System.out.println("-----------------------------------------------------------------------------------------------------------");
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
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        // Hỏi người dùng có muốn lưu thông tin cập nhật hay không
        String saveChoice;
        do {
            System.out.print("Bạn có muốn lưu thông tin cập nhật không? (y/n): ");
            saveChoice = scanner.nextLine().trim().toLowerCase();
            if (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' hoặc 'n'.");
            }
        } while (!saveChoice.equals("y") && !saveChoice.equals("n"));

        if (saveChoice.equals("y")) {
            rentalAgreementManager.update(existingAgreement); // Cập nhật trong danh sách
            rentalAgreementManager.saveToFile("src/File/rental_agreements.txt"); // Lưu vào file
            rentalAgreementManager.loadFromFile("src/File/rental_agreements.txt");
            System.out.println("Cập nhật hợp đồng thuê thành công và đã được lưu!");
        } else {
            System.out.println("Cập nhật đã bị hủy. Thông tin không được lưu.");
        }
    }
    // Quản lý Tenant
    private static void displayTenants() {
        System.out.println("\nDanh sách tenants hiện tại:");
        for (Tenant tenant : tenantManager.getAll()) {
            System.out.println(tenant.toString());
        }
    }
    private static void addNewTenant() {
        Tenant newTenant = tenantManager.inputTenantData(); // Nhập thông tin tenant mới
        if (newTenant != null) {
            // In thông tin tenant mới ra màn hình
            System.out.println("Thông tin tenant mới: ");
            System.out.println(newTenant);

            // Hỏi người dùng có muốn lưu không
            System.out.print("Bạn có muốn lưu tenant này không? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                if (tenantManager.add(newTenant)) {
                    tenantManager.saveToFile("src/File/tenants.txt"); // Lưu tenant vào file
                    System.out.println("Thêm tenant mới thành công!");
                } else {
                    System.out.println("Không thể thêm tenant.");
                }
            } else if ("n".equals(confirmation)) {
                System.out.println("Hành động thêm tenant đã bị hủy.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận lưu hoặc 'n' để hủy.");
            }
        } else {
            System.out.println("Không thể thêm tenant.");
        }
    }
    private static void removeTenant() {
        System.out.print("\nNhập tenantId cần xóa: ");
        String tenantId = scanner.nextLine();

        // Xác nhận xóa tenant
        System.out.print("Bạn có chắc chắn muốn xóa tenant với ID: " + tenantId + " không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            tenantManager.remove(tenantId); // Xóa tenant
            System.out.println("Xóa tenant thành công!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động xóa tenant đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận xóa hoặc 'n' để hủy.");
        }
    }
    private static void updateTenant() {
        System.out.print("\nNhập tenantId cần cập nhật: ");
        String tenantId = scanner.nextLine();

        // Lấy đối tượng Tenant cần cập nhật
        Tenant existingTenant = tenantManager.getOne(tenantId);
        if (existingTenant == null) {
            System.out.println("Không tìm thấy tenant với tenantId: " + tenantId);
            return;
        }

        System.out.println("Cập nhật tenant với ID: " + tenantId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập các thông tin mới, để trống nếu giữ nguyên giá trị cũ
        System.out.print("Tên đầy đủ hiện tại: " + existingTenant.getFullName() + " -> ");
        String newFullName = scanner.nextLine();
        if (!newFullName.isEmpty()) {
            existingTenant.setFullName(newFullName);
        }

        System.out.print("Ngày sinh hiện tại (yyyy-MM-dd): " + existingTenant.getDateOfBirth() + " -> ");
        String newDateOfBirthStr = scanner.nextLine();
        if (!newDateOfBirthStr.isEmpty()) {
            try {
                Date newDateOfBirth = java.sql.Date.valueOf(newDateOfBirthStr);
                existingTenant.setDateOfBirth(newDateOfBirth);
            } catch (IllegalArgumentException e) {
                System.out.println("Ngày sinh không hợp lệ. Bỏ qua cập nhật ngày sinh.");
            }
        }

        System.out.print("Thông tin liên hệ hiện tại: " + existingTenant.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingTenant.setContactInfo(newContactInfo);
        }

        // Xác nhận lưu các thay đổi
        System.out.print("Bạn có muốn lưu các thay đổi không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            // Thực hiện cập nhật trong tenantManager
            tenantManager.update(existingTenant);
            System.out.println("Cập nhật tenant thành công!");
            tenantManager.saveToFile("src/File/tenants.txt");
        } else if ("n".equals(confirmation)) {
            System.out.println("Cập nhật tenant đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để lưu thay đổi hoặc 'n' để hủy.");
        }
    }
    private static void removePayment() {
        System.out.print("\nNhập paymentId cần xóa: ");
        String paymentId = scanner.nextLine();

        // Xác nhận xóa payment
        System.out.print("Bạn có chắc chắn muốn xóa payment với ID: " + paymentId + " không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            paymentManager.remove(paymentId); // Xóa payment
            System.out.println("Xóa payment thành công!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động xóa payment đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận xóa hoặc 'n' để hủy.");
        }
    }

    private static void updatePayment() {
        System.out.print("\nNhập paymentId cần cập nhật: ");
        String paymentId = scanner.nextLine();

        // Lấy đối tượng Payment cần cập nhật
        Payment existingPayment = paymentManager.getOne(paymentId);
        if (existingPayment == null) {
            System.out.println("Không tìm thấy payment với paymentId: " + paymentId);
            return;
        }

        System.out.println("Cập nhật payment với ID: " + paymentId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập các thông tin mới, để trống nếu giữ nguyên giá trị cũ
        System.out.print("Phương thức thanh toán hiện tại: " + existingPayment.getPaymentMethod() + " -> ");
        String newPaymentMethod = scanner.nextLine();
        if (newPaymentMethod.isEmpty()) {
            newPaymentMethod = existingPayment.getPaymentMethod();
        }

        System.out.print("Số tiền hiện tại: " + existingPayment.getAmount() + " -> ");
        String newAmountStr = scanner.nextLine();
        double newAmount = newAmountStr.isEmpty() ? existingPayment.getAmount() : Double.parseDouble(newAmountStr);

        System.out.print("Mã tenant hiện tại: " + existingPayment.getTenant().getId() + " -> ");
        String newTenantId = scanner.nextLine();
        if (newTenantId.isEmpty()) {
            newTenantId = existingPayment.getTenant().getId();
        }

        // Xác nhận lưu thay đổi
        System.out.print("Bạn có muốn lưu các thay đổi không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            // Cập nhật đối tượng Payment
            Payment updatedPayment = new Payment(
                    newPaymentMethod,
                    existingPayment.getDate(),
                    newAmount,
                    new Tenant(newTenantId),
                    existingPayment.getPaymentId() // Giữ nguyên paymentId
            );

            // Thực hiện cập nhật
            paymentManager.update(updatedPayment);
            System.out.println("Cập nhật payment thành công!");
            paymentManager.loadFromFile("payments.txt");
        } else if ("n".equals(confirmation)) {
            System.out.println("Cập nhật payment đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để lưu thay đổi hoặc 'n' để hủy.");
        }
    }

    private static void addNewPayment() {
        Payment newPayment = paymentManager.inputPaymentData();
        if (newPayment != null) {
            // Xác nhận thêm payment mới
            System.out.print("Bạn có chắc chắn muốn thêm payment mới không? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                paymentManager.add(newPayment);
                paymentManager.saveToFile("src/File/payments.txt");
                System.out.println("Thêm thanh toán mới thành công!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Thêm payment đã bị hủy.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để thêm payment hoặc 'n' để hủy.");
            }
        } else {
            System.out.println("Không thể thêm payment do trùng ID.");
        }
    }
    private static void displayPayments() {
        System.out.println("\nDanh sách thanh toán hiện tại:");
        for (Payment pm : paymentManager.getAll()) {
            System.out.println(pm.toString());
        }
    }
    private static void displayHosts() {
        System.out.println("\nDanh sách Host hiện tại:");
        for (Host host : hostManager.getAll()) {
            System.out.println(host.toString());
        }
    }

    private static void addNewHost() {
        Host newHost = hostManager.inputHostData(); // Phương thức này cần được triển khai trong HostManager
        if (newHost != null) {
            hostManager.add(newHost);
            hostManager.saveToFile("src/File/hosts.txt"); // Đường dẫn tới file Host
            System.out.println("Thêm Host mới thành công!");
        } else {
            System.out.println("Không thể thêm Host do trùng ID.");
        }
    }

    private static void removeHost() {
        System.out.print("\nNhập hostId cần xóa: ");
        String hostId = scanner.nextLine();

        // Yêu cầu xác nhận trước khi xóa
        System.out.print("Bạn có chắc chắn muốn xóa Host với ID: " + hostId + " (y/n)? ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            hostManager.remove(hostId);
            System.out.println("Xóa Host thành công!");
        } else {
            System.out.println("Hủy bỏ xóa Host.");
        }
    }

    private static void updateHost() {
        System.out.print("\nNhập hostId cần cập nhật: ");
        String hostId = scanner.nextLine();

        // Lấy đối tượng Host cần cập nhật
        Host existingHost = hostManager.getOne(hostId);
        if (existingHost == null) {
            System.out.println("Không tìm thấy Host với ID: " + hostId);
            return;
        }

        System.out.println("Cập nhật Host với ID: " + hostId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập thông tin mới cho Host
        System.out.print("Tên hiện tại: " + existingHost.getFullName() + " -> ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            existingHost.setFullName(newName);
        }

        System.out.print("Ngày sinh hiện tại (yyyy-MM-dd): " + existingHost.getDateOfBirth() + " -> ");
        String newDateOfBirth = scanner.nextLine();
        if (!newDateOfBirth.isEmpty()) {
            try {
                Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(newDateOfBirth);
                existingHost.setDateOfBirth(date);
            } catch (ParseException e) {
                System.out.println("Ngày sinh không hợp lệ. Định dạng đúng: yyyy-MM-dd");
            }
        }

        System.out.print("Thông tin liên hệ hiện tại: " + existingHost.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingHost.setContactInfo(newContactInfo);
        }

        // Yêu cầu xác nhận trước khi cập nhật
        System.out.print("Bạn có chắc chắn muốn cập nhật thông tin của Host này? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            // Thực hiện cập nhật
            hostManager.update(existingHost);
            hostManager.saveToFile("src/File/hosts.txt");
            System.out.println("Cập nhật Host thành công!");
        } else {
            System.out.println("Hủy bỏ cập nhật Host.");
        }
    }
    private static void displayOwners() {
        System.out.println("\nDanh sách chủ sở hữu hiện tại:");
        for (Owner owner : ownerManager.getAll()) {
            System.out.println(owner.toString());
        }
    }

    private static void addNewOwner() {
        Owner newOwner = ownerManager.inputOwnerData(); // Phương thức này cần được triển khai trong OwnerManager
        if (newOwner != null) {
            ownerManager.add(newOwner);
            ownerManager.saveToFile("src/File/owners.txt"); // Đường dẫn tới file lưu Owner
            System.out.println("Thêm chủ sở hữu mới thành công!");
        } else {
            System.out.println("Không thể thêm chủ sở hữu do trùng ID.");
        }
    }

    private static void removeOwner() {
        System.out.print("\nNhập ownerId cần xóa: ");
        String ownerId = scanner.nextLine();

        // Yêu cầu xác nhận trước khi xóa
        System.out.print("Bạn có chắc chắn muốn xóa chủ sở hữu với ID: " + ownerId + " (y/n)? ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            ownerManager.remove(ownerId);
            ownerManager.saveToFile("src/File/owners.txt");
            System.out.println("Xóa chủ sở hữu thành công!");
        } else {
            System.out.println("Hủy bỏ xóa chủ sở hữu.");
        }
    }

    private static void updateOwner() {
        System.out.print("\nNhập ownerId cần cập nhật: ");
        String ownerId = scanner.nextLine();

        // Lấy đối tượng Owner cần cập nhật
        Owner existingOwner = ownerManager.getOne(ownerId);
        if (existingOwner == null) {
            System.out.println("Không tìm thấy chủ sở hữu với ID: " + ownerId);
            return;
        }

        System.out.println("Cập nhật chủ sở hữu với ID: " + ownerId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập thông tin mới cho Owner
        System.out.print("Tên hiện tại: " + existingOwner.getFullName() + " -> ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            existingOwner.setFullName(newName);
        }

        System.out.print("Ngày sinh hiện tại (yyyy-MM-dd): " + existingOwner.getDateOfBirth() + " -> ");
        String newDateOfBirth = scanner.nextLine();
        if (!newDateOfBirth.isEmpty()) {
            try {
                Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(newDateOfBirth);
                existingOwner.setDateOfBirth(date);
            } catch (ParseException e) {
                System.out.println("Ngày sinh không hợp lệ. Định dạng đúng: yyyy-MM-dd");
            }
        }

        System.out.print("Thông tin liên hệ hiện tại: " + existingOwner.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingOwner.setContactInfo(newContactInfo);
        }

        // Yêu cầu xác nhận trước khi cập nhật
        System.out.print("Bạn có chắc chắn muốn cập nhật thông tin của chủ sở hữu này? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            // Thực hiện cập nhật
            ownerManager.update(existingOwner);
            ownerManager.saveToFile("src/File/owners.txt");
            System.out.println("Cập nhật chủ sở hữu thành công!");
        } else {
            System.out.println("Hủy bỏ cập nhật chủ sở hữu.");
        }
    }
    private static void removeCommercialProperty() {
        System.out.print("\nNhập propertyId cần xóa: ");
        String propertyId = scanner.nextLine();

        // Xác nhận người dùng có muốn xóa bất động sản này không
        System.out.print("Bạn có chắc chắn muốn xóa bất động sản này không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            commercialPropertyManager.remove(propertyId);
            System.out.println("Bất động sản thương mại với propertyId " + propertyId + " đã được xóa thành công.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động xóa bất động sản đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận xóa hoặc 'n' để hủy.");
        }
    }
    private static void updateCommercialProperty() {
        System.out.print("\nNhập propertyId cần cập nhật: ");
        String propertyId = scanner.nextLine();
        CommercialProperty existingProperty = commercialPropertyManager.getOne(propertyId);

        if (existingProperty == null) {
            System.out.println("Không tìm thấy property với propertyId: " + propertyId);
            return;
        }

        System.out.println("Cập nhật bất động sản thương mại với ID: " + propertyId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập các thông tin mới, nếu không thay đổi thì giữ nguyên giá trị cũ.
        System.out.print("Địa chỉ hiện tại: " + existingProperty.getAddress() + " -> ");
        String newAddress = scanner.nextLine();
        if (newAddress.isEmpty()) {
            newAddress = existingProperty.getAddress();
        }

        System.out.print("Giá thuê hiện tại: " + existingProperty.getPricing() + " -> ");
        String newPricingStr = scanner.nextLine();
        double newPricing = newPricingStr.isEmpty() ? existingProperty.getPricing() : Double.parseDouble(newPricingStr);

        System.out.print("Trạng thái hiện tại: " + existingProperty.getStatus() + " -> ");
        String newStatusStr = scanner.nextLine();
        Property.PropertyStatus newStatus = newStatusStr.isEmpty() ? existingProperty.getStatus() : Property.PropertyStatus.valueOf(newStatusStr.toUpperCase());

        System.out.print("Loại hình kinh doanh hiện tại: " + existingProperty.getBusinessType() + " -> ");
        String newBusinessType = scanner.nextLine();
        if (newBusinessType.isEmpty()) {
            newBusinessType = existingProperty.getBusinessType();
        }

        System.out.print("Số chỗ đậu xe hiện tại: " + existingProperty.getParkingSpaces() + " -> ");
        String newParkingSpacesStr = scanner.nextLine();
        int newParkingSpaces = newParkingSpacesStr.isEmpty() ? existingProperty.getParkingSpaces() : Integer.parseInt(newParkingSpacesStr);

        System.out.print("Diện tích hiện tại: " + existingProperty.getSquareFootage() + " -> ");
        String newSquareFootageStr = scanner.nextLine();
        double newSquareFootage = newSquareFootageStr.isEmpty() ? existingProperty.getSquareFootage() : Double.parseDouble(newSquareFootageStr);

        // Thực hiện cập nhật
        CommercialProperty updatedProperty = new CommercialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newBusinessType,
                newParkingSpaces,
                newSquareFootage
        );

        // Xác nhận người dùng có muốn cập nhật không
        System.out.print("Bạn có muốn lưu thay đổi này không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            commercialPropertyManager.update(updatedProperty);
            System.out.println("Cập nhật bất động sản thương mại thành công!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động cập nhật đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận lưu thay đổi hoặc 'n' để hủy.");
        }
    }
    private static void addNewCommercialProperty() {
        CommercialProperty newProperty = commercialPropertyManager.inputCommercialPropertyData();
        if (newProperty != null) {
            // Xác nhận người dùng có muốn thêm không
            System.out.print("Bạn có muốn thêm bất động sản thương mại này không? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                commercialPropertyManager.add(newProperty);
                commercialPropertyManager.saveToFile("src/File/commercial_properties.txt");
                System.out.println("Thêm bất động sản thương mại mới thành công!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Hành động thêm bất động sản đã bị hủy.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận thêm hoặc 'n' để hủy.");
            }
        } else {
            System.out.println("Không thể thêm bất động sản thương mại do trùng ID.");
        }
    }
    private static void removeResidentialProperty() {
        System.out.print("\nNhập propertyId cần xóa: ");
        String propertyId = scanner.nextLine();

        // Xác nhận người dùng có muốn xóa bất động sản này không
        System.out.print("Bạn có chắc chắn muốn xóa bất động sản này không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            residentialPropertyManager.remove(propertyId);
            System.out.println("Bất động sản dân dụng với propertyId " + propertyId + " đã được xóa thành công.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động xóa bất động sản đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận xóa hoặc 'n' để hủy.");
        }
    }
    private static void updateResidentialProperty() {
        System.out.print("\nNhập propertyId cần cập nhật: ");
        String propertyId = scanner.nextLine();
        ResidentialProperty existingProperty = residentialPropertyManager.getOne(propertyId);

        if (existingProperty == null) {
            System.out.println("Không tìm thấy property với propertyId: " + propertyId);
            return;
        }

        System.out.println("Cập nhật bất động sản dân dụng với ID: " + propertyId);
        System.out.println("Nhấn Enter để giữ nguyên giá trị cũ.");

        // Nhập các thông tin mới, nếu không thay đổi thì giữ nguyên giá trị cũ.
        System.out.print("Địa chỉ hiện tại: " + existingProperty.getAddress() + " -> ");
        String newAddress = scanner.nextLine();
        if (newAddress.isEmpty()) {
            newAddress = existingProperty.getAddress();
        }

        System.out.print("Giá thuê hiện tại: " + existingProperty.getPricing() + " -> ");
        String newPricingStr = scanner.nextLine();
        double newPricing = newPricingStr.isEmpty() ? existingProperty.getPricing() : Double.parseDouble(newPricingStr);

        System.out.print("Trạng thái hiện tại: " + existingProperty.getStatus() + " -> ");
        String newStatusStr = scanner.nextLine();
        Property.PropertyStatus newStatus = newStatusStr.isEmpty() ? existingProperty.getStatus() : Property.PropertyStatus.valueOf(newStatusStr.toUpperCase());

        System.out.print("Số phòng ngủ hiện tại: " + existingProperty.getNumBedrooms() + " -> ");
        String newNumBedroomsStr = scanner.nextLine();
        int newNumBedrooms = newNumBedroomsStr.isEmpty() ? existingProperty.getNumBedrooms() : Integer.parseInt(newNumBedroomsStr);

        System.out.print("Có vườn không (true/false): " + existingProperty.isGardenAvailability() + " -> ");
        String newGardenAvailabilityStr = scanner.nextLine();
        boolean newGardenAvailability = newGardenAvailabilityStr.isEmpty() ? existingProperty.isGardenAvailability() : Boolean.parseBoolean(newGardenAvailabilityStr);

        System.out.print("Thân thiện với thú cưng không (true/false): " + existingProperty.isPetFriendliness() + " -> ");
        String newPetFriendlinessStr = scanner.nextLine();
        boolean newPetFriendliness = newPetFriendlinessStr.isEmpty() ? existingProperty.isPetFriendliness() : Boolean.parseBoolean(newPetFriendlinessStr);

        // Thực hiện cập nhật
        ResidentialProperty updatedProperty = new ResidentialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newNumBedrooms,
                newGardenAvailability,
                newPetFriendliness
        );

        // Xác nhận người dùng có muốn cập nhật không
        System.out.print("Bạn có muốn lưu thay đổi này không? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            residentialPropertyManager.update(updatedProperty);
            System.out.println("Cập nhật bất động sản dân dụng thành công!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Hành động cập nhật đã bị hủy.");
        } else {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận lưu thay đổi hoặc 'n' để hủy.");
        }
    }
    private static void addNewResidentialProperty() {
        ResidentialProperty newProperty = residentialPropertyManager.inputResidentialPropertyData();
        if (newProperty != null) {
            // Xác nhận người dùng có muốn thêm không
            System.out.print("Bạn có muốn thêm bất động sản dân dụng này không? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                residentialPropertyManager.add(newProperty);
                residentialPropertyManager.saveToFile("src/File/residential_properties.txt");
                System.out.println("Thêm bất động sản dân dụng mới thành công!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Hành động thêm bất động sản đã bị hủy.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'y' để xác nhận thêm hoặc 'n' để hủy.");
            }
        } else {
            System.out.println("Không thể thêm bất động sản dân dụng do trùng ID.");
        }
    }
    private static void displayResidentialProperties() {
        System.out.println("\nDanh sách bất động sản dân dụng hiện tại:");
        for (ResidentialProperty property : residentialPropertyManager.getAll()) {
            System.out.println(property.toString());
        }
    }
    private static void displayCommercialProperties() {
        System.out.println("\nDanh sách bất động sản thương mại hiện tại:");
        for (CommercialProperty property : commercialPropertyManager.getAll()) {
            System.out.println(property.toString());
        }
    }
    private  static void getByOwnerName() {
        System.out.println("\nNhập OwnerName: ");
        String ownerNameStr = scanner.nextLine();
        List<RentalAgreement> listR = rentalAgreementManager.getByOwnerName(ownerNameStr);
        for (RentalAgreement rentalAgreement : listR) {
            System.out.println(rentalAgreement.toString());
            System.out.println("---------------------------------------------------------");
        }
    }
    private static void getByPropertyAddress() {
        System.out.println("\nNhập Property Address: ");
        String propertyAddressStr = scanner.nextLine();
        List<RentalAgreement> listR = rentalAgreementManager.getByPropertyAddress(propertyAddressStr);
        if (listR.isEmpty()) {
            System.out.println("Không tìm thấy hợp đồng nào với địa chỉ bất động sản: " + propertyAddressStr);
        } else {
            for (RentalAgreement rentalAgreement : listR) {
                System.out.println(rentalAgreement.toString());
                System.out.println("---------------------------------------------------------");
            }
        }
    }
    private static void getByStatus() {
        System.out.println("\nNhập trạng thái hợp đồng (NEW, ACTIVE, COMPLETED): ");
        String statusStr = scanner.nextLine().toUpperCase();

        try {
            RentalAgreement.RentalAgreementStatus status = RentalAgreement.RentalAgreementStatus.valueOf(statusStr);
            List<RentalAgreement> listR = rentalAgreementManager.getByStatus(status);

            if (listR.isEmpty()) {
                System.out.println("Không tìm thấy hợp đồng nào với trạng thái: " + status);
            } else {
                for (RentalAgreement rentalAgreement : listR) {
                    System.out.println(rentalAgreement.toString());
                    System.out.println("---------------------------------------------------------");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Trạng thái không hợp lệ. Vui lòng nhập lại (NEW, ACTIVE, COMPLETED).");
        }
    }
    private static void displayRentalAgreements() {
        System.out.println("\nDanh sách hợp đồng thuê hiện tại:");
        for (RentalAgreement agreement : rentalAgreementManager.getAll()) {
            System.out.println(agreement.toString());
            System.out.println("---------------------------------------------------------");
        }
    }
}
