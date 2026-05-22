package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.*;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.model.SortField;
import com.engalladofc.vector.model.SortOrder;
import com.engalladofc.vector.dto.ApiResponse;
import com.engalladofc.vector.dto.TaskRequest;
import com.engalladofc.vector.service.TaskService;

import java.util.List;

@RestController
public class TaskController {

    //===============================//
    //         INITIALIZATION        //
    //===============================//
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    //===============================//
    //           TASK LIST           //
    //===============================//
    @GetMapping("/tasks")
    public ApiResponse<List<Task>> getTasks() {
        return service.handleGetTasks();
    }


    //===============================//
    //          CREATE TASK          //
    //===============================//
    @PostMapping("/tasks")
    public ApiResponse<Void> createTask(@RequestBody TaskRequest body) {
        return service.handleCreateTask(body);
    }


    //===============================//
    //           EDIT TASK           //
    //===============================//
    @PutMapping("/tasks/{id}")
    public ApiResponse<Void> editTask(@PathVariable int id, @RequestBody TaskRequest body) {
        return service.handleEditTask(id, body);
    }


    //===============================//
    //          DELETE TASK          //
    //===============================//
    @DeleteMapping("/tasks/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable int id) {
        return service.handleDeleteTask(id);
    }


    //===============================//
    //          SAVE & READ          //
    //===============================//
    @PostMapping("/tasks/save")
    public ApiResponse<Void> saveTasks() {
        return service.handleSaveTasks();
    }

    @PostMapping("/tasks/read")
    public ApiResponse<Void> readTasks() {
        return service.handleReadTasks();
    }


    //===============================//
    //           SEARCHING           //
    //===============================//
    @GetMapping("/tasks/search")
    public ApiResponse<List<Task>> searchTasks(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String stringMinDate,
            @RequestParam(required = false) String stringMaxDate,
            @RequestParam(required = false) Integer minDiff,
            @RequestParam(required = false) Integer maxDiff,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "PRIORITY") SortField field,
            @RequestParam(defaultValue = "ASCENDING") SortOrder order
    	)
    {
        return service.handleSearch(
        		subject, 
        		stringMinDate, 
        		stringMaxDate, 
        		minDiff, 
        		maxDiff,
        		status, 
        		field, 
        		order
        	);
    }
}