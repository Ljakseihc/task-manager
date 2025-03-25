package epam.nosql.task.manager.model.exception;

public class NotFoundDocumentException extends RuntimeException {
    public NotFoundDocumentException(String message) {
        super(message);
    }
}
