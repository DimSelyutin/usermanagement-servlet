package by.homework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.homework.dto.request.RequestUser;
import by.homework.entity.AdminUser;
import by.homework.entity.CommonUser;
import by.homework.entity.GuestUser;
import by.homework.entity.User;
import by.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RequestUser requestUser) {
        try {
            log.info("Request type: {}", requestUser);
            User user = createUserFromRequest(requestUser);
            userService.insertUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error occurred while creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.selectAllUsers();

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while retrieving users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody RequestUser requestUser) {
        try {
            User updatedUser = createUserFromRequest(requestUser);
            userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Error occurred while updating user: {}", e.getLocalizedMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getLocalizedMessage());
        }
    }

    private User createUserFromRequest(RequestUser requestUser) {
        switch (requestUser.getUserType().toLowerCase()) {
            case "guest":
                return new GuestUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getGuestPass());
            case "admin":
                AdminUser admin = new AdminUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getAdminLevel());
                log.info("Created/Updated Admin User: {}", admin);
                return admin;
            default:
                return new CommonUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getMembershipType());
        }
    }

}