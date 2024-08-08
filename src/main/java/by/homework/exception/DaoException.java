package by.homework.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException() {
        super();
    }
}
