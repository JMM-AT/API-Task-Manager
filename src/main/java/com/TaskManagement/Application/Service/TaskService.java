package com.TaskManagement.Application.Service;

import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Enemurate.TaskStatus;
import com.TaskManagement.Application.Exception.ProjectNotFoundException;
import com.TaskManagement.Application.Exception.TaskNotFoundException;
import com.TaskManagement.Application.Exception.UnauthorizedAccessException;
import com.TaskManagement.Application.Model.Projet;
import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Model.Users;
import com.TaskManagement.Application.Repository.ProjectRepo;
import com.TaskManagement.Application.Repository.TaskRepo;
import com.TaskManagement.Application.Repository.UserRepo;
import com.TaskManagement.Application.Specification.TaskSpecs;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectRepo prRep;
    @Autowired
    private ProjectService prServ;
    @Autowired
    private UserRepo userRepo;

    public Page<Task> getAllTasks(Integer id,  TaskStatus status, TaskPriorite priorite,Pageable pageable) {

        //String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        //return taskRepo.findByOwnerUsername(userName);
        Projet pr=prRep.findById(id).orElseThrow(
                ()->new ProjectNotFoundException(id)
        );
        prServ.verifyOwnership(pr,"Only the project owner can see this project.");
        Specification <Task> spec=Specification.where(TaskSpecs.hasProjetId(id))
                .and(TaskSpecs.hasPriorite(priorite))
                .and(TaskSpecs.hasStatus(status));
        return taskRepo.findAll(spec,pageable);
    }

    public Task getTaskById(Integer id) {

        Task task=taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        verifyTaskOwnership(task);
        return task;
    }

    public Task creatTask(Task task, Integer projectId) {
        Projet pr = prRep.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        prServ.verifyOwnership(pr, "Only the project owner can create a task in this project.");

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException(currentUsername));

        task.setOwner(currentUser);
        task.setProjet(pr);
        return taskRepo.save(task);
    }

    public Task updateTask( Integer id, Task task) {
        Task updatedTask = taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        verifyTaskOwnership(updatedTask);

        updatedTask.setTitle(task.getTitle());
        //updatedTask.setStatus(task.getStatus());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setPriorite(task.getPriorite());
        return taskRepo.save(updatedTask);

    }

    public String deleteTask(Integer id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        verifyTaskOwnership(task);

        taskRepo.deleteById(id);
        return "Task deleted successfully";
    }

    private void verifyTaskOwnership(Task task) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String ownerUsername = task.getOwner().getUsername();

        if (ownerUsername == null || !ownerUsername.equals(currentUsername)) {
            throw new UnauthorizedAccessException("Only the task owner can access this task.");
        }
    }

}
