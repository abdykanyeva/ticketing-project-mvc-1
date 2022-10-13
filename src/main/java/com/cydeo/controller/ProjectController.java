package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }


    @GetMapping("/create")
    public String projectCreate(Model model){
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("managers", userService.findManagers());
        model.addAttribute("projectList", projectService.findAll());
        return "/project/create";
    }


    @PostMapping("/create")
    public String saveProject(ProjectDTO project){

        projectService.save(project);

        return "redirect:/project/create";
    }

    @GetMapping("/delete/{project}")
    public String deleteProject(@PathVariable("project") ProjectDTO project){
        projectService.deleteById(project.getProjectCode());

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable String projectCode){
        projectService.complete(projectService.findById(projectCode));

        return "redirect:/project/create";
    }


    @GetMapping("/update/{projectCode}")
    public String updateProject(@PathVariable ("projectCode") String projectCode, Model model){
        model.addAttribute("project", projectService.findById(projectCode));
        model.addAttribute("managers", userService.findManagers());
        model.addAttribute("projectList", projectService.findAll());


        return "/project/create";
    }

    @PostMapping("/update")

    public String projectUpdate(@ModelAttribute("project") ProjectDTO project){

    projectService.update(project);

    return "redirect:/project/create";

    }

    @GetMapping("/manager/project-status")
    public String getProjectByManager(Model model){

        UserDTO manager = userService.findById("john@cydeo.com");

        List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);

        model.addAttribute("projects", projects);

        return "manager/project-status";
    }

}
