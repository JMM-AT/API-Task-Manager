package com.TaskManagement.Application.Service;

import com.TaskManagement.Application.Exception.TaskNotFoundException;
import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task getTaskById(Integer id) {

        return taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task creatTask(Task task) {
        return taskRepo.save(task);
    }

    public Task updateTask( Integer id, Task task) {
        Task updatedTask=taskRepo.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id))
                ;
        updatedTask.setTitle(task.getTitle());
        updatedTask.setStatus(task.getStatus());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setPriorite(task.getPriorite());
        return taskRepo.save(updatedTask);

    }

    public String deleteTask(Integer id) {
        if (!taskRepo.existsById(id))
            throw new TaskNotFoundException(id);

        taskRepo.deleteById(id);

        return "Task deleted successfully";
    }

}
