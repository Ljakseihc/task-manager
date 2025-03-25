package epam.nosql.task.manager.repository;

import epam.nosql.task.manager.model.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByCategory(String category);

    List<Task> findByDeadlineBefore(LocalDate date);

    List<Task> findByDescriptionContainingIgnoreCase(String description);

    List<Task> findBySubtasks_NameContainingIgnoreCase(String name);
}