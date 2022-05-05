package mx.nopaloverflow.vetapi.customers.entities;

import mx.nopaloverflow.vetapi.core.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "customers")
public class CustomerEntity extends BaseEntity {
    @Column
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @Column
    private String address;
    @Column(name = "accepts_notifications")
    private boolean optsInNotifications;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOptsInNotifications() {
        return optsInNotifications;
    }

    public void setOptsInNotifications(boolean optsInNotifications) {
        this.optsInNotifications = optsInNotifications;
    }
}
