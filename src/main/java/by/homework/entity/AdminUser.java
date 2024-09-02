package by.homework.entity;

import javax.persistence.*;

@Entity
@Table(name = "admin_users")
public class AdminUser extends User {

    private String adminLevel;

    public AdminUser() {
        super();
    }

    public AdminUser(String firstname, String email, int age, String adminLevel) {
        super(null, firstname, email, age, null);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}
