package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.*;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Error;
import com.engalladofc.vector.model.Status;
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
    //           FILTERING           //
    //===============================//
    @GetMapping("/tasks/filter/date")
    public ApiResponse<List<Task>> filterByDate(@RequestParam(required = false) String date) {
    	try {
	    	LocalDate deadline = date != null ? LocalDate.parse(date) : null;
	    	List<Task> dateFiltered = analysis.filterByDate(service.getTaskList(), deadline);
	        return new ApiResponse<>(
	        		true, 
	        		List.of("Filtered by date"),
	                dateFiltered
	        	);
    	} catch (DateTimeParseException e) {
            return new ApiResponse<>(false, List.of("Invalid Date / Format: YYYY-MM-DD"), new ArrayList<>());
    	}
    }

    
    @GetMapping("/tasks/filter/days")
    public ApiResponse<List<Task>> filterByDays(@RequestParam(required = false) Integer min, @RequestParam(required = false)  Integer max) {

        List<String> messages = new ArrayList<>();
        
        if (min != null && max != null && min > max) {
            messages.add("Minimum cannot be greater than maximum difficulty");
        }

        boolean isValid = messages.isEmpty();
        
        if(!isValid) {
            return new ApiResponse<>(false, messages, new ArrayList<>());
        }
        
        List<Task> daysFiltered = analysis.filterByDays(service.getTaskList(), min, max);
        
        return new ApiResponse<>(
        		true, 
        		List.of("Filtered by days"), 
                daysFiltered
        );
    }

    
    @GetMapping("/tasks/filter/difficulty")
    public ApiResponse<List<Task>> filterByDifficulty(@RequestParam(defaultValue = "1") Integer min, @RequestParam(defaultValue = "5") Integer max) {

        List<String> messages = new ArrayList<>();

        Error minError = service.validateDifficulty(min);
        if (minError != null) {
            messages.add("Minimum difficulty: " + minError.getMessage());
        }

        Error maxError = service.validateDifficulty(max);
        if (maxError != null) {
            messages.add("Maximum difficulty: " + maxError.getMessage());
        }

        if (min != null && max != null && min > max) {
            messages.add("Minimum cannot be greater than maximum difficulty");
        }

        boolean isValid = messages.isEmpty();

        if (!isValid) {
            return new ApiResponse<>(false, messages, new ArrayList<>());
        }

        List<Task> difficultyFiltered = analysis.filterByDifficulty(service.getTaskList(), min, max);

        return new ApiResponse<>(
                true,
                List.of("Filtered by difficulty"),
                difficultyFiltered
        );
    }

    @GetMapping("/tasks/filter/status")
    public ApiResponse<List<Task>> filterByStatus(@RequestParam Status status) {
    	List<Task> statusFiltered = analysis.filterByStatus(service.getTaskList(), status);
        return new ApiResponse<>(
        		true, 
        		List.of("Filtered by status"), 
        		statusFiltered
        	);
    }


    //===============================//
    //            SORTING            //
    //===============================//
    @GetMapping("/tasks/sort/deadline")
    public ApiResponse<List<Task>> sortByDeadline() {
        return new ApiResponse<>(
        		true, 
        		List.of("Sorted by deadline"),
                analysis.sortByDeadline(service.getTaskList())
        	);
    }

    @GetMapping("/tasks/sort/priority")
    public ApiResponse<List<Task>> sortByPriority() {
        return new ApiResponse<>(
        		true, 
        		List.of("Sorted by priority"),
                analysis.sortByPriority(service.getTaskList())
        	);
    }
}