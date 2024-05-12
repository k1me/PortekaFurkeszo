package hu.mobilalk.porteka_furkeszo.models;

import java.util.Date;

public class User {
    private String uid;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private Date registeredOn;
    private String password;

    public User() {

    }

    public User(String uid, String email, String firstName, String lastName, String address, Date registeredOn, String password) {
        this.uid = uid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.registeredOn = registeredOn;
        this.password = password;
    }
    public User(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.registeredOn = new Date();
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
