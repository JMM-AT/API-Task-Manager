package com.TaskManagement.Application.Exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Integer id){
        super("Project not found with id: " + id);

    }


}
