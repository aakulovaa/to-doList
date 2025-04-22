package com.todo.todoList.service;

import com.todo.todoList.dto.TaskDTO;
import com.todo.todoList.models.Task;
import com.todo.todoList.models.User;
import com.todo.todoList.repository.TaskRepository;
import com.todo.todoList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long taskId){
        return taskRepository.findById(taskId);
    }

    public List<Task> findTasksByUserId(Long userId){
        return taskRepository.findByUserId(userId);
    }

    public List<Task> findWithSortingTasksByUserId(Long userId){
        Sort sort = Sort.by(
                Sort.Order.asc("title"),
                Sort.Order.desc("status"),
                Sort.Order.asc("priority"),
                Sort.Order.desc("createdAt")
        );
        return taskRepository.findWithSortingByUserId(userId, sort);
    }

    public Task saveTask(TaskDTO taskDTO){
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, TaskDTO taskDTO){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new EntityNotFoundException("Задача не найдена"));

        if (taskDTO.getTitle()!=null){
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription()!=null){
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getPriority()!=null) {
            task.setPriority(taskDTO.getPriority());
        }
        if(taskDTO.getStatus()!=null) {
            task.setStatus(taskDTO.getStatus());
        }
        task.setCreatedAt(LocalDateTime.now());
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        task.setUser(user);

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }
}
