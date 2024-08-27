package by.homework.controller;

import by.homework.entity.Role;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import by.homework.util.JsonUtil;
import by.homework.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/roles")
public class GetAllRolesServlet extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Role> roles = roleService.getAllRoles();
            JsonUtil.sendJsonResponse(response, roles);
        } catch (ServiceException e) {
            JsonUtil.sendErrorResponse(response, "An error occurred while retrieving roles.");
        }
    }
}