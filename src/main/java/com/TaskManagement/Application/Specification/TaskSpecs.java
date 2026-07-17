package com.TaskManagement.Application.Specification;

import com.TaskManagement.Application.Enemurate.TaskPriorite;
import com.TaskManagement.Application.Enemurate.TaskStatus;
import com.TaskManagement.Application.Model.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecs {

    public static Specification<Task> hasProjetId(Integer projetId){

           //root si on veut dire c'est l'équivalent de la clause FROM dans SQL ici c'est task

                //Query est pour ordering by, grouping by, distinct
                return (root,cq,cb)-> cb.equal(root.get("projet").get("id"),projetId);
    }
    public static Specification<Task> hasStatus(TaskStatus status){
        return (root,cq,cb)->status==null?null:cb.equal(root.get("status"),status);
    }
    public static Specification<Task> hasPriorite(TaskPriorite priorite){
        return (root,cq,cb)->priorite==null?null:cb.equal(root.get("priorite"),priorite);
    }
    public static Specification<Task> containsName(String name){
        return (root,cq,cb)->cb.like(cb.lower(root.get("name")),  "%" + name.toLowerCase() + "%");// % represent zero,one , or Multiple caracters. // _ represent one caracter
    }
}
