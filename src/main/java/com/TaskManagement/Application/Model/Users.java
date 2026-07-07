package com.TaskManagement.Application.Model;

import com.TaskManagement.Application.Enemurate.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
public class Users implements UserDetails {
   @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
   Integer id;
   @NotBlank
   private String username;
   @NotBlank
   private  String password;
   @NotBlank
   @Email
   @Column(unique = true)
   private String email;
   private Role role;
   @CreationTimestamp
   private LocalDateTime createdAt;

   public Users(Integer id, String username, String password, String email, Role role, LocalDateTime createdAt) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.email = email;
      this.role = role;
      this.createdAt = createdAt;
   }

    public Users() {
    }

    public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUsername() {
      return this.username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return UserDetails.super.isAccountNonExpired();
   }

   @Override
   public boolean isAccountNonLocked() {
      return UserDetails.super.isAccountNonLocked();
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return UserDetails.super.isCredentialsNonExpired();
   }

   @Override
   public boolean isEnabled() {
      return UserDetails.super.isEnabled();
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of();
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }
}
