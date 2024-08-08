package by.homework.controller;

import java.io.IOException;

import by.homework.exception.ServiceException;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MainController extends HttpServlet {
    private final String COMMAND = "c";
    private final String EXCEPTION = "exception";

    private final CommandProvider commandProvider = new CommandProvider();

    public MainController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doAction(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doAction(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doAction(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doAction(req, resp);
    }

    private void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = null;
        String commandStr = req.getParameter(COMMAND);
        try {
            command = commandProvider.getCommand(commandStr);
            command.execute(req, resp);
        } catch (ServiceException | ServletException | IOException e) {
            req.setAttribute(EXCEPTION, "Page not found. Pls chek you link!");
        }

    }

}