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

public class CreateUser implements Command {
    private final UserService userService = UserServiceImple.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String email = request.getParameter("email");
        String ageStr = request.getParameter("age");

        if (firstname == null || email == null || ageStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please provide all required parameters: firstname, email, and age.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Age must be a valid integer.");
            return;
        }

        User newUser = new User(firstname, email, age);

        try {
            userService.insertUser(newUser);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(newUser));
            out.flush();
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while creating the user.");
        }
    }
}
