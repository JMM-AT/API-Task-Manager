package com.TaskManagement.Application.Service;

import com.TaskManagement.Application.Exception.TaskNotFoundException;
import com.TaskManagement.Application.Exception.UnauthorizedAccessException;
import com.TaskManagement.Application.Model.Projet;
import com.TaskManagement.Application.Model.Task;
import com.TaskManagement.Application.Model.Users;
import com.TaskManagement.Application.Repository.ProjectRepo;
import com.TaskManagement.Application.Repository.TaskRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.TaskManagement.Application.Repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;
    @Mock
    private ProjectRepo prRep;
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_shouldReturnTask_whenTaskExists() {
        Users owner = new Users();
        owner.setUsername("test1");

        Task fakeTask = new Task();
        fakeTask.setId(1);
        fakeTask.setTitle("Tache de test");
        fakeTask.setOwner(owner);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test1");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(taskRepo.findById(1)).thenReturn(Optional.of(fakeTask));

        Task result = taskService.getTaskById(1);

        assertEquals("Tache de test", result.getTitle());
    }

    @Test
    void getTaskById_shouldThrowException_whenTaskNotFound(){
        when(taskRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,()->{
            taskService.getTaskById(999);
        });
    }

    @Test
    void verifyTaskOwnership_shouldThrowException_whenUserIsNotOwner() {
        Users owner = new Users();
        owner.setUsername("test1");

        Task task = new Task();
        task.setId(1);
        task.setOwner(owner);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test2");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(taskRepo.findById(1)).thenReturn(Optional.of(task));

        assertThrows(UnauthorizedAccessException.class, () -> {
            taskService.getTaskById(1);
        });
    }

    void creatTask_shouldSetOwnerFromSecurityContext(){
        Users user= new Users();
        user.setId(1);
        user.setUsername("test1");

        Projet projet=new Projet();
        projet.setId(1);
        projet.setOwner(user);

        Task task=new Task();
        task.setId(1);
        task.setTitle("Tache de test");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test1");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(prRep.findById(1)).thenReturn(Optional.of(projet));
        when(userRepo.findByUsername("test2")).thenReturn(Optional.of(user));
        when(taskRepo.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.creatTask(task,1);

        assertEquals("test1",result.getOwner());
        assertEquals(projet,result.getProjet());
    }

    @Test
    void deleteTask_shouldThrowException_whenTaskNotFound() {
        when(taskRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(999);
        });
    }
}