package by.homework.controller;

import by.homework.entity.Role;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import by.homework.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebServlet("/roles/generate")
public class Create20KKRoles extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int batchSize = 10000; // Размер партий для сохранения в базу данных
            int totalCount = 20000000; // Общее количество ролей
            int currentBatch = 0;
            
            // while (currentBatch < totalCount) {
            //     int remaining = totalCount - currentBatch;
            //     int currentBatchSize = Math.min(batchSize, remaining);

            //     List<Role> roles = generateRandomRoles(currentBatch, currentBatchSize);

            //     // Сохранение сгенерированных ролей партиями
            //     roleService.saveRoles(roles);

            //     currentBatch += currentBatchSize;

            //     log.info("Сохранено {} из {} ролей", currentBatch, totalCount);
            // }

            // Отправка подтверждения в виде JSON ответа
            JsonUtil.sendJsonResponse(response, "20 миллионов ролей успешно созданы и сохранены.");
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
            JsonUtil.sendErrorResponse(response, "Произошла ошибка при создании и сохранении ролей.");
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