package Main;

import Classes.*;
import Interface.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final CommercialPropertyManager commercialPropertyManager = new CommercialPropertyManager(); // Khởi tạo đối tượng CommercialPropertyManager
    private static final ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager(); // Khởi tạo đối tượng ResidentialPropertyManager
    private static final PaymentManager paymentManager = new PaymentManager(); // Khởi tạo đối tượng PaymentManager
    private static final Scanner scanner = new Scanner(System.in); // Khởi tạo Scanner
    private static TenantManager tenantManager = new TenantManager(); // Khởi tạo đối tượng TenantManager
    private static HostManager hostManager = new HostManager();


    public static void main(String[] args) {
        // Tải dữ liệu từ file
        commercialPropertyManager.loadFromFile("src/File/commercial_properties.txt");
        residentialPropertyManager.loadFromFile("src/File/residential_properties.txt"); // Tải dữ liệu ResidentialProperty
        paymentManager.loadFromFile("src/File/payments.txt");
        tenantManager.loadFromFile("src/File/tenant.txt");
        hostManager.loadFromFile("src/File/hosts.txt");
        int choice = 0;
        do {
            try {
                displayMenu();
                System.out.print("Nhập lựa chọn của bạn: ");
                choice = Integer.parseInt(scanner.nextLine()); // Xử lý ngoại lệ khi nhập không phải số
                switch (choice) {
                    case 1 -> displayPayments();
                    case 2 -> addNewPayment();
                    case 3 -> removePayment();
                    case 4 -> updatePayment();
                    case 5 -> displayCommercialProperties();
                    case 6 -> addNewCommercialProperty();
                    case 7 -> removeCommercialProperty();
                    case 8 -> updateCommercialProperty();
                    case 9 -> displayResidentialProperties();
                    case 10 -> addNewResidentialProperty();
                    case 11 -> removeResidentialProperty();
                    case 12 -> updateResidentialProperty();
                    case 13 -> displayTenants();
                    case 14 -> addNewTenant();
                    case 15 -> removeTenant();
                    case 16 -> updateTenant();
                    case 17 -> displayHosts();
                    case 18 -> addNewHost();
                    case 19 -> removeHost();
                    case 20 -> updateHost();
                    case 21 -> System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        } while (choice != 21);

        scanner.close(); // Đóng scanner sau khi sử dụng
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
        hostManager.remove(hostId);
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
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(newDateOfBirth);
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

        // Thực hiện cập nhật
        hostManager.update(existingHost);
        hostManager.saveToFile("src/File/hosts.txt");
        System.out.println("Cập nhật Host thành công!");
    }





    // Quản lý Tenant
    private static void displayTenants() {
        System.out.println("\nDanh sách tenants hiện tại:");
        for (Tenant tenant : tenantManager.getAll()) {
            System.out.println(tenant.toString());
        }
    }
    private static void addNewTenant() {
        Tenant newTenant = tenantManager.inputTenantData();
        if (tenantManager.add(newTenant)) {
            System.out.println("Thêm tenant mới thành công!");
            tenantManager.saveToFile("src/File/tenants.txt");
        } else {
            System.out.println("Không thể thêm tenant.");
        }
    }



    private static void removeTenant() {
        System.out.print("\nNhập tenantId cần xóa: ");
        String tenantId = scanner.nextLine();
        tenantManager.remove(tenantId);
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

        // Thực hiện cập nhật trong tenantManager
        tenantManager.update(existingTenant);
        System.out.println("Cập nhật tenant thành công!");
        tenantManager.saveToFile("src/File/tenants.txt");
    }

    // Quản lý thanh toán
    private static void removePayment() {
        System.out.print("\nNhập paymentId cần xóa: ");
        String paymentId = scanner.nextLine();
        paymentManager.remove(paymentId);
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
    }

    private static void displayPayments() {
        System.out.println("\nDanh sách thanh toán hiện tại:");
        for (Payment pm : paymentManager.getAll()) {
            System.out.println(pm.toString());
        }
    }

    private static void addNewPayment() {
        Payment newPayment = paymentManager.inputPaymentData();
        if (newPayment != null) {
            paymentManager.add(newPayment);
            paymentManager.saveToFile("src/File/payments.txt");
            System.out.println("Thêm thanh toán mới thành công!");
        } else {
            System.out.println("Không thể thêm payment do trùng ID.");
        }
    }

    // Quản lý bất động sản thương mại
    private static void removeCommercialProperty() {
        System.out.print("\nNhập propertyId cần xóa: ");
        String propertyId = scanner.nextLine();
        commercialPropertyManager.remove(propertyId);
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

        // Cập nhật đối tượng CommercialProperty
        CommercialProperty updatedProperty = new CommercialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newBusinessType,
                newParkingSpaces,
                newSquareFootage
        );

        // Thực hiện cập nhật
        commercialPropertyManager.update(updatedProperty);
        System.out.println("Cập nhật bất động sản thương mại thành công!");
    }


    private static void displayCommercialProperties() {
        System.out.println("\nDanh sách bất động sản thương mại hiện tại:");
        for (CommercialProperty property : commercialPropertyManager.getAll()) {
            System.out.println(property.toString());
        }
    }

    private static void addNewCommercialProperty() {
        CommercialProperty newProperty = commercialPropertyManager.inputCommercialPropertyData();
        if (newProperty != null) {
            commercialPropertyManager.add(newProperty);
            commercialPropertyManager.saveToFile("src/File/commercial_properties.txt");
            System.out.println("Thêm bất động sản thương mại mới thành công!");
        } else {
            System.out.println("Không thể thêm bất động sản thương mại do trùng ID.");
        }
    }

    // Quản lý bất động sản dân dụng
    private static void removeResidentialProperty() {
        System.out.print("\nNhập propertyId cần xóa: ");
        String propertyId = scanner.nextLine();
        residentialPropertyManager.remove(propertyId);
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

        // Cập nhật đối tượng ResidentialProperty
        ResidentialProperty updatedProperty = new ResidentialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newNumBedrooms,
                newGardenAvailability,
                newPetFriendliness
        );

        // Thực hiện cập nhật
        residentialPropertyManager.update(updatedProperty);
        System.out.println("Cập nhật bất động sản dân dụng thành công!");
    }
    private static void displayResidentialProperties() {
        System.out.println("\nDanh sách bất động sản dân dụng hiện tại:");
        for (ResidentialProperty property : residentialPropertyManager.getAll()) {
            System.out.println(property.toString());
        }
    }

    private static void addNewResidentialProperty() {
        ResidentialProperty newProperty = residentialPropertyManager.inputResidentialPropertyData();
        if (newProperty != null) {
            residentialPropertyManager.add(newProperty);
            residentialPropertyManager.saveToFile("src/File/residential_properties.txt");
            System.out.println("Thêm bất động sản dân dụng mới thành công!");
        } else {
            System.out.println("Không thể thêm bất động sản dân dụng do trùng ID.");
        }
    }

    // Hiển thị menu
    private static void displayMenu() {
        System.out.println("\n==== QUẢN LÝ CHƯƠNG TRÌNH ====");
        System.out.println("1. Xem danh sách thanh toán:");
        System.out.println("2. Thêm thanh toán");
        System.out.println("3. Xóa thanh toán mới");
        System.out.println("4. Sửa thanh toán");
        System.out.println("5. Xem danh sách Bất động sản thương mại");
        System.out.println("6. Thêm Bất động sản thương mại:");
        System.out.println("7. Xóa Bất động sản thương mại");
        System.out.println("8. Sửa Bất động sản thương mại");
        System.out.println("9. Xem danh sách Bất động sản dân dụng");
        System.out.println("10. Thêm Bất động sản dân dụng");
        System.out.println("11. Xóa Bất động sản dân dụng");
        System.out.println("12. Sửa Bất động sản dân dụng");
        System.out.println("13. Hiển thị danh sách tenant");
        System.out.println("14. Thêm tenant mới");
        System.out.println("15. Xóa tenant");
        System.out.println("16. Cập nhật tenant");
        System.out.println("17. Hiển thị danh sách Host");
        System.out.println("18. Thêm Host mới");
        System.out.println("19. Xóa Host");
        System.out.println("20. Cập nhật Host");
        System.out.println("21. Thoát");
        System.out.println("Vui lòng chọn option: ");
    }
}
