package epam.nosql.task.manager.service;

import epam.nosql.task.manager.model.Subtask;
import epam.nosql.task.manager.model.dto.TaskDto;
import epam.nosql.task.manager.model.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDto taskDto);
    Task updateTask(String id, TaskDto taskDto);
    void deleteTask(String id);
    List<Task> getAllTasks();
    Task getTaskById(String id);
    List<Task> getTasksByCategory(String category);
    List<Task> getOverdueTasks();
    List<Task> searchTasksByDescription(String description);
    List<Subtask> searchSubtasksByName(String subtaskName);

    Task createSubtasksInTask(String id, List<Subtask> subtasks);

    void deleteSubtasksInTask(String id);
}
