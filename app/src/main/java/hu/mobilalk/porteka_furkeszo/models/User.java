package hu.mobilalk.porteka_furkeszo.models;

import java.util.Date;

public class User {
    private final String uid;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final Date registeredOn;
    private final String password;

    public User(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.registeredOn = new Date();
        this.password = password;
    }

}
