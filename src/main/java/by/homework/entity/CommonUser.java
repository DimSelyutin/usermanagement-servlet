package by.homework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "common_users")
public class CommonUser extends User {
    private String membershipType;

    // Constructors

    public CommonUser() {
        super();
    }

    public CommonUser(String firstname, String email, int age, String membershipType) {
        super(null, firstname, email, age, null);
        this.membershipType = membershipType;
    }

    // Getters and setters

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}