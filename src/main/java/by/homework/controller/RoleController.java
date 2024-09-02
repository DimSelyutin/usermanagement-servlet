package by.homework.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.homework.dto.request.RequestRole;
import by.homework.dto.request.RequestUpdateRole;
import by.homework.entity.Role;
import by.homework.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RequestRole requestRole) {
        try {
            // Создание новой Role из данных запроса
            Role role = new Role(requestRole.getName(), requestRole.getDescription());

            // Сохранение новой роли
            roleService.saveRole(role);

            // Возвращаем подтверждение в виде JSON ответа с ролью и статусом 201 Created
            return ResponseEntity.status(201).body(role);
        } catch (Exception e) {
            log.error("Error creating role: {}", e.getLocalizedMessage());

            // Возвращаем ошибку с статусом 500 Internal Server Error и сообщением об ошибке
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRole(@RequestParam("id") Long roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the role: " + e.getMessage());
        }
    }

    // @GetMapping
    // public ResponseEntity<List<Role>> getAllRoles() {
    // try {
    // List<Role> roles = roleService.getAllRoles();
    // return ResponseEntity.ok(roles);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(Collections.singletonList("An error occurred while retrieving
    // roles."));
    // }
    // }

    @GetMapping("/20kk")
    public ResponseEntity<List<Role>> getRolesWithIdGreaterThan2000(
            @RequestParam("page") int pageNumber,
            @RequestParam("size") int pageSize) {

        long startTime = System.currentTimeMillis();
        try {
            List<Role> roles = roleService.getRolesWithIdGreaterThan2000(pageNumber, pageSize);
            long endTime = System.currentTimeMillis();

            log.info("Время выполнения запроса: {}ms", (endTime - startTime));
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            log.error("An error occurred while retrieving roles.", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody RequestUpdateRole requestUpdateRole) {
        if (requestUpdateRole.getName() == null || requestUpdateRole.getDescription() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Please provide the name and description parameters.");
        }

        Role updatedRole = new Role(
                requestUpdateRole.getId(),
                requestUpdateRole.getName(),
                requestUpdateRole.getDescription(),
                null,
                null);

        try {
            roleService.updateRole(updatedRole);
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the role: " + e.getMessage());
        }
    }
}