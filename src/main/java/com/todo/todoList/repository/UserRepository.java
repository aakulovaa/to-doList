package com.todo.todoList.repository;

import com.todo.todoList.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}