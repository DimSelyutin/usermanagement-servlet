package by.homework.controller;

import java.io.IOException;

import by.homework.dto.request.RequestUserRole;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import by.homework.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/role")
public class AssignRoleToUser extends HttpServlet {
    private final UserService userService = UserServiceImple.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestUserRole requestUserRole = JsonUtil.fromJson(request, RequestUserRole.class);

        try {
            userService.assignRoleToUser(requestUserRole.getUserId(), requestUserRole.getRoleId());
            response.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.sendJsonResponse(response, "Role assigned successfully.");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendErrorResponse(response, e.getMessage());
        }
    }

}
