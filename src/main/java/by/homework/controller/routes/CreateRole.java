package by.homework.controller.routes;

import by.homework.controller.Command;
import by.homework.entity.Role;
import by.homework.exception.ServiceException;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CreateRole implements Command {
    private final RoleService roleService = RoleServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name == null || description == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please provide all required parameters: name and description.");
            return;
        }

        Role newRole = new Role(name, description);

        try {
            roleService.insertRole(newRole);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(newRole));
            out.flush();
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while creating the role.");
        }
    }
}
