package by.homework.controller;

import by.homework.dto.request.RequestUser;
import by.homework.entity.AdminUser;
import by.homework.entity.CommonUser;
import by.homework.entity.GuestUser;
import by.homework.entity.User;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import by.homework.util.JsonUtil;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@WebServlet("/users/create")
public class CreateUser extends HttpServlet {
    private final UserService userService = UserServiceImple.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Чтение данных из запроса
            RequestUser requestUser = JsonUtil.fromJson(request, RequestUser.class);

            log.info("Request type: {}", requestUser);
            // В зависимости от типа пользователя создаем соответствующий объект
            User user;
            if ("guest".equalsIgnoreCase(requestUser.getUserType())) {
                user = new GuestUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getGuestPass());
            } else if ("admin".equalsIgnoreCase(requestUser.getUserType())) {
                user = new AdminUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getAdminLevel());
                log.info("User: ", user);
            } else {
                user = new CommonUser(
                        requestUser.getFirstname(),
                        requestUser.getEmail(),
                        requestUser.getAge(),
                        requestUser.getMembershipType());
            }

            // Сохранение нового пользователя
            userService.insertUser(user);

            // Отправка подтверждения в виде JSON ответа
            JsonUtil.sendJsonResponse(response, user);
        } catch (Exception e) {
            log.error("Error occurred while creating user: {}", e.getLocalizedMessage(), e);
            JsonUtil.sendErrorResponse(response, e.getLocalizedMessage());
        }
    }
}