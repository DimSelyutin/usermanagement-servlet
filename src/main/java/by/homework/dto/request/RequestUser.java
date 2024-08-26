package by.homework.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    private String firstname;
    private String email;
    private int age;
    @JsonIgnore
    private String userType; // E.g., "guest" or "common"
    @JsonIgnore
    private String guestPass;
    @JsonIgnore
    private String membershipType;
}