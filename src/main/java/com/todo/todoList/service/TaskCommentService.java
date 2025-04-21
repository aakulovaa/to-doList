package com.todo.todoList.service;

import com.todo.todoList.dto.TaskCommentDTO;
import com.todo.todoList.models.Task;
import com.todo.todoList.models.TaskComment;
import com.todo.todoList.models.User;
import com.todo.todoList.repository.TaskCommentRepository;
import com.todo.todoList.repository.TaskRepository;
import com.todo.todoList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskCommentService(TaskCommentRepository taskCommentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskCommentRepository = taskCommentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskComment> findAllTaskComments(){
        return taskCommentRepository.findAll();
    }

    public List<TaskComment> findTaskCommentsByTaskId(Long taskId){
        return taskCommentRepository.findByTaskId(taskId);
    }

    public TaskComment saveTaskComment(TaskCommentDTO taskCommentDTO){
        Task task = taskRepository.findById(taskCommentDTO.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Задание не найдено"));
        User user = userRepository.findById(taskCommentDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        TaskComment taskComment = new TaskComment();
        taskComment.setContent(taskCommentDTO.getContent());
        taskComment.setCreatedAt(LocalDateTime.now());
        taskComment.setTask(task);
        taskComment.setUser(user);

        return taskCommentRepository.save(taskComment);
    }

    public void deleteTaskComment(Long taskCommentId){
        taskCommentRepository.deleteById(taskCommentId);
    }
}
