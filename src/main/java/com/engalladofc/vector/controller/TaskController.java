package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.service.TaskService;
import com.engalladofc.vector.service.AnalysisService;

import java.util.List;

@RestController
public class TaskController {
	
	private final TaskService service;
	private final AnalysisService analysis;

	public TaskController(TaskService service, AnalysisService analysis) {
	    this.service = service;
	    this.analysis = analysis;
	}
    
    @GetMapping("/tasks")
    public List<Task> getTasks() {
    	service.readTasks();
        return service.getTaskList();
    }
}