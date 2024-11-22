package Classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public abstract class Person {
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String contactInfo;

    public Person(String fullName, String id, Date dateOfBirth, String contactInfo) {
        this.fullName = fullName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.contactInfo = contactInfo;
    }

    public Person(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "Person{" +
                "id: " + id + '\'' +
                "| fullName: " + fullName + '\'' +
                "| dateOfBirth: " + dateFormat.format(dateOfBirth) +
                "| contactInfo: " + contactInfo + '\'' +
                '}';
    }
}
