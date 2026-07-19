package com.TaskManagement.Application.Service;

import com.TaskManagement.Application.Model.Users;
import com.TaskManagement.Application.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public List<Users> findAll() {
        return userRepo.findAll();
    }
}
