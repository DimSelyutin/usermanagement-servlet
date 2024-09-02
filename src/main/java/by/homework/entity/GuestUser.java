package by.homework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "guest_users")
public class GuestUser extends User {
    private String guestPass;

    // Getters and setters

    public String getGuestPass() {
        return guestPass;
    }

    public GuestUser(String firstname, String email, int age, String guestPass) {
        super(null, firstname, email, age, null);
        this.guestPass = guestPass;
    }

    public void setGuestPass(String guestPass) {
        this.guestPass = guestPass;
    }
}