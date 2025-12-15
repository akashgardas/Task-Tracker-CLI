package com.example.tasktrackercli.repository;

import com.example.tasktrackercli.model.Task;
import com.example.tasktrackercli.model.TaskStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class JsonFileTaskRepository implements TaskRepository {

    private static final String FILE_NAME = "tasks.json";

    private final ObjectMapper objectMapper;
    private final File file = new File(FILE_NAME);
    private final AtomicLong idGenerator = new AtomicLong(1);

    public JsonFileTaskRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        initializeFile();
    }

    @Override
    public Task save(Task task) {
        List<Task> tasks = readTasks();

        if (task.getId() == 0) {
            task = new Task(idGenerator.getAndIncrement(), task.getDescription());
        } else {
            Task finalTask = task;
            tasks.removeIf(t -> t.getId() == finalTask.getId());
        }

        tasks.add(task);
        writeTasks(tasks);
        return task;
    }

    @Override
    public Optional<Task> findById(long id) {
        return readTasks().stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return readTasks();
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return readTasks().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        List<Task> tasks = readTasks();
        tasks.removeIf(t -> t.getId() == id);
        writeTasks(tasks);
    }

    private void initializeFile() {
        try {
            if (!file.exists() || file.length() == 0) {
                writeTasks(new ArrayList<>());
                idGenerator.set(1);
            }
            else {
                List<Task> tasks = readTasks();
                long maxId = tasks.stream()
                        .mapToLong(Task::getId)
                        .max()
                        .orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize tasks file", e);
        }
    }

    private List<Task> readTasks() {
        try {
            if (!file.exists() || file.length() == 0)
                return new ArrayList<>();
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read tasks file", e);
        }
    }

    private void writeTasks(List<Task> tasks) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, tasks);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write tasks file", e);
        }
    }
}
