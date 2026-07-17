package com.TaskManagement.Application.Repository;

import com.TaskManagement.Application.Model.Projet;
import com.TaskManagement.Application.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Projet,Integer> {

    List<Projet> findProjetsByOwnerUsername(String username);

}
