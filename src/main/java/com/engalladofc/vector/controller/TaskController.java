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
import java.time.LocalDate;

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
        return new ApiResponse<>(true, "Tasks fetched", service.getTaskList());
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
        return new ApiResponse<>(true, "Task created", null);
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
        return new ApiResponse<>(true, "Task updated", null);
    }


    //===============================//
    //          DELETE TASK          //
    //===============================//
    @DeleteMapping("/tasks/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        service.saveTasks();
        return new ApiResponse<>(true, "Task deleted", null);
    }


    //===============================//
    //          SAVE & READ          //
    //===============================//
    @PostMapping("/tasks/save")
    public ApiResponse<Void> saveTasks() {
        service.saveTasks();
        return new ApiResponse<>(true, "Tasks saved", null);
    }

    @PostMapping("/tasks/read")
    public ApiResponse<Void> readTasks() {
        service.readTasks();
        return new ApiResponse<>(
        		true, 
        		"Tasks loaded", 
        		null
        	);
    }


    //===============================//
    //           FILTERING           //
    //===============================//
    @GetMapping("/tasks/filter/date")
    public ApiResponse<List<Task>> filterByDate(@RequestParam(required = false) String date) {
        return new ApiResponse<>(
        		true, 
        		"Filtered by date",
                analysis.filterByDate(service.getTaskList(), date != null ? LocalDate.parse(date) : null)
        	);
    }

    @GetMapping("/tasks/filter/days")
    public ApiResponse<List<Task>> filterByDays(@RequestParam Integer min, @RequestParam  Integer max) {
        return new ApiResponse<>(
        		true, 
        		"Filtered by days",
                analysis.filterByDays(service.getTaskList(), min, max)
        	);
    }

    @GetMapping("/tasks/filter/difficulty")
    public ApiResponse<List<Task>> filterByDifficulty(@RequestParam Integer min, @RequestParam Integer max) {
        return new ApiResponse<>(
        		true, 
        		"Filtered by difficulty",
                analysis.filterByDifficulty(service.getTaskList(), min, max)
        	);
    }

    @GetMapping("/tasks/filter/status")
    public ApiResponse<List<Task>> filterByStatus(@RequestParam Status status) {
        return new ApiResponse<>(
        		true, 
        		"Filtered by status",
                analysis.filterByStatus(service.getTaskList(), status)
        	);
    }


    //===============================//
    //            SORTING            //
    //===============================//
    @GetMapping("/tasks/sort/deadline")
    public ApiResponse<List<Task>> sortByDeadline() {
        return new ApiResponse<>(
        		true, 
        		"Sorted by deadline",
                analysis.sortByDeadline(service.getTaskList())
        	);
    }

    @GetMapping("/tasks/sort/priority")
    public ApiResponse<List<Task>> sortByPriority() {
        return new ApiResponse<>(
        		true, 
        		"Sorted by priority",
                analysis.sortByPriority(service.getTaskList())
        	);
    }


    //===============================//
    //          VALIDATION           //
    //===============================//
    @GetMapping("/validate/title")
    public ApiResponse<Error> validateTitle(@RequestParam String title) {
        Error error = service.validateTitle(title);
        return error == null
                ? new ApiResponse<>(true, "Title is valid", null)
                : new ApiResponse<>(false, error.getMessage(), error);
    }

    @GetMapping("/validate/subject")
    public ApiResponse<Error> validateSubject(@RequestParam String subject) {
        Error error = service.validateSubject(subject);
        return error == null
                ? new ApiResponse<>(true, "Subject is valid", null)
                : new ApiResponse<>(false, error.getMessage(), error);
    }

    @GetMapping("/validate/description")
    public ApiResponse<Error> validateDescription(@RequestParam String description) {
        Error error = service.validateDescription(description);
        return error == null
                ? new ApiResponse<>(true, "Description is valid", null)
                : new ApiResponse<>(false, error.getMessage(), error);
    }

    @GetMapping("/validate/difficulty")
    public ApiResponse<Error> validateDifficulty(@RequestParam Integer difficulty) {
        Error error = service.validateDifficulty(difficulty);
        return error == null
                ? new ApiResponse<>(true, "Difficulty is valid", null)
                : new ApiResponse<>(false, error.getMessage(), error);
    }
}