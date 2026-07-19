package com.TaskManagement.Application.Controller;

import com.TaskManagement.Application.Model.Users;
import com.TaskManagement.Application.Repository.UserRepo;
import com.TaskManagement.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserService userServ;

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Users>> getAll(){
        List<Users> usersList=userServ.findAll();

        if (usersList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        return ResponseEntity.ok(usersList);
    }
}
