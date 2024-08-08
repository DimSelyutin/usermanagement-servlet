package by.homework.controller;

import java.util.HashMap;
import java.util.Map;

import by.homework.controller.routes.*;
import by.homework.exception.ServiceException;

public class CommandProvider {

    private Map<CommandName, Command> commandProvider = new HashMap<>();

    public CommandProvider() {
        commandProvider.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commandProvider.put(CommandName.CREATE_USER, new CreateUser());
        commandProvider.put(CommandName.GET_ALL_USERS, new GetAllUsers());
        commandProvider.put(CommandName.UPDATE_USER, new UpdateUser());
        commandProvider.put(CommandName.DELETE_USER, new DeleteUser());
        commandProvider.put(CommandName.CREATE_ROLE, new CreateRole());
        commandProvider.put(CommandName.GET_ALL_ROLES, new GetAllRoles());
        commandProvider.put(CommandName.UPDATE_ROLE, new UpdateRole());
        commandProvider.put(CommandName.DELETE_ROLE, new DeleteRole());
    }

    public Command getCommand(String commandInput) throws ServiceException {
        CommandName commandName;
        Command command = null;
        if (commandInput == null || commandInput.equals("")) {
            commandInput = CommandName.GO_TO_MAIN_PAGE.toString();
        }

        try {
            commandName = CommandName.valueOf(commandInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Page not found!");
        }

        command = commandProvider.get(commandName);
        return command;
    }
}
