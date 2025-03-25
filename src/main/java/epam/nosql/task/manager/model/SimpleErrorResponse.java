package epam.nosql.task.manager.model;

public record SimpleErrorResponse(
        String errorMessage,
        String errorCode
) {}
