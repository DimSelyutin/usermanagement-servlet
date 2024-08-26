package by.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.homework.entity.Role;
import by.homework.entity.User;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import by.homework.util.JsonUtil;
import by.homework.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@WebServlet("/users")
public class GetAllUsersServlet extends HttpServlet {
    private final UserService userService = UserServiceImple.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = userService.selectAllUsers();
            log.info("Users: {}", users);
            JsonUtil.sendJsonResponse(response, users);
        } catch (ServiceException e) {
            JsonUtil.sendErrorResponse(response, "An error occurred while retrieving users.");
        }
    }
}