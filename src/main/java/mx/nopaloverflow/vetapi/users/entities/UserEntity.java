package mx.nopaloverflow.vetapi.users.entities;

import mx.nopaloverflow.vetapi.core.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "users")
public class UserEntity extends BaseEntity {
    @Column
    private String name;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String address;
    @Column(name = "user_type")
    private String userType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
