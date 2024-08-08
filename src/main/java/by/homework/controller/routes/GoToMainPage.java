package by.homework.controller.routes;

import java.io.IOException;
import java.util.List;

import by.homework.controller.Command;
import by.homework.entity.User;
import by.homework.exception.ServiceException;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoToMainPage implements Command {
    private final UserService USERSERVICE = UserServiceImple.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Получаем список пользователей
            List<User> users = USERSERVICE.selectAllUsers();
            // Преобразуем список пользователей в JSON
            String jsonResponse = convertToJson(users);
            // Устанавливаем тип ответа как JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Записываем JSON ответ в поток
            response.getWriter().write(jsonResponse);
        } catch (ServiceException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while receiving user data.");
        }
    }

    private String convertToJson(List<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

}