package by.homework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestUser {

    private String firstname;
    private String email;
    private int age;

    private String userType; // E.g., "guest" or "common"
    private String adminLevel; // E.g., "guest" or "common"

    private String guestPass;

    private String membershipType;
}