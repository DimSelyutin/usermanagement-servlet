package by.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String email;
    private int age;

    public User(String firstname, String email, int age) {
        this.firstname = firstname;
        this.email = email;
        this.age = age;
    }

    public User(Long i, String firstname, String email, int age) {
        this.firstname = firstname;
        this.email = email;
        this.age = age;
    }
}