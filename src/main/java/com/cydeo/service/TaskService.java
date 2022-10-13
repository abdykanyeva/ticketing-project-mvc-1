package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService extends CrudService<TaskDTO, Long>{


    List<TaskDTO> findTasksByManagers(UserDTO manager);

    List<TaskDTO> findAllTasksIfStatusIsNotComplete(Status status);

    List<TaskDTO> findAllTasksByStatus(Status status);
}
