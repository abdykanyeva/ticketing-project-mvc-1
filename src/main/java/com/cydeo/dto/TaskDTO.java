package com.cydeo.dto;


import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {


    private Long id;
    private ProjectDTO project;
    private UserDTO assignedUser;
    private String taskSubject;
    private LocalDate assignedDate;
    private String taskDetail;

    private Status taskStatus;

}
