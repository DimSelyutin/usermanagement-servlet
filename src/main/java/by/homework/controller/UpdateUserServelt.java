package by.homework.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.homework.dto.request.RequestUser;
import by.homework.entity.AdminUser;
import by.homework.entity.CommonUser;
import by.homework.entity.GuestUser;
import by.homework.entity.User;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import by.homework.util.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@WebServlet("/user")
public class UpdateUserServelt extends HttpServlet {

   private final UserService userService = UserServiceImple.getInstance();

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      try {
         // Чтение данных из запроса
         RequestUser requestUser = JsonUtil.fromJson(request, RequestUser.class);

         User updatedUser;
         if ("guest".equalsIgnoreCase(requestUser.getUserType())) {
            updatedUser = new GuestUser(
                  requestUser.getFirstname(),
                  requestUser.getEmail(),
                  requestUser.getAge(),
                  requestUser.getGuestPass());
         } else if ("admin".equalsIgnoreCase(requestUser.getUserType())) {
            updatedUser = new AdminUser(
                  requestUser.getFirstname(),
                  requestUser.getEmail(),
                  requestUser.getAge(),
                  requestUser.getAdminLevel());
            log.info("User: ", updatedUser);
         } else {
            updatedUser = new CommonUser(
                  requestUser.getFirstname(),
                  requestUser.getEmail(),
                  requestUser.getAge(),
                  requestUser.getMembershipType());
         }

         // Сохранение нового пользователя
         userService.updateUser(updatedUser);
         // Отправка подтверждения в виде JSON ответа
         JsonUtil.sendJsonResponse(response, updatedUser);
      } catch (Exception e) {
         log.error("Error occurred while creating user: {}", e.getLocalizedMessage(), e);
         JsonUtil.sendErrorResponse(response, e.getLocalizedMessage());
      }
   }
}
