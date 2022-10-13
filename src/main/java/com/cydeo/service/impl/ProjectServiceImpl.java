package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO save(ProjectDTO project) {

        if(project.getProjectStatus() == null){
            project.setProjectStatus(Status.OPEN);
        }

        return super.save(project.getProjectCode(), project);
    }

    @Override
    public ProjectDTO findById(String username) {
        return super.findById(username);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(ProjectDTO object) {

        if(object.getProjectStatus() == null){
            object.setProjectStatus(findById(object.getProjectCode()).getProjectStatus());
        }


        super.update(object.getProjectCode(), object);
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);
    }

    @Override
    public void complete(ProjectDTO project) {

        project.setProjectStatus(Status.COMPLETE);
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        List<ProjectDTO> projectList =
                findAll()
                        .stream()
                .filter(project-> project.getAssignedManager().equals(manager))
                        .map(


                                project -> {

                                    List<TaskDTO> taskList = taskService.findTasksByManagers(manager);

                                    int completeTaskCount = (int) taskList.stream().filter(t -> t.getProject().equals(project) && t.getTaskStatus() == (Status.COMPLETE)).count();
                                    int unfinishedTaskCount = (int) taskList.stream().filter(t->t.getProject().equals(project) && t.getTaskStatus() != Status.COMPLETE).count();

                                    project.setCompleteTaskCounts(completeTaskCount);
                                    project.setCompleteTaskCounts(unfinishedTaskCount);

                                    return  project;
                                }
                        )
                        .collect(Collectors.toList());
        return null;
    }
}
