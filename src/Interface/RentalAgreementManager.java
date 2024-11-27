package Interface;

import Classes.*;
import DAO.RentalAgreementDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RentalAgreementManager implements RentalManager<RentalAgreement> {
    private List<RentalAgreement> agreements = new ArrayList<>();
    private RentalAgreementDAO rentalAgreementDAO = new RentalAgreementDAO(); // Tạo đối tượng RentalAgreementDAO để lưu và tải từ file
    private TenantManager tenantManager = new TenantManager();
    private HostManager hostManager = new HostManager();
    private static String FILE_PATH = "src/File/rental_agreements.txt";
    @Override
    public boolean add(RentalAgreement item) {
        if (agreements.contains(item)) {
            System.out.println("Error: contractId already exists: " + item.getContractId());
            return false;
        }
        agreements.add(item);
        System.out.println("Rental Agreement successfully added: " + item);
        return true;
    }

    @Override
    public void update(RentalAgreement item) {
        if (rentalAgreementDAO.update(item)) {
            System.out.println("Rental Agreement updated successfully!");
        } else {
            System.out.println("No rental agreement found with contractId: " + item.getContractId());
        }
    }

    @Override
    public void remove(String id) {
        boolean removed = agreements.removeIf(agreement -> agreement.getContractId().equals(id));
        if (removed) {
            System.out.println("Rental Agreement with contractId removed: " + id);
            saveToFile("src/File/rental_agreements.txt"); // Cập nhật dữ liệu vào file sau khi xóa
        } else {
            System.out.println("No rental agreement found with contractId: " + id);
        }
    }

    @Override
    public RentalAgreement getOne(String id) {
        for (RentalAgreement agreement : agreements) {
            if (agreement.getContractId().equals(id)) {
                return agreement;
            }
        }
        return null;
    }

    @Override
    public List<RentalAgreement> getAll() {
        return new ArrayList<>(agreements);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            ids.add(agreement.getContractId());
        }
        return ids;
    }

    @Override
    public List<RentalAgreement> getAllByCustomerID(String customerID) {
        List<RentalAgreement> customerAgreements = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getMainTenant().getId().equals(customerID) ||
                    agreement.getSubTenants().stream().anyMatch(tenant -> tenant.getId().equals(customerID))) {
                customerAgreements.add(agreement);
            }
        }
        return customerAgreements;
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            rentalAgreementDAO.writeToFile(agreements,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            agreements = rentalAgreementDAO.readFromFile();
            if (agreements.isEmpty()) {
                System.out.println("No rental agreement data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }
    public boolean validateContractId(String contractId) {
        // Regular expression to check if the contractId starts with "RA"
        return contractId.matches("^RA\\d+$");

    }

    public RentalAgreement inputRentalAgreementData() {
        tenantManager.loadFromFile("src/File/rental_agreements.txt");
        hostManager.loadFromFile("src/File/hosts.txt");
        RentalAgreement rentalAgreement = new RentalAgreement();
        Scanner scanner = new Scanner(System.in);
        String contractId;

        // Nhập contractId cho hợp đồng
        while (true) {
            System.out.print("Enter contractId (must start with 'RA' followed by natural numbers): ");
            contractId = scanner.nextLine();

            if (!validateContractId(contractId)) {
                System.out.println("Error: contractId must start with 'RA' followed by natural numbers. Please re-enter.");
                continue;
            }
            String finalContractId = contractId;
            boolean exists = agreements.stream().anyMatch(r -> r.getContractId().equals(finalContractId));
            if (exists) {
                System.out.println("Error: contractId already exists. Please re-enter.");
            } else {
                break;
            }
        }

        String tenantId;
        Tenant tenant = null;

        do {
            // Nhập tenantId cho tenant chính
            System.out.print("Enter main tenantId: ");
            tenantId = scanner.nextLine();
            tenant = tenantManager.getOne(tenantId);
            if (tenant != null) {
                System.out.println(tenant);
            } else {
                System.out.println("No tenant found with id: " + tenantId);
            }
        } while (tenant == null);


        List<Tenant> subTenants = new ArrayList<>();
        int subTenantCount;

        // Vòng lặp kiểm tra định dạng cho số lượng sub-tenants
        while (true) {
            try {
                System.out.print("Enter number of sub-tenants: ");
                subTenantCount = Integer.parseInt(scanner.nextLine());
                if (subTenantCount < 0) {
                    System.out.println("Number of sub-tenants cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        // Nhập danh sách sub-tenants
        for (int i = 0; i < subTenantCount; i++) {
            while (true) { // Vòng lặp nếu không tìm thấy tenant
                System.out.print("Enter sub-tenantId: ");
                String subTenantId = scanner.nextLine();
                Tenant temp = tenantManager.getOne(subTenantId);
                if (temp != null) {
                    subTenants.add(temp);
                    System.out.println(temp);
                    break; // Thoát vòng lặp nếu tìm thấy tenant
                } else {
                    System.out.println("Don't find sub-tenant with id: " + subTenantId + ". Please try again.");
                }
            }
        }

        // Hiển thị danh sách sub-tenants đã nhập
        System.out.println("Sub-tenants list:");
        for (Tenant t : subTenants) {
            System.out.println(t);
        }



        List<Host> listHost = new ArrayList<>();
        int hostCount;

        // Vòng lặp kiểm tra định dạng cho số lượng hosts
        while (true) {
            try {
                System.out.print("Enter number of hosts: ");
                hostCount = Integer.parseInt(scanner.nextLine());
                if (hostCount < 0) {
                    System.out.println("Number of hosts cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        // Nhập danh sách hosts
        for (int i = 0; i < hostCount; i++) {
            while (true) { // Vòng lặp nếu không tìm thấy host
                System.out.print("Enter hostId: ");
                String hostId = scanner.nextLine();
                Host host = hostManager.getOne(hostId);
                if (host != null) {
                    listHost.add(host);
                    System.out.println(host);
                    break; // Thoát vòng lặp khi tìm thấy host
                } else {
                    System.out.println("Don't find host with id: " + hostId + ". Please try again.");
                }
            }
        }

        // Hiển thị danh sách hosts đã nhập
        System.out.println("Hosts list:");
        for (Host host : listHost) {
            System.out.println(host);
        }


        // Nhập rentalCycle (chu kỳ thuê) - Đảm bảo biến rentalCycle là final hoặc effectively final
        final RentalAgreement.RentalCycleType[] rentalCycle = new RentalAgreement.RentalCycleType[1]; // Dùng mảng để làm biến final
        while (rentalCycle[0] == null) {
            System.out.print("Enter rental cycle (DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY): ");
            try {
                rentalCycle[0] = RentalAgreement.RentalCycleType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid rental cycle. Please enter again.");
            }
        }

        // Nhập duration (thời gian thuê)
        int duration;
        while (true) {
            System.out.print("Enter duration (in number of units): ");
            duration = scanner.nextInt();
            if (duration <= 0) {
                System.out.println("Duration must be a positive number. Please enter again.");
            } else {
                break;
            }
        }
        scanner.nextLine(); // Consume newline

        // Nhập contractTerms (Điều khoản hợp đồng)
        System.out.print("Enter contract terms: ");
        String contractTerms = scanner.nextLine();

        // Nhập rentalFee (Phí thuê)
        double rentalFee;
        while (true) {
            System.out.print("Enter rental fee: ");
            rentalFee = scanner.nextDouble();
            if (rentalFee <= 0) {
                System.out.println("Rental fee must be a positive value. Please enter again.");
            } else {
                break;
            }
        }
        scanner.nextLine(); // Consume newline

        // Nhập status (trạng thái hợp đồng) - Đảm bảo biến status là final hoặc effectively final
        final RentalAgreement.RentalAgreementStatus[] status = new RentalAgreement.RentalAgreementStatus[1]; // Dùng mảng để làm biến final
        while (status[0] == null) {
            System.out.print("Enter contract status (NEW, ACTIVE, COMPLETED): ");
            try {
                status[0] = RentalAgreement.RentalAgreementStatus.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter again.");
            }
        }

        // Tạo đối tượng OwnerManager và tải dữ liệu
        OwnerManager ownerManager = new OwnerManager();
        ownerManager.loadFromFile("src/File/rental_agreements.txt");

        Owner owner;
        while (true) { // Vòng lặp nếu không tìm thấy owner
            // Nhập thông tin chủ sở hữu (owner)
            System.out.print("Enter ownerId: ");
            String ownerId = scanner.nextLine();

            owner = ownerManager.getOne(ownerId);
            if (owner != null) {
                System.out.println(owner);
                break; // Thoát vòng lặp nếu tìm thấy owner
            } else {
                System.out.println("Owner with id: " + ownerId + " not found. Please try again.");
            }
        }


        System.out.println("Enter Property\n");
        System.out.print("1. Commercial_Property\n");
        System.out.print("2. Residental_Property\n");
        System.out.print("Choses: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                CommercialPropertyManager propertyManager = new CommercialPropertyManager();
                propertyManager.loadFromFile("src/File/rental_agreements.txt");
                CommercialProperty property;

                while (true) { // Vòng lặp nếu không tìm thấy property
                    System.out.print("Enter property id: ");
                    String propertyId = scanner.nextLine();

                    property = propertyManager.getOne(propertyId);
                    if (property != null) {
                        System.out.println("Property found: " + property);
                        break; // Thoát vòng lặp nếu tìm thấy property
                    } else {
                        System.out.println("Property with id: " + propertyId + " not found. Please try again.");
                    }
                }

                // Tạo và trả về RentalAgreement
                rentalAgreement = new RentalAgreement(
                        contractId, owner, tenant, subTenants, property, listHost,
                        rentalCycle[0], duration, contractTerms, rentalFee, status[0]
                );
                break;

            case 2:
                ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager();
                residentialPropertyManager.loadFromFile("src/File/rental_agreements.txt");
                ResidentialProperty residentialProperty;

                while (true) { // Vòng lặp nếu không tìm thấy property
                    System.out.print("Enter property id: ");
                    String propertyID = scanner.nextLine();

                    residentialProperty = residentialPropertyManager.getOne(propertyID);
                    if (residentialProperty != null) {
                        System.out.println("Property found: " + residentialProperty);
                        break; // Thoát vòng lặp nếu tìm thấy property
                    } else {
                        System.out.println("Property with id: " + propertyID + " not found. Please try again.");
                    }
                }

                // Tạo và trả về RentalAgreement
                rentalAgreement = new RentalAgreement(
                        contractId, owner, tenant, subTenants, residentialProperty, listHost,
                        rentalCycle[0], duration, contractTerms, rentalFee, status[0]
                );
                break;

        }
        return rentalAgreement;
    }
    public List<RentalAgreement> getByOwnerName(String ownerName) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getOwner().getFullName().equalsIgnoreCase(ownerName)) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found for owner name: " + ownerName);
        }
        return result;
    }
    // Lấy danh sách hợp đồng theo địa chỉ bất động sản
    public List<RentalAgreement> getByPropertyAddress(String propertyAddress) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getRentedProperty().getAddress().equalsIgnoreCase(propertyAddress)) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found for property address: " + propertyAddress);
        }
        return result;
    }
    public List<RentalAgreement> getByStatus(RentalAgreement.RentalAgreementStatus status) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getStatus() == status) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found with status: " + status);
        }
        return result;
    }
    public void sortRentalAgreementsById() {
        agreements.sort((a1, a2) -> {
            try {
                // Extract the numeric part after the "RA" prefix and convert it to an integer
                int id1 = Integer.parseInt(a1.getContractId().substring(2)); // Remove "RA" prefix and parse the number
                int id2 = Integer.parseInt(a2.getContractId().substring(2)); // Same for the second agreement

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If the ID is not a number, compare the entire contract ID as a string
                return a1.getContractId().compareTo(a2.getContractId());
            }
        });
        System.out.println("Danh sách Rental Agreements đã được sắp xếp theo contractId (tăng dần).");
    }

    public void saveRentalAgreementsBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Tên file backup không hợp lệ.");
            return;
        }

        try {
            rentalAgreementDAO.writeToFile(agreements,backupFileName);
            System.out.println("Danh sách Rental Agreements đã được lưu vào file backup: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file backup: " + backupFileName);
            e.printStackTrace();
        }
    }

}
