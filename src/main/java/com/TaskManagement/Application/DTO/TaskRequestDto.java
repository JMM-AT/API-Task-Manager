package com.TaskManagement.Application.DTO;

import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    @NotBlank
    private String title;
    @Size(max=500)
    private String description;
    @NotNull
    private TaskPriorite priorite;

    public static Task toEntity(TaskRequestDto dto){
        Task task=new Task();
        task.setDescription(dto.getDescription());
        task.setTitle(dto.getTitle());
        task.setPriorite(dto.getPriorite());
        return task;
    }
}
