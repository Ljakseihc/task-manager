package epam.nosql.task.manager.model.entity;

import epam.nosql.task.manager.model.Subtask;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private LocalDate dateOfCreation;
    private LocalDate deadline;
    private String name;
    private String description;
    private List<Subtask> subtasks;
    private String category;

    public Task(LocalDate dateOfCreation, LocalDate deadline, String name, String description, String category, List<Subtask> subtasks) {
        this.dateOfCreation = dateOfCreation;
        this.deadline = deadline;
        this.name = name;
        this.description = description;
        this.category = category;
        this.subtasks = subtasks;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;

        return Objects.equals(id, task.id) && Objects.equals(dateOfCreation, task.dateOfCreation) && Objects.equals(deadline, task.deadline) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(subtasks, task.subtasks) && Objects.equals(category, task.category);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(dateOfCreation);
        result = 31 * result + Objects.hashCode(deadline);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(subtasks);
        result = 31 * result + Objects.hashCode(category);
        return result;
    }
}
