package by.homework.controller;

import by.homework.dto.request.RequestRole;
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

@Slf4j
@WebServlet("/roles/create")
public class CreateRole extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Чтение данных из запроса
            RequestRole requestRole = JsonUtil.fromJson(request, RequestRole.class);

            // Создание новой Role из данных запроса
            Role role = new Role(requestRole.getName(), requestRole.getDescription());

            // Сохранение новой роли
            roleService.saveRole(role);

            // Отправка подтверждения в виде JSON ответа
            JsonUtil.sendJsonResponse(response, role);
        } catch (Exception e) {
            log.info("Error slsl: {}", e.getLocalizedMessage());
            JsonUtil.sendErrorResponse(response, e.getLocalizedMessage());
        }
    }
}