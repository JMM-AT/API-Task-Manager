package com.TaskManagement.Application.Controller;

import com.TaskManagement.Application.Model.Projet;
import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Service.ProjectService;
import com.TaskManagement.Application.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    public ResponseEntity<List<Projet>> getAllProjects(){
        return ResponseEntity.ok(projectService.getAllProject());
    }
    @GetMapping("/project/{id}")
    public ResponseEntity<Projet> getProjectById(@PathVariable Integer id){
        return ResponseEntity.ok( projectService.getProjectById(id));
    }



    @PostMapping("/project")
    public ResponseEntity<Projet> creatProject(@Valid @RequestBody Projet projet){
        return new ResponseEntity<>(projectService.creatProject(projet), HttpStatus.CREATED);
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<Projet> updateProject(@PathVariable Integer id,@Valid @RequestBody Projet projet){
        return ResponseEntity.ok(projectService.updateProject(id,projet));
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer id){
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
}
