package epam.nosql.task.manager.service.impl;

import epam.nosql.task.manager.model.Subtask;
import epam.nosql.task.manager.model.dto.TaskDto;
import epam.nosql.task.manager.model.entity.Task;
import epam.nosql.task.manager.repository.TaskRepository;
import epam.nosql.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static epam.nosql.task.manager.util.Constants.DATE_TIME_FORMATTER;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        return taskRepository.save(new Task(
                LocalDate.now(),
                LocalDate.parse(taskDto.deadline(), DATE_TIME_FORMATTER),
                taskDto.name(),
                taskDto.description(),
                taskDto.category(),
                taskDto.subtask()
        ));
    }

    @Override
    public Task updateTask(String id, Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getTasksByCategory(String category) {
        return taskRepository.findByCategory(category);
    }

    @Override
    public List<Task> getOverdueTasks() {
        return taskRepository.findByDeadlineBefore(LocalDate.now());
    }

    @Override
    public List<Task> searchTasksByDescription(String description) {
        return taskRepository.findByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public List<Subtask> searchSubtasksByName(String name) {
        List<Task> tasks =  taskRepository.findBySubtasks_NameContainingIgnoreCase(name);
        return tasks.stream()
                .flatMap(task -> task.getSubtasks().stream()) // Get all subtasks as one combined stream
                .filter(subtask -> subtask.name().toLowerCase().contains(name.toLowerCase())) // Match name
                .toList();
    }
}
