package com.example.tasktrackercli.repository;

import com.example.tasktrackercli.model.Task;
import com.example.tasktrackercli.model.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> taskStore = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        if (task.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            task = new Task(id, task.getDescription());
        }
        taskStore.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(long id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskStore.values());
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return taskStore.values()
                .stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        taskStore.remove(id);
    }
}
