package Classes;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RentalAgreement implements Comparable<RentalAgreement> {
    private String contractId;
    private Date ContractDate;
    private Owner owner;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
    private Property rentedProperty;
    private List<Host> hosts;
    private RentalCycleType rentalCycle; // Thêm thuộc tính rentalCycle cho chu kỳ thuê
    private int duration;  // Dùng số lượng cho các đơn vị như ngày, tuần, tháng
    private String contractTerms;
    private double rentalFee;
    private RentalAgreementStatus status;

    // Enum định nghĩa trạng thái hợp đồng
    public enum RentalAgreementStatus {
        NEW, ACTIVE, COMPLETED;
    }

    // Enum định nghĩa chu kỳ thuê
    public enum RentalCycleType {
        DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY;
    }

    // Constructor cho RentalAgreement
    public RentalAgreement(String contractId,Date ConDate,Owner owner ,Tenant mainTenant, List<Tenant> subTenants,
                           Property rentedProperty, List<Host> hosts, RentalCycleType rentalCycle,
                           int duration, String contractTerms, double rentalFee, RentalAgreementStatus status) {
        this.contractId = contractId;
        this.ContractDate = ConDate;
        this.owner = owner;
        this.mainTenant = mainTenant;
        this.subTenants = subTenants;
        this.rentedProperty = rentedProperty;
        this.hosts = hosts;
        this.rentalCycle = rentalCycle;
        this.duration = duration;  // duration là số đơn vị (ngày, tuần, tháng, v.v.)
        this.contractTerms = contractTerms;
        this.rentalFee = rentalFee;
        this.status = status;
    }

    public RentalAgreement() {
    }

    // Getters và Setters
    public RentalCycleType getRentalCycle() {
        return rentalCycle;
    }

    public void setRentalCycle(RentalCycleType rentalCycle) {
        this.rentalCycle = rentalCycle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getContractDate() { return ContractDate;}

    public void setContractDate(Date contractDate) { this.ContractDate = contractDate;}


    public Tenant getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public List<Tenant> getSubTenants() {
        return subTenants;
    }

    public void setSubTenants(List<Tenant> subTenants) {
        this.subTenants = subTenants;
    }

    public Property getRentedProperty() {
        return rentedProperty;
    }

    public void setRentedProperty(Property rentedProperty) {
        this.rentedProperty = rentedProperty;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public String getContractTerms() {
        return contractTerms;
    }

    public void setContractTerms(String contractTerms) {
        this.contractTerms = contractTerms;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public RentalAgreementStatus getStatus() {
        return status;
    }

    public void setStatus(RentalAgreementStatus status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // Triển khai phương thức compareTo để sắp xếp theo rentalFee
    @Override
    public int compareTo(RentalAgreement other) {
        return Double.compare(this.rentalFee, other.rentalFee);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RentalAgreement that = (RentalAgreement) o;
        return Objects.equals(contractId, that.contractId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contractId);
    }

    @Override
    public String toString() {
        // Format the contractDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = (ContractDate != null) ? dateFormat.format(ContractDate) : "N/A";

        // Chuỗi fullname của từng subTenant
        StringBuilder subTenantsNames = new StringBuilder();
        if (subTenants != null && !subTenants.isEmpty()) {
            for (Tenant tenant : subTenants) {
                subTenantsNames.append(tenant.getFullName()).append(", ");
            }
            // Xóa dấu phẩy cuối cùng
            if (subTenantsNames.length() > 0) {
                subTenantsNames.setLength(subTenantsNames.length() - 2);
            }
        } else {
            subTenantsNames.append("Không có");
        }

        // Chuỗi fullname của từng host
        StringBuilder hostsNames = new StringBuilder();
        if (hosts != null && !hosts.isEmpty()) {
            for (Host host : hosts) {
                hostsNames.append(host.getFullName()).append(", ");
            }
            // Xóa dấu phẩy cuối cùng
            if (hostsNames.length() > 0) {
                hostsNames.setLength(hostsNames.length() - 2);
            }
        } else {
            hostsNames.append("Không có");
        }

        return  "| ContractId: " + contractId + "\n" +
                "| ContractDate: " + formattedDate + "\n" +
                "| FullName_Owner: " + owner.getFullName() + "\n" +
                "| MainTenant: " + mainTenant.getFullName() + " | SubTenants: " + subTenantsNames + "\n" +
                "| RentedProperty: " + "\n" + rentedProperty + "\n" +
                "| Hosts: " + hostsNames + "\n" +
                "| rentalCycle: " + rentalCycle + "\n" +
                "| duration: " + duration + "\n" +
                "| contractTerms: " + contractTerms + "\n" +
                "| rentalFee: " + rentalFee + "\n" +
                "| status: " + status;
    }


}
