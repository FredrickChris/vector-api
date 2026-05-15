package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.service.TaskService;

@RestController
public class TaskController {
	private final TaskService service;

	public TaskController(TaskService service) {
	    this.service = service;
	}

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
    
    @GetMapping("/tasks")
    public List<Task> getTasks() {
    	service.readTasks();
        return service.getTaskList();
    }
}