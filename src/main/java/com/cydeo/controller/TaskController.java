package com.cydeo.controller;


import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;


    public TaskController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }


    @GetMapping("/create")
    public String createTask(Model model){
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("taskList", taskService.findAll());
        return "task/create";
    }



    @PostMapping("/create")
    public String saveTask(TaskDTO task){
        taskService.save(task);
        return "redirect:/task/create";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){

        taskService.deleteById(id);
        return "redirect:/task/create";
    }


    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable("taskId") TaskDTO task, Model model){


        model.addAttribute("task", taskService.findById(task.getId()));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("taskList", taskService.findAll());

        return "/task/update";
    }


//    @PostMapping("/update/{id}")
//    public String updateTask(TaskDTO task){
//
//        taskService.update(task);
//        return "redirect:/task/create";
//    }


    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task){
        taskService.update(task);
        return "redirect:/task/create";
    }


    @GetMapping("employee/pending-tasks")
    public String employeePendingTask(Model model){
        model.addAttribute("tasks", taskService.findAllTasksIfStatusIsNotComplete(Status.COMPLETE));

        return "/task/pending-tasks";

    }


    @GetMapping("/employee/archive")
    public String employeeArchivedTask(Model model){

        model.addAttribute("tasks", taskService.findAllTasksByStatus(Status.COMPLETE));

        return "/task/archive";
    }


    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable Long id, Model model){

        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("tasks", taskService.findAllTasksIfStatusIsNotComplete(Status.COMPLETE));


        return "/task/status-update";
    }



}
