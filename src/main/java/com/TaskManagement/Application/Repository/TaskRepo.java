package com.TaskManagement.Application.Repository;

import com.TaskManagement.Application.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {
    List<Task> findByOwnerUsername(String username);
}
