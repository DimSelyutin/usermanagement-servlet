package by.homework.controller;

import java.io.IOException;
import java.util.List;

import by.homework.entity.Role;
import by.homework.exception.ServiceException;
import by.homework.service.RoleService;
import by.homework.service.impl.RoleServiceImpl;
import by.homework.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/roles/20kk")
public class GetRecord2000 extends HttpServlet {
    private final RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {

            int pageNumber = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("size"));
            List<Role> roles = roleService.getRolesWithIdGreaterThan2000(pageNumber, pageSize);
            long endTime = System.currentTimeMillis();

            log.info("Время выполнения запроса: {}ms", (endTime - startTime));
            JsonUtil.sendJsonResponse(response, roles);
        } catch (ServiceException e) {
            JsonUtil.sendErrorResponse(response, "An error occurred while retrieving roles.");
        }
    }
}
