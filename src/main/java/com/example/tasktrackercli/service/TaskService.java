package com.example.tasktrackercli.service;

import com.example.tasktrackercli.model.Task;
import com.example.tasktrackercli.model.TaskStatus;
import com.example.tasktrackercli.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String description) {
        Task task = new Task(0, description);
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public List<Task> getTaskByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task updateTask(long id, String newDescription) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        task.setDescription(newDescription);
        return taskRepository.save(task);
    }

    public void deleteTask(long id) {
        if (taskRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("Task not found: " + id);
        taskRepository.deleteById(id);
    }

    public Task markInProgress(long id) {
        return updateStatus(id, TaskStatus.IN_PROGRESS);
    }

    public Task markDone(long id) {
        return updateStatus(id, TaskStatus.DONE);
    }

    public Task updateStatus(long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        task.setStatus(status);
        return taskRepository.save(task);
    }

}
