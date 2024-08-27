package by.homework.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import by.homework.entity.Role;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import by.homework.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/role")
public class UpdateRoleServlet extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long roleId = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name == null || description == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Please provide the name and description parameters.");
            return;
        }

        Role updatedRole = new Role(roleId, name, description, null, null);

        try {
            roleService.updateRole(updatedRole);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(objectMapper.writeValueAsString(updatedRole));
            out.flush();
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while updating the role.");
        }
    }
}