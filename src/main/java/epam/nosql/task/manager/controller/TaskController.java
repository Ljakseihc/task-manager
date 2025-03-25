package epam.nosql.task.manager.controller;

import epam.nosql.task.manager.model.Subtask;
import epam.nosql.task.manager.model.dto.TaskDto;
import epam.nosql.task.manager.model.entity.Task;
import epam.nosql.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/overdue")
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @GetMapping("/category/{category}")
    public List<Task> getTasksByCategory(@PathVariable String category) {
        return taskService.getTasksByCategory(category);
    }

    @GetMapping("/search/description")
    public List<Task> searchTasksByDescription(@RequestParam String description) {
        return taskService.searchTasksByDescription(description);
    }

    @GetMapping("/search/subtask")
    public List<Subtask> searchSubtasksByName(@RequestParam String subtaskName) {
        return taskService.searchSubtasksByName(subtaskName);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/subtasks/{id}")
    public Task createSubtasksInTask(@PathVariable String id, @RequestBody List<Subtask> subtasks) {
        return taskService.createSubtasksInTask(id, subtasks);
    }

    @DeleteMapping("/subtasks/{id}")
    public void deleteSubtasksInTask(@PathVariable String id) {
        taskService.deleteSubtasksInTask(id);
    }
}
