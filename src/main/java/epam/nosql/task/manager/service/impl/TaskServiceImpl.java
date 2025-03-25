package epam.nosql.task.manager.service.impl;

import epam.nosql.task.manager.model.Subtask;
import epam.nosql.task.manager.model.dto.TaskDto;
import epam.nosql.task.manager.model.entity.Task;
import epam.nosql.task.manager.model.exception.NotFoundDocumentException;
import epam.nosql.task.manager.repository.TaskRepository;
import epam.nosql.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                parseDate(taskDto.deadline()),
                taskDto.name(),
                taskDto.description(),
                taskDto.category(),
                taskDto.subtask()
        ));
    }

    @Override
    public Task updateTask(String id, TaskDto taskDto) {
        var currentTask = getTaskById(id);
        if(Objects.nonNull(taskDto.subtask())) currentTask.setSubtasks(taskDto.subtask());
        if(Objects.nonNull(taskDto.category())) currentTask.setCategory(taskDto.category());
        if(Objects.nonNull(taskDto.deadline())) currentTask.setDeadline(parseDate(taskDto.deadline()));
        if(Objects.nonNull(taskDto.name())) currentTask.setName(taskDto.name());
        return taskRepository.save(currentTask);
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
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundDocumentException("Task by id is not exists"));
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

    @Override
    public Task createSubtasksInTask(String id, List<Subtask> subtasks) {
        var currentTask = getTaskById(id);
        if(Objects.nonNull(subtasks)) currentTask.setSubtasks(subtasks);
        return taskRepository.save(currentTask);
    }

    @Override
    public void deleteSubtasksInTask(String id) {
        var currentTask = getTaskById(id);
        currentTask.setSubtasks(null);
        taskRepository.save(currentTask);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
