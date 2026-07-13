package com.TaskManagement.Application.Controller;

import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CrudTaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/project/{id}/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Integer id){
        return ResponseEntity.ok(taskService.getAllTasks(id));
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id){
        return ResponseEntity.ok( taskService.getTaskById(id));
    }

    @PostMapping("/project/{id}/task")
    public ResponseEntity<Task> creatTask(@Valid @RequestBody Task task,@PathVariable Integer id){
        return new ResponseEntity<>(taskService.creatTask(task,id), HttpStatus.CREATED);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id,@Valid @RequestBody Task task){
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}
