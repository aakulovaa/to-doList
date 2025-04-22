package com.todo.todoList.controllers;

import com.todo.todoList.dto.TaskDTO;
import com.todo.todoList.models.Task;
import com.todo.todoList.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAllTasks(){
        List<Task> tasks = taskService.findAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Optional<Task>> findTaskById(@PathVariable Long taskId){
        Optional<Task> task = taskService.findTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findTasksByUserId(@PathVariable Long userId){
        List<Task> tasks = taskService.findTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/sort/user/{userId}")
    public ResponseEntity<List<Task>> findWithSortingTasksByUserId(@PathVariable Long userId){
        List<Task> tasks = taskService.findWithSortingTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> saveTask(@RequestBody TaskDTO taskDTO){
        Task createdTask = taskService.saveTask(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO){
        Task updatedTask  = taskService.updateTask(taskId, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/{task}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

}
