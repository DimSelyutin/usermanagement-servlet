package by.homework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.homework.dto.request.RequestUserRole;
import by.homework.entity.Role;
import by.homework.service.RoleService;
import by.homework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/role")
public class UserRoleController {

    private final RoleService roleService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> assignRoleToUser(@RequestBody RequestUserRole requestUserRole) {
        try {
            userService.assignRoleToUser(requestUserRole.getUserId(), requestUserRole.getRoleId());
            return ResponseEntity.ok("Role assigned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error assigning role: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateRoles(HttpServletResponse response) throws IOException {
        try {
            createAndSaveRoles();
            return ResponseEntity.ok("Процесс создания ролей запущен.");
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Произошла ошибка при создании и сохранении ролей.");
        }
    }

    @Async
    public void createAndSaveRoles() {
        int batchSize = 10000; // Размер партий для сохранения в базу данных
        int totalCount = 20000000; // Общее количество ролей
        int currentBatch = 0;

        while (currentBatch < totalCount) {
            int remaining = totalCount - currentBatch;
            int currentBatchSize = Math.min(batchSize, remaining);

            List<Role> roles = generateRandomRoles(currentBatch, currentBatchSize);

            // Сохранение сгенерированных ролей партиями
            roleService.saveRoles(roles);

            currentBatch += currentBatchSize;

            log.info("Сохранено {} из {} ролей", currentBatch, totalCount);
        }
    }

    private List<Role> generateRandomRoles(int start, int count) {
        List<Role> roles = new ArrayList<>(count);

        for (int i = start; i < start + count; i++) {
            String name = "Role" + i; // Уникальное значение
            String description = "Description" + i;
            roles.add(new Role(name, description));
        }

        return roles;
    }
}
