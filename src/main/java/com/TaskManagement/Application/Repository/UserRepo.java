package com.TaskManagement.Application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.TaskManagement.Application.Model.Users;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {


    Optional<Users > findByUsername(String username);
}
