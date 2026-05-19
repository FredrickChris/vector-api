package com.engalladofc.vector.dto;

import java.util.ArrayList;
import java.time.LocalDate;

public class ValidationResponse {
	private ArrayList<String> errors;
	private String subject;
	private LocalDate minDate;
	private LocalDate maxDate;
	private Integer minDiff;
	private Integer maxDiff;
	
	public ValidationResponse(ArrayList<String> errors, String subject, LocalDate minDate, LocalDate maxDate, Integer minDiff, Integer maxDiff) {
        this.errors = errors;
        this.subject = subject;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.minDiff = minDiff;
        this.maxDiff = maxDiff;
	}
	
	public ArrayList<String> getErrors() { return errors; }
	public String getSubject() { return subject; }
	public LocalDate getMinDate() { return minDate; }
	public LocalDate getMaxDate() { return maxDate; }
	public Integer getMinDiff() { return minDiff; }
	public Integer getMaxDiff() { return maxDiff; }
}
