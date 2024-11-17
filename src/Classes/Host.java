package Classes;

import java.util.Date;
import java.util.List;

public class Host extends Person implements Comparable<Host> {
    private List<Property> managedProperties;
    private List<Owner> cooperatingOwners;

    public Host(String fullName, String id, Date dateOfBirth, String contactInfo, List<Property> managedProperties, List<Owner> cooperatingOwners) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.managedProperties = managedProperties;
        this.cooperatingOwners = cooperatingOwners;

    }

    public Host(String id) {
        super(id);
    }

    public List<Property> getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(List<Property> managedProperties) {
        this.managedProperties = managedProperties;
    }

    public List<Owner> getCooperatingOwners() {
        return cooperatingOwners;
    }

    public void setCooperatingOwners(List<Owner> cooperatingOwners) {
        this.cooperatingOwners = cooperatingOwners;
    }

    @Override
    public String toString() {
        return super.toString() +
                "managedProperties=" + managedProperties +
                ", cooperatingOwners=" + cooperatingOwners +
                '}';
    }
    @Override
    public int compareTo(Host other) {
        return Integer.compare(this.managedProperties.size(), other.managedProperties.size());
    }
}
