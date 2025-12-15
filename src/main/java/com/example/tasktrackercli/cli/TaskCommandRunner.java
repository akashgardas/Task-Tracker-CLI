package com.example.tasktrackercli.cli;

import com.example.tasktrackercli.model.Task;
import com.example.tasktrackercli.model.TaskStatus;
import com.example.tasktrackercli.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskCommandRunner implements CommandLineRunner {

    private final TaskService taskService;

    public TaskCommandRunner(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        try {
            switch (command) {
                case "add" -> handleAdd(args);
                case "list" -> handleList(args);
                case "update" -> handleUpdate(args);
                case "delete" -> handleDelete(args);
                case "mark-in-progress" -> handleMarkInProgress(args);
                case "mark-done" -> handleMarkDone(args);
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleAdd(String[] args) {
        requireArgs(args, 2);
        Task task = taskService.addTask(args[1]);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    private void handleList(String[] args) {
        List<Task> tasks;
        if (args.length == 1) {
            tasks = taskService.getAllTask();
        } else {
            TaskStatus status = parseStatus(args[1]);
            tasks = taskService.getTaskByStatus(status);
        }
        tasks.forEach(this::printTask);
    }

    private void handleUpdate(String[] args) {
        requireArgs(args, 3);
        long id = Long.parseLong(args[1]);
        Task task = taskService.updateTask(id, args[2]);
        System.out.println("Task updated (ID: " + task.getId() + ")");
    }

    private void handleDelete(String[] args) {
        requireArgs(args, 2);
        long id = Long.parseLong(args[1]);
        taskService.deleteTask(id);
        System.out.println("Task deleted (ID: " + id + ")");
    }

    private void handleMarkInProgress(String[] args) {
        requireArgs(args, 2);
        long id = Long.parseLong(args[1]);
        taskService.markDone(id);
        System.out.println("Task marked as IN_PROGRESS (ID: " + id + ")");
    }

    private void handleMarkDone(String[] args) {
        requireArgs(args, 2);
        long id = Long.parseLong(args[1]);
        taskService.markInProgress(id);
        System.out.println("Task marked as DONE (ID: " + id + ")");
    }

    private void requireArgs(String[] args, int count) {
        if (args.length < count) {
            throw new IllegalArgumentException("Insufficient arguments");
        }
    }

    private TaskStatus parseStatus(String value) {
        return switch (value.toLowerCase()) {
            case "todo" -> TaskStatus.TODO;
            case "in-progress" -> TaskStatus.IN_PROGRESS;
            case "done" -> TaskStatus.DONE;
            default -> throw new IllegalArgumentException("Invalid Status: " + value);
        };
    }

    private void printTask(Task task) {
        System.out.printf(
                "[%d] %s | %s | Created: %s |Updated %s%n",
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private void printUsage() {
        System.out.println("""
                Usage:
                    add,<description>
                    update,<id><description>
                    delete,<id>
                    mark-in-progress,<id>
                    mark-done,<id>
                    list
                    list,<todo|in-progress|done>
                """);
    }
}
