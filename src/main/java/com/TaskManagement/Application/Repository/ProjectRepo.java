package com.TaskManagement.Application.Repository;

import com.TaskManagement.Application.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Projet,Integer> {

    List<Projet> findProjetsByOwnerUsername(String username);
}
