package by.homework.controller;

import by.homework.dto.request.RequestRole;
import by.homework.dto.request.RequestUser;
import by.homework.entity.CommonUser;
import by.homework.entity.GuestUser;
import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.exception.ServiceException;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import by.homework.util.JsonUtil;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
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

            // В зависимости от типа пользователя создаем соответствующий объект
            User user;
            if ("guest".equalsIgnoreCase(requestUser.getUserType())) {
                user = new GuestUser();
                user.setFirstname(requestUser.getFirstname());
                user.setEmail(requestUser.getEmail());
                user.setAge(requestUser.getAge());
                ((GuestUser) user).setGuestPass(requestUser.getGuestPass());
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