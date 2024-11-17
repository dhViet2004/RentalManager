package Main;

import Classes.*;
import Interface.CommercialPropertyManager;
import Interface.ResidentialPropertyManager;
import Interface.PaymentManager;
import java.util.Scanner;

public class Main {
    private static final CommercialPropertyManager commercialPropertyManager = new CommercialPropertyManager(); // Khởi tạo đối tượng CommercialPropertyManager
    private static final ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager(); // Khởi tạo đối tượng ResidentialPropertyManager
    private static final PaymentManager paymentManager = new PaymentManager(); // Khởi tạo đối tượng PaymentManager
    private static final Scanner scanner = new Scanner(System.in); // Khởi tạo Scanner

    public static void main(String[] args) {
        // Tải dữ liệu từ file
        commercialPropertyManager.loadFromFile("src/File/commercial_properties.txt");
        residentialPropertyManager.loadFromFile("src/File/residential_properties.txt"); // Tải dữ liệu ResidentialProperty
        paymentManager.loadFromFile("src/File/payments.txt");

        int choice;
        do {
            // Hiển thị menu
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc dòng mới còn lại trong bộ đệm

            switch (choice) {
                case 1 -> displayPayments();  // Xem danh sách thanh toán
                case 2 -> addNewPayment();    // Thêm thanh toán mới
                case 3 -> removePayment();    // Xóa thanh toán
                case 4 -> updatePayment();    // Cập nhật thanh toán
                case 5 -> displayCommercialProperties();  // Xem danh sách bất động sản thương mại
                case 6 -> addNewCommercialProperty();    // Thêm bất động sản thương mại mới
                case 7 -> removeCommercialProperty();    // Xóa bất động sản thương mại
                case 8 -> updateCommercialProperty();    // Cập nhật bất động sản thương mại
                case 9 -> displayResidentialProperties();  // Xem danh sách bất động sản dân dụng
                case 10 -> addNewResidentialProperty();    // Thêm bất động sản dân dụng mới
                case 11 -> removeResidentialProperty();    // Xóa bất động sản dân dụng
                case 12 -> updateResidentialProperty();    // Cập nhật bất động sản dân dụng
                case 13 -> System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 13);

        scanner.close(); // Đóng scanner sau khi sử dụng
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
        System.out.println("13. Thoát");
        System.out.println("Vui lòng chọn option: ");
    }
}
