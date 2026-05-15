package com.engalladofc.vector.model;

import java.time.LocalDate;

public class Task {

    private Integer id;
    private String title;
    private String subject;
    private String description;
    private LocalDate deadline;
    private Integer difficulty;
    private Status status;

    public Task(Integer id, String title, String subject, String description, LocalDate deadline, Integer difficulty, Status status) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.deadline = deadline;
        this.difficulty = difficulty;
        this.status = status;
    }
    
    
    //===============================//
    //            GETTERS            //
    //===============================//
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Integer getDifficulty() {
        return difficulty;
    }
    
    public Status getStatus() {
    	return status;
    }
    
    
    //===============================//
    //            SETTERS            //
    //===============================//
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
    
    public void setStatus(Status status) {
    	this.status = status;
    }
}
