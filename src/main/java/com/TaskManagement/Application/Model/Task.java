package com.TaskManagement.Application.Model;

import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Enemurate.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100)
    private String title;
    @Size(max=500)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status=TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    private TaskPriorite priorite=TaskPriorite.LOW;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
