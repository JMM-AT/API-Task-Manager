package com.TaskManagement.Application.Service;

import com.TaskManagement.Application.Exception.ProjectNotFoundException;
import com.TaskManagement.Application.Exception.UnauthorizedAccessException;
import com.TaskManagement.Application.Model.Projet;
import com.TaskManagement.Application.Repository.ProjectRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    protected void verifyOwnership(Projet projet,String message){
        String ownerUserName=projet.getOwner().getUsername();
        String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();

        if (ownerUserName==null || currentUserName==null || !(ownerUserName.equals(currentUserName)))
            throw new UnauthorizedAccessException(message);

    }

    public Projet getProjectById(Integer id) {

        Projet projet=projectRepo.findById(id)
                .orElseThrow(  ()->new ProjectNotFoundException(id)
                );
        verifyOwnership(projet,"Only the project owner can see this project.");

        return projet;
    }

    public List<Projet> getAllProject() {
        String Username= SecurityContextHolder.getContext().getAuthentication().getName();

        return projectRepo.findProjetsByOwnerUsername(Username);
    }

    public Projet creatProject(@Valid Projet projet) {
        return projectRepo.save(projet);
    }


    public Projet updateProject(Integer id, @Valid Projet projet) {
        Projet pr=projectRepo.findById(id)
                .orElseThrow(
                        ()->new ProjectNotFoundException(id)
                );

        verifyOwnership(pr,"Only the project owner can update this project.");

        pr.setTask(projet.getTask());
        pr.setName(projet.getName());
        pr.setDescription(projet.getDescription());

        return projectRepo.save(pr);
    }

    public String deleteProject(Integer id) {
        Projet pr=projectRepo.findById(id).orElseThrow(
                ()->new ProjectNotFoundException(id)
        );
        verifyOwnership(pr,"Only the project owner can delete this project.");
        projectRepo.deleteById(id);
        return "project deleted successfully";
    }


}
