package by.homework.controller;

import by.homework.exception.ServiceException;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
@WebServlet("/roles/delete")
public class DeleteRole extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long roleId = Long.parseLong(request.getParameter("id"));

        try {
            roleService.deleteRole(roleId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while deleting the role.");
        }
    }
}