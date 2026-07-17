package com.TaskManagement.Application.Repository;

import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Enemurate.TaskStatus;
import com.TaskManagement.Application.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository                                                     //to support spring data jpa specefication
public interface TaskRepo extends JpaRepository<Task,Integer> , JpaSpecificationExecutor {
    Page<Task> findByOwnerUsername(String username,Pageable pageable);

    Page<Task> findByProjetId(Integer id, Pageable pageable);

}
