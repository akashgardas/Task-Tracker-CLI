package com.example.tasktrackercli.repository;

import com.example.tasktrackercli.model.Task;
import com.example.tasktrackercli.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(long id);

    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);

    void deleteById(long id);

}
