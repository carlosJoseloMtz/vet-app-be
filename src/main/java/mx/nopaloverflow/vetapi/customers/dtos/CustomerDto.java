package mx.nopaloverflow.vetapi.customers.dtos;

public class CustomerDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean optsInNotifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
