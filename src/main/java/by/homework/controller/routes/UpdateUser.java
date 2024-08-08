package by.homework.controller.routes;

import by.homework.controller.Command;
import by.homework.entity.User;
import by.homework.exception.ServiceException;
import by.homework.service.UserService;

import by.homework.service.impl.UserServiceImple;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class UpdateUser implements Command {
    private final UserService userService = UserServiceImple.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        String firstname = request.getParameter("firstname");
        String email = request.getParameter("email");
        Integer age = Integer.parseInt(request.getParameter("age"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        if (firstname == null || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please provide the username, email, and password parameters.");
            return;
        }

        User updatedUser = new User(userId, firstname, email, roleId);

        try {
            userService.updateUser(updatedUser);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(updatedUser));
            out.flush();
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while updating the user.");
        }
    }
}
