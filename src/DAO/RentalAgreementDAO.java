package DAO;

import Classes.*;
import Interface.HostManager;
import Interface.OwnerManager;
import Interface.TenantManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalAgreementDAO {
    private static final OwnerManager ownerManager = new OwnerManager();
    private static final TenantManager tenantManager = new TenantManager();
    private static final HostManager hostManager = new HostManager();
    private static final String FILE_PATH = "src/File/rental_agreements.txt";

    // Convert RentalAgreement object to String for file writing
    private String convertRentalAgreementToString(RentalAgreement agreement) {
        return String.join(",",
                agreement.getContractId(),
                agreement.getOwner().getId(),
                agreement.getMainTenant().getId(),
                convertSubTenantsToString(agreement.getSubTenants()),
                convertPropertyToString(agreement.getRentedProperty()),
                convertHostsToString(agreement.getHosts()),
                agreement.getRentalCycle().name(),
                String.valueOf(agreement.getDuration()),
                agreement.getContractTerms(),
                String.valueOf(agreement.getRentalFee()),
                agreement.getStatus().name()
        );
    }

    // Convert String from file to RentalAgreement object
    private RentalAgreement convertStringToRentalAgreement(String line) {
        String[] parts = line.split(",");
        if (parts.length < 11) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String contractId = parts[0];
        ownerManager.loadFromFile("src/File/owners.txt");
        Owner owner = ownerManager.getOne(parts[1]);

        tenantManager.loadFromFile("src/File/tenants.txt");
        Tenant mainTenant = tenantManager.getOne(parts[2]);

        List<Tenant> subTenants = convertStringToSubTenants(parts[3]);
        Property rentedProperty = convertStringToProperty(parts[4]);
        List<Host> hosts = convertStringToHosts(parts[5]);
        RentalAgreement.RentalCycleType rentalCycle = RentalAgreement.RentalCycleType.valueOf(parts[6]);
        int duration = Integer.parseInt(parts[7]);
        String contractTerms = parts[8];
        double rentalFee = Double.parseDouble(parts[9]);
        RentalAgreement.RentalAgreementStatus status = RentalAgreement.RentalAgreementStatus.valueOf(parts[10]);

        return new RentalAgreement(contractId, owner, mainTenant, subTenants, rentedProperty, hosts,
                rentalCycle, duration, contractTerms, rentalFee, status);
    }

    // Serialize Property object to String
    private String convertPropertyToString(Property property) {
        if (property instanceof CommercialProperty) {
            CommercialProperty cp = (CommercialProperty) property;
            return String.join(";",
                    "CommercialProperty",
                    cp.getPropertyId(),
                    cp.getAddress(),
                    String.valueOf(cp.getPricing()),
                    cp.getStatus().name(),
                    cp.getBusinessType(),
                    String.valueOf(cp.getParkingSpaces()),
                    String.valueOf(cp.getSquareFootage())
            );
        } else if (property instanceof ResidentialProperty) {
            ResidentialProperty rp = (ResidentialProperty) property;
            return String.join(";",
                    "ResidentialProperty",
                    rp.getPropertyId(),
                    rp.getAddress(),
                    String.valueOf(rp.getPricing()),
                    rp.getStatus().name(),
                    String.valueOf(rp.getNumBedrooms()),
                    String.valueOf(rp.isGardenAvailability()),
                    String.valueOf(rp.isPetFriendliness())
            );
        } else {
            throw new IllegalArgumentException("Unknown property type: " + property.getClass().getSimpleName());
        }
    }

    // Deserialize Property from String
    private Property convertStringToProperty(String propertyString) {
        String[] parts = propertyString.split(";");
        String propertyType = parts[0];
        switch (propertyType) {
            case "CommercialProperty":
                return new CommercialProperty(
                        parts[1], parts[2], Double.parseDouble(parts[3]), Property.PropertyStatus.valueOf(parts[4]),
                        parts[5], Integer.parseInt(parts[6]), Double.parseDouble(parts[7]));
            case "ResidentialProperty":
                return new ResidentialProperty(
                        parts[1], parts[2], Double.parseDouble(parts[3]), Property.PropertyStatus.valueOf(parts[4]),
                        Integer.parseInt(parts[5]), Boolean.parseBoolean(parts[6]), Boolean.parseBoolean(parts[7]));
            default:
                throw new IllegalArgumentException("Unknown property type: " + propertyType);
        }
    }

    // Convert subTenants list to String
    private String convertSubTenantsToString(List<Tenant> subTenants) {
        StringBuilder result = new StringBuilder();
        for (Tenant tenant : subTenants) {
            result.append(tenant.getId()).append(";");
        }
        return result.toString();
    }

    // Convert String to subTenants list
    private List<Tenant> convertStringToSubTenants(String subTenantsString) {
        List<Tenant> subTenants = new ArrayList<>();
        String[] ids = subTenantsString.split(";");

        tenantManager.loadFromFile("src/File/tenants.txt"); // Load danh sách Tenant từ file

        for (String id : ids) {
            if (!id.isEmpty()) {
                Tenant tenant = tenantManager.getOne(id); // Lấy đối tượng Tenant đầy đủ từ TenantManager
                if (tenant != null) {
                    subTenants.add(tenant);
                } else {
                    System.err.println("SubTenant not found for ID: " + id);
                }
            }
        }
        return subTenants;
    }

    // Convert hosts list to String
    private String convertHostsToString(List<Host> hosts) {
        StringBuilder result = new StringBuilder();
        for (Host host : hosts) {
            result.append(host.getId()).append(";");
        }
        return result.toString();
    }

    // Convert String to hosts list
    private List<Host> convertStringToHosts(String hostsString) {
        List<Host> hosts = new ArrayList<>();
        String[] ids = hostsString.split(";");

        hostManager.loadFromFile("src/File/hosts.txt"); // Load danh sách Host từ file

        for (String id : ids) {
            if (!id.isEmpty()) {
                Host host = hostManager.getOne(id); // Lấy đối tượng Host đầy đủ từ HostManager
                if (host != null) {
                    hosts.add(host);
                } else {
                    System.err.println("Host not found for ID: " + id);
                }
            }
        }
        return hosts;
    }

    // Write a list of RentalAgreements to a file
    public void writeToFile(List<RentalAgreement> agreements,String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RentalAgreement agreement : agreements) {
                writer.write(convertRentalAgreementToString(agreement));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read a list of RentalAgreements from a file
    public List<RentalAgreement> readFromFile() {
        List<RentalAgreement> agreements = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("Rental agreements file does not exist.");
            return agreements; // Return empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RentalAgreement agreement = convertStringToRentalAgreement(line);
                if (agreement != null) {
                    agreements.add(agreement);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agreements;
    }

    // Update rental agreement information in the list and write it back to the file
    public boolean update(RentalAgreement updatedAgreement) {
        List<RentalAgreement> agreements = readFromFile();

        boolean agreementFound = false;
        for (int i = 0; i < agreements.size(); i++) {
            if (agreements.get(i).getContractId().equals(updatedAgreement.getContractId())) {
                agreements.set(i, updatedAgreement);
                agreementFound = true;
                break;
            }
        }

        if (agreementFound) {
            writeToFile(agreements,FILE_PATH);
            System.out.println("Rental Agreement updated successfully!");
            return true;
        } else {
            System.out.println("Rental Agreement not found!");
            return false;
        }
    }

    // Delete rental agreement by contractId and update the file
    public boolean delete(String contractId) {
        List<RentalAgreement> agreements = readFromFile();

        boolean agreementFound = false;
        Iterator<RentalAgreement> iterator = agreements.iterator();
        while (iterator.hasNext()) {
            RentalAgreement agreement = iterator.next();
            if (agreement.getContractId().equals(contractId)) {
                iterator.remove();
                agreementFound = true;
                break;
            }
        }

        if (agreementFound) {
            writeToFile(agreements,FILE_PATH);
            System.out.println("Rental Agreement deleted successfully!");
            return true;
        } else {
            System.out.println("Rental Agreement not found!");
            return false;
        }
    }
}
