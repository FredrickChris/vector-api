package com.engalladofc.vector.dto;

import java.util.List;
import java.time.LocalDate;

public class TaskValidationResult {

    private List<String> errors;
    private String title;
    private String subject;
    private String description;
    private LocalDate deadline;
    private Integer difficulty;

    public TaskValidationResult(List<String> errors, String title, String subject, String description, LocalDate deadline, Integer difficulty) {
        this.errors = errors;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.deadline = deadline;
        this.difficulty = difficulty;
    }

    public List<String> getErrors() { return errors; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public LocalDate getDeadline() { return deadline; }
    public Integer getDifficulty() { return difficulty; }
}