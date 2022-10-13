package com.cydeo.converter;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.TaskService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoConverter implements Converter<String, TaskDTO> {



    TaskService taskService;

    public TaskDtoConverter(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskDTO convert(String source) {
        return taskService.findById(Long.valueOf(source));
    }
}
