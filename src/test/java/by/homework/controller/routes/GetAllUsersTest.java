package by.homework.controller.routes;

import by.homework.controller.routes.GetAllUsers;
import by.homework.entity.User;
import by.homework.exception.ServiceException;
import by.homework.service.UserService;
import by.homework.service.impl.UserServiceImple;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetAllUsersTest {
    private GetAllUsers getAllUsers;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userService = Mockito.spy(UserServiceImple.getInstance());
        getAllUsers = new GetAllUsers();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testExecute_SuccessfulRetrieval() throws IOException, ServiceException, ServletException {
        List<User> users = Arrays.asList(
                new User(1L, "John", "john@example.com", 30),
                new User(2L, "Jane", "jane@example.com", 25));
        when(userService.selectAllUsers()).thenReturn(users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        getAllUsers.execute(request, response);

        // verify(userService).selectAllUsers();
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

    }

    @Test
    void testExecute_ServiceException() throws IOException, ServiceException, ServletException {
        when(userService.selectAllUsers()).thenThrow(new ServiceException("Error"));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        getAllUsers.execute(request, response);

        // verify(userService).selectAllUsers();
        verify(response).setStatus(HttpServletResponse.SC_OK);
       
    }
}
