package com.engalladofc.vector.service;

import org.springframework.stereotype.Service;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.SortField;
import com.engalladofc.vector.model.SortOrder;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.dto.ApiResponse;
import com.engalladofc.vector.dto.SearchValidationResult;
import com.engalladofc.vector.dto.TaskValidationResult;
import com.engalladofc.vector.dto.TaskRequest;
import com.engalladofc.vector.repository.TaskRepository;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class TaskService {

    private TaskRepository repo = new TaskRepository();
    private AnalysisService analysis = new AnalysisService();


    //===============================//
    //          CREATE TASK          //
    //===============================//
    public ApiResponse<Void> handleCreateTask(TaskRequest body) {
        TaskValidationResult taskResponse = validateTask(body.title, body.subject, body.description, body.deadline, body.difficulty);

        if (!taskResponse.getErrors().isEmpty()) {
            return new ApiResponse<>(false, taskResponse.getErrors(), null);
        }

        repo.addTask(new Task(null,
                taskResponse.getTitle(),
                taskResponse.getSubject(),
                taskResponse.getDescription(),
                taskResponse.getDeadline(),
                taskResponse.getDifficulty(),
                body.status));

        repo.saveTasks();

        return new ApiResponse<>(true, List.of("Task created"), null);
    }
    
    //===============================//
    //            SEARCH             //
    //===============================//
	public ApiResponse<List<Task>> handleSearch(
    		String subject, 
    		String stringMinDate, 
    		String stringMaxDate, 
    		Status status, 
    		SortField field, 
    		SortOrder order, 
    		Integer minDiff, 
    		Integer maxDiff
		) 
	{

		SearchValidationResult searchResponse = validateSearch(stringMinDate, stringMaxDate, minDiff, maxDiff);
		
		List<String> errors = searchResponse.getErrors();
		
	    if (!errors.isEmpty()) {
	        return new ApiResponse<>(false, errors, null);
	    }

	    subject = (subject != null && !subject.isBlank()) ? subject.trim().toLowerCase() : null;

	    return new ApiResponse<>(
	            true,
	            List.of("Tasks searched"),
	            analysis.searchTasks(
	    				getTaskList(), 
	    				subject, 
	    				searchResponse.getMinDate(), 
	    				searchResponse.getMaxDate(), 
	    				searchResponse.getMinDiff(), 
	    				searchResponse.getMaxDiff(),
	    				status,
	    				field, 
	    				order
	    			)
	    );
	}


    //===============================//
    //           EDIT TASK           //
    //===============================//
    public ApiResponse<Void> handleEditTask(int id, TaskRequest body) {
        TaskValidationResult taskResponse = validateTask(body.title, body.subject, body.description, body.deadline, body.difficulty);

        if (!taskResponse.getErrors().isEmpty()) {
            return new ApiResponse<>(false, taskResponse.getErrors(), null);
        }

        repo.editTask(id,
                taskResponse.getTitle(),
                taskResponse.getSubject(),
                taskResponse.getDescription(),
                taskResponse.getDeadline(),
                taskResponse.getDifficulty(),
                body.status);

        repo.saveTasks();

        return new ApiResponse<>(true, List.of("Task updated"), null);
    }


    //===============================//
    //          DELETE TASK          //
    //===============================//
    public ApiResponse<Void> handleDeleteTask(int id) {
        repo.deleteTask(id);
        repo.saveTasks();
        return new ApiResponse<>(true, List.of("Task deleted"), null);
    }


    //===============================//
    //           TASK LIST           //
    //===============================//
    public ApiResponse<List<Task>> handleGetTasks() {
        repo.readTasks();
        return new ApiResponse<>(true, List.of("Tasks fetched"), repo.getAllTasks());
    }


    //===============================//
    //          SAVE & READ          //
    //===============================//
    public ApiResponse<Void> handleSaveTasks() {
        repo.saveTasks();
        return new ApiResponse<>(true, List.of("Tasks saved"), null);
    }

    public ApiResponse<Void> handleReadTasks() {
        repo.readTasks();
        return new ApiResponse<>(true, List.of("Tasks loaded"), null);
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

        return new TaskValidationResult(errors, title, subject, description, deadline, difficulty);
    }


    public SearchValidationResult validateSearch(String stringMinDate, String stringMaxDate, Integer minDiff, Integer maxDiff) {
        List<String> errors = new ArrayList<>();

        LocalDate minDate = validateDeadline(errors, stringMinDate, "Earliest Deadline");
        LocalDate maxDate = validateDeadline(errors, stringMaxDate, "Latest Deadline");
        validateDifficulty(errors, minDiff, "Minimum Difficulty");
        validateDifficulty(errors, maxDiff, "Maximum Difficulty");

        return new SearchValidationResult(errors, minDate, maxDate, minDiff, maxDiff);
    }

    private void validateTitle(List<String> errors, String title) {
        if (title == null || title.isBlank()) {
            errors.add("Title cannot be empty");
        }
    }

    private void validateSubject(List<String> errors, String subject) {
        if (subject == null || subject.isBlank()) {
            errors.add("Subject cannot be empty");
        }
    }

    private void validateDescription(List<String> errors, String description) {
        if (description != null && description.length() > 150) {
            errors.add("Description exceeds 150 characters");
        }
    }

    private LocalDate validateDeadline(List<String> errors, String stringDeadline, String reference) {
        if (stringDeadline == null || stringDeadline.isBlank()) return null;
        try {
            return LocalDate.parse(stringDeadline);
        } catch (DateTimeParseException e) {
            errors.add(reference + ": invalid date / format (YYYY-MM-DD)");
            return null;
        }
    }

    private void validateDifficulty(List<String> errors, Integer difficulty, String reference) {
        if (difficulty == null) return;
        if (difficulty > 5) errors.add(reference + " cannot be greater than 5");
        if (difficulty < 1) errors.add(reference + " cannot be less than 1");
    }


    //===============================//
    //         INTERNAL USE          //
    //===============================//
    public List<Task> getTaskList() {
        return repo.getAllTasks();
    }

    public void deleteTask(int id) {
        repo.deleteTask(id);
    }

    public void readTasks() {
        repo.readTasks();
    }

    public void saveTasks() {
        repo.saveTasks();
    }
}