package com.TaskManagement.Application.DTO;

import com.TaskManagement.Application.Model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private Integer id;
    private String title;
    private String description;
    private String priorite;
    private String status;
    private LocalDateTime createdAt;
    private String ownerUsername;
    private Integer projectId;

    public static TaskResponseDto fromEntity(Task task) {

        TaskResponseDto dto = new TaskResponseDto();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriorite(task.getPriorite().name());
        dto.setStatus(task.getStatus().name());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setProjectId(task.getProjet().getId());

        if (task.getOwner() != null) {
            dto.setOwnerUsername(task.getOwner().getUsername());
        }
        return dto;
    }
}
