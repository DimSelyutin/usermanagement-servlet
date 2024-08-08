package by.homework.controller.routes;

import by.homework.controller.routes.CreateUser;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateUserTest {
    private CreateUser createUser;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userService = Mockito.spy(UserServiceImple.getInstance());
        createUser = new CreateUser();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testExecute_ValidParameters() throws IOException, ServiceException {
        when(request.getParameter("firstname")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("age")).thenReturn("30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        try {
            createUser.execute(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // verify(userService).insertUser(any(User.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // User createdUser = objectMapper.readValue(stringWriter.toString(),
        // User.class);
        // assertEquals("John", createdUser.getFirstname());
        // assertEquals("john@example.com", createdUser.getEmail());
        // assertEquals(30, createdUser.getAge());
    }

    @Test
    void testExecute_MissingParameters() throws IOException {
        when(request.getParameter("firstname")).thenReturn(null);
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("age")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        try {
            createUser.execute(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        verify(userService, never()).insertUser(any(User.class));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals("Please provide all required parameters: firstname, email, and age.", stringWriter.toString());
    }

    @Test
    void testExecute_InvalidAge() throws IOException {
        when(request.getParameter("firstname")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("age")).thenReturn("invalid");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        try {
            createUser.execute(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        verify(userService, never()).insertUser(any(User.class));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}