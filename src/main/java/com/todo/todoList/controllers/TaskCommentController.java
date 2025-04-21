package com.todo.todoList.controllers;

import com.todo.todoList.dto.TaskCommentDTO;
import com.todo.todoList.models.TaskComment;
import com.todo.todoList.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class TaskCommentController {
    private final TaskCommentService taskCommentService;

    @Autowired
    public TaskCommentController(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    @GetMapping
    public ResponseEntity<List<TaskComment>> findAllTaskComments(){
        List<TaskComment> taskComments = taskCommentService.findAllTaskComments();
        return ResponseEntity.ok(taskComments);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskComment>> findTaskCommentsByTaskId(@PathVariable Long taskId){
        List<TaskComment> taskComments = taskCommentService.findTaskCommentsByTaskId(taskId);
        return ResponseEntity.ok(taskComments);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskComment> saveTaskComment(@RequestBody TaskCommentDTO taskCommentDTO){
        TaskComment createdTaskComment = taskCommentService.saveTaskComment(taskCommentDTO);
        return ResponseEntity.ok(createdTaskComment);
    }

    @DeleteMapping("/delete/{taskCommentId}")
    public ResponseEntity<Void> deleteTaskComment(@PathVariable Long taskCommentId){
        taskCommentService.deleteTaskComment(taskCommentId);
        return ResponseEntity.noContent().build();
    }
}
