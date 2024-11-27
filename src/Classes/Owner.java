package Classes;

import java.util.Date;
import java.util.List;

public class Owner extends Person{
    private List<Property> ownedProperties;
    private List<Host> managingHosts;

    public Owner(String fullName, String id, Date dateOfBirth, String contactInfo, List<Property> ownedProperties, List<Host> managingHosts) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.ownedProperties = ownedProperties;
        this.managingHosts = managingHosts;
    }

    public Owner(String id) {
        super(id);
    }

    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(List<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public List<Host> getManagingHosts() {
        return managingHosts;
    }

    public void setManagingHosts(List<Host> managingHosts) {
        this.managingHosts = managingHosts;
    }

    @Override
    public String toString() {
        return String.format("%s|%-35s|%-35s",
                super.toString(), // Gọi toString của lớp cha
                ownedProperties != null ? ownedProperties.toString() : "No owned properties",
                managingHosts != null ? managingHosts.toString() : "No managing hosts");
    }

}
