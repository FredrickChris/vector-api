package com.engalladofc.vector.service;

import org.springframework.stereotype.Service;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Error;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.repository.TaskRepository;

import java.util.ArrayList;
import java.time.LocalDate;

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
    //        TASK VALDIATION        //
    //===============================//
    public Error validateTitle(String title) {
        if (title == null || title.isBlank()) {
            return Error.TITLE;
        }
        return null;
    }

    public Error validateSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            return Error.SUBJECT;
        }
        return null;
    }

    public Error validateDescription(String description) {
        if (description.length() > 300) {
            return Error.DESCRIPTION;
        }
        return null;
    }

    public Error validateDeadline(LocalDate deadline) { //NOT USED AS OF THE MOMENT
        return null;
    }

    public Error validateDifficulty(Integer difficulty) {
        if (difficulty == null || difficulty > 5 || difficulty < 1) {
            return Error.DIFFICULTY;
        }
        return null;
    }
    
    
    //===============================//
    //           TASK LIST           //
    //===============================//
    public ArrayList<Task> getTaskList() {
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