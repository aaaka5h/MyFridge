package model.project_w2e9j.src.persistence;

/**
 * Represents the exception that can occur when
 * printing the event log.
 */
public class LogException extends Exception {
    public LogException() {
        super("Error printing log");
    }

    public LogException(String msg) {
        super(msg);
    }
}
