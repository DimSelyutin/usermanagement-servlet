package by.homework.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super();
    }

    public ServiceException(String string, Exception e) {
        super(string, e);
    }
}
