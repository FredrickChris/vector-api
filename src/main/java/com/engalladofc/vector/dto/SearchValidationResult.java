package com.engalladofc.vector.dto;

import java.util.List;
import java.time.LocalDate;

public class SearchValidationResult {
	private List<String> errors;
	private LocalDate minDate;
	private LocalDate maxDate;
	private Integer minDiff;
	private Integer maxDiff;
	
	public SearchValidationResult(List<String> errors, LocalDate minDate, LocalDate maxDate, Integer minDiff, Integer maxDiff) {
        this.errors = errors;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.minDiff = minDiff;
        this.maxDiff = maxDiff;
	}
	
	public List<String> getErrors() { return errors; }
	public LocalDate getMinDate() { return minDate; }
	public LocalDate getMaxDate() { return maxDate; }
	public Integer getMinDiff() { return minDiff; }
	public Integer getMaxDiff() { return maxDiff; }
}
