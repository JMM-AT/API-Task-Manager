package com.TaskManagement.Application.Controller;

import com.TaskManagement.Application.DTO.TaskRequestDto;
import com.TaskManagement.Application.DTO.TaskResponseDto;
import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Enemurate.TaskStatus;
import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CrudTaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/project/{id}/tasks")
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(@PathVariable Integer id, Pageable pageable, @RequestParam(required = false) TaskStatus status, @RequestParam(required = false) TaskPriorite priorite){

        Page<TaskResponseDto> taskList=taskService.getAllTasks(id,status,priorite,pageable)
                .map(TaskResponseDto::fromEntity)
                ;

        return ResponseEntity.ok(taskList);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Integer id){
        Task task=taskService.getTaskById(id);
        return ResponseEntity.ok(TaskResponseDto.fromEntity( task));
    }

    @PostMapping("/project/{id}/task")
    public ResponseEntity<TaskResponseDto> creatTask(@Valid @RequestBody TaskRequestDto dto, @PathVariable Integer id){
        Task t=taskService.creatTask(TaskRequestDto.toEntity(dto),id);
        return new ResponseEntity<>(TaskResponseDto.fromEntity(t), HttpStatus.CREATED);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Integer id,@Valid @RequestBody TaskRequestDto dto){
        Task t=taskService.updateTask(id,TaskRequestDto.toEntity(dto));
        return ResponseEntity.ok(TaskResponseDto.fromEntity(t));
    }

    
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}
