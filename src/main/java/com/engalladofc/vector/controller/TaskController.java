package com.engalladofc.vector.controller;

import org.springframework.web.bind.annotation.*;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Error;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.service.TaskService;
import com.engalladofc.vector.service.AnalysisService;

import java.util.List;
import java.time.LocalDate;

@RestController
public class TaskController {

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
    public List<Task> getTasks() {
        service.readTasks();
        return service.getTaskList();
    }


    //===============================//
    //          CREATE TASK          //
    //===============================//
    @PostMapping("/tasks")
    public void createTask(
            @RequestParam String title,
            @RequestParam String subject,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String deadline,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam Status status) {
        service.createTask(title, subject, description,
                deadline != null ? LocalDate.parse(deadline) : null,
                difficulty, status);
        service.saveTasks();
    }


    //===============================//
    //           EDIT TASK           //
    //===============================//
    @PutMapping("/tasks/{id}")
    public void editTask(
            @PathVariable int id,
            @RequestParam String title,
            @RequestParam String subject,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String deadline,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam Status status) {
        service.editTask(id, title, subject, description,
                deadline != null ? LocalDate.parse(deadline) : null,
                difficulty, status);
        service.saveTasks();
    }


    //===============================//
    //          DELETE TASK          //
    //===============================//
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        service.saveTasks();
    }


    //===============================//
    //          SAVE & READ          //
    //===============================//
    @PostMapping("/tasks/save")
    public void saveTasks() {
        service.saveTasks();
    }

    @PostMapping("/tasks/read")
    public void readTasks() {
        service.readTasks();
    }


    //===============================//
    //           FILTERING           //
    //===============================//
    @GetMapping("/tasks/filter/date")
    public List<Task> filterByDate(@RequestParam(required = false) String date) {
        return analysis.filterByDate(service.getTaskList(),
                date != null ? LocalDate.parse(date) : null);
    }

    @GetMapping("/tasks/filter/days")
    public List<Task> filterByDays(@RequestParam Integer days) {
        return analysis.filterByDays(service.getTaskList(), days);
    }

    @GetMapping("/tasks/filter/difficulty")
    public List<Task> filterByDifficulty(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return analysis.filterByDifficulty(service.getTaskList(), min, max);
    }

    @GetMapping("/tasks/filter/status")
    public List<Task> filterByStatus(@RequestParam Status status) {
        return analysis.filterByStatus(service.getTaskList(), status);
    }


    //===============================//
    //            SORTING            //
    //===============================//
    @GetMapping("/tasks/sort/deadline")
    public List<Task> sortByDeadline() {
        return analysis.sortByDeadline(service.getTaskList());
    }

    @GetMapping("/tasks/sort/priority")
    public List<Task> sortByPriority() {
        return analysis.sortByPriority(service.getTaskList());
    }


    //===============================//
    //          VALIDATION           //
    //===============================//
    @GetMapping("/validate/title")
    public Error validateTitle(@RequestParam String title) {
        return service.validateTitle(title);
    }

    @GetMapping("/validate/subject")
    public Error validateSubject(@RequestParam String subject) {
        return service.validateSubject(subject);
    }

    @GetMapping("/validate/description")
    public Error validateDescription(@RequestParam String description) {
        return service.validateDescription(description);
    }

    @GetMapping("/validate/difficulty")
    public Error validateDifficulty(@RequestParam Integer difficulty) {
        return service.validateDifficulty(difficulty);
    }
}