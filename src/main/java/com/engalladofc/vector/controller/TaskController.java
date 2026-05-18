package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.*;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Error;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.model.SortField;
import com.engalladofc.vector.model.SortOrder;
import com.engalladofc.vector.dto.ApiResponse;
import com.engalladofc.vector.dto.TaskRequest;
import com.engalladofc.vector.service.TaskService;
import com.engalladofc.vector.service.AnalysisService;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
public class TaskController {
	
    //===============================//
    //         INITIALIZATION        //
    //===============================//
    private final TaskService service;
    private final AnalysisService analysis;

    public TaskController(TaskService service, AnalysisService analysis) {
        this.service = service;
        this.analysis = analysis;
    }


    //===============================//
    //           TASK LIST           //
    //===============================//
    @GetMapping("/tasks")
    public ApiResponse<List<Task>> getTasks() {
        service.readTasks();
        
        return new ApiResponse<>(
        		true, 
        		List.of("Tasks fetched"), 
        		service.getTaskList()
        	);
    }


    //===============================//
    //          CREATE TASK          //
    //===============================//
    @PostMapping("/tasks")
    public ApiResponse<Void> createTask(@RequestBody TaskRequest body) {
        service.createTask(
        		body.title, 
        		body.subject, 
        		body.description,
                body.deadline != null ? LocalDate.parse(body.deadline) : null,
                body.difficulty, 
                body.status
        	);
        
        service.saveTasks();
        
        return new ApiResponse<>(
        		true, 
        		List.of("Task created"), 
        		null
        	);
    }


    //===============================//
    //           EDIT TASK           //
    //===============================//
    @PutMapping("/tasks/{id}")
    public ApiResponse<Void> editTask(@PathVariable int id, @RequestBody TaskRequest body) {
        service.editTask(
        		id, 
        		body.title, 
        		body.subject, 
        		body.description,
                body.deadline != null ? LocalDate.parse(body.deadline) : null,
                body.difficulty, 
                body.status
        	);
        
        service.saveTasks();
        
        return new ApiResponse<>(
        		true, 
        		List.of("Task updated"), 
        		null
        	);
    }


    //===============================//
    //          DELETE TASK          //
    //===============================//
    @DeleteMapping("/tasks/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        service.saveTasks();
        return new ApiResponse<>(
        		true, 
        		List.of("Task deleted"), 
        		null
        	);
    }


    //===============================//
    //          SAVE & READ          //
    //===============================//
    @PostMapping("/tasks/save")
    public ApiResponse<Void> saveTasks() {
        service.saveTasks();
        return new ApiResponse<>(
        		true, 
        		List.of("Tasks saved"), 
        		null
        	);
    }

    @PostMapping("/tasks/read")
    public ApiResponse<Void> readTasks() {
        service.readTasks();
        return new ApiResponse<>(
        		true, 
        		List.of("Tasks loaded"), 
        		null
        	);
    }


    //===============================//
    //           SEARCHING           //
    //===============================//
    @GetMapping("/tasks/sort")
    public ApiResponse<List<Task>> sortTasks(
            @RequestParam(required = false) String stringMinDate,
            @RequestParam(required = false) String stringMaxDate,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "PRIORITY") SortField field,
            @RequestParam(defaultValue = "ASC") SortOrder order,
            @RequestParam(defaultValue = "1") Integer minDiff,
            @RequestParam(defaultValue = "5") Integer maxDiff
    	) {
    	try {
    		LocalDate minDate = stringMinDate != null ? LocalDate.parse(stringMinDate) : null;
    		LocalDate maxDate = stringMaxDate != null ? LocalDate.parse(stringMaxDate) : null;
	        return new ApiResponse<>(
	        		true, 
	        		List.of("Tasks Searched"), 
	        		analysis.search(
	        				service.getTaskList(), 
	        				minDate, 
	        				maxDate, 
	        				minDiff, 
	        				maxDiff, 
	        				status, 
	        				field, 
	        				order
	        			)
	        	);
    	} catch(DateTimeParseException e) {
    		return new ApiResponse<>(
	        		true, 
	        		List.of("Tasks Searched"), 
	        		null
	        	);
    	}
	}
}