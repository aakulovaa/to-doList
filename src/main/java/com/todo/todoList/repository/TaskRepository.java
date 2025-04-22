package com.todo.todoList.repository;

import com.todo.todoList.models.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findWithSortingByUserId(Long userId, Sort sort);
}