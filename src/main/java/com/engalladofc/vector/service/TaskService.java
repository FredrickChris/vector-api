package com.engalladofc.vector.service;

import org.springframework.stereotype.Service;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.dto.SearchValidationResult;
import com.engalladofc.vector.dto.TaskValidationResult;
import com.engalladofc.vector.repository.TaskRepository;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class TaskService {

    private TaskRepository repo = new TaskRepository();

    
    //===============================//
    //          CREATE TASK          //
    //===============================//
    public void createTask(String title, String subject, String description, LocalDate deadline, Integer difficulty, Status status) {
    	repo.addTask(new Task(null, title, subject, description, deadline, difficulty, status));
    }
    
    
    //===============================//
    //           EDIT TASK           //
    //===============================//
    public void editTask(int id, String title, String subject, String description, LocalDate deadline, Integer difficulty, Status status) {
        repo.editTask(id, title, subject, description, deadline, difficulty, status);
    }
    
    
    //===============================//
    //        TASK VALIDATION        //
    //===============================//
    
    public TaskValidationResult validateTask(String title, String subject, String description, String stringDeadline, Integer difficulty) {
    	List<String> errors = new ArrayList<>();

    	validateTitle(errors, title);
    	validateSubject(errors, subject);
    	validateDescription(errors, description);
    	LocalDate deadline = validateDeadline(errors, stringDeadline, "Deadline");
    	validateDifficulty(errors, difficulty, "Difficulty");
    	
    	return new TaskValidationResult(
    			errors,
    			title,
    			subject,
    			description,
    			deadline,
    			difficulty
    		);
    }
    
    
    public SearchValidationResult validateSearch(String stringMinDate, String stringMaxDate, Integer minDiff, Integer maxDiff) {
    	List<String> errors = new ArrayList<>();

    	LocalDate minDate = validateDeadline(errors, stringMinDate, "Earliest Deadline");
    	LocalDate maxDate = validateDeadline(errors, stringMaxDate, "Latest Deadline");
    	validateDifficulty(errors, minDiff, "Minimum Difficulty");
    	validateDifficulty(errors, maxDiff, "Maximum Difficulty");
    	
    	return new SearchValidationResult(
    			errors,
    			minDate,
    			maxDate,
    			minDiff,
    			maxDiff
    		);
    }
    
    private void validateTitle(List<String> errors, String title) {
        if (title == null || title.isBlank()) {
        	errors.add("Title cannot be Empty");
        }
    }

    private void validateSubject(List<String> errors, String subject) {
        if (subject == null || subject.isBlank()) {
        	errors.add("Subject cannot be Empty");
        }
    }

    private void validateDescription(List<String> errors, String description) {
        if (description.length() > 150) {
            errors.add("Description exceed 150 characters.");
        }
    }

    private LocalDate validateDeadline(List<String> errors, String stringDeadline, String reference) {
    	try {
    		return LocalDate.parse(stringDeadline);
    	} catch(DateTimeParseException e) {
    		errors.add(" invalid date / format (YYYY-MM-DD)");
    		return null;
    	}
    }

    private void validateDifficulty(List<String> errors, Integer difficulty, String reference) {
    	if (difficulty > 5) {
    		errors.add(reference + " cannot be greater than 5");
    	}
    	
    	if(difficulty < 1) {
    		errors.add(reference + " cannot be less than 1");
    	}
    }
    
    
    //===============================//
    //           TASK LIST           //
    //===============================//
    public List<Task> getTaskList() {
    	return repo.getAllTasks();
    }
    
    
    //===============================//
    //          DELETE TASK          //
    //===============================//
    public void deleteTask(int id) {
    	repo.deleteTask(id);
    }
    
    
    //===============================//
    //           READ TASK           //
    //===============================//
    public void readTasks() {
    	repo.readTasks();
    }
    
    
    //===============================//
    //           SAVE TASK           //
    //===============================//
    public void saveTasks() {
    	repo.saveTasks();
    }
}