package epam.nosql.task.manager.model.dto;

import epam.nosql.task.manager.model.Category;
import epam.nosql.task.manager.model.Subtask;

import java.util.List;

public record TaskDto(
        String name,
        String description,
        Category category,
        String deadline,
        List<Subtask> subtask
) {
}
