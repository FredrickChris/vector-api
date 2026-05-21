package com.engalladofc.vector.service;

import org.springframework.stereotype.Service;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Status;
import com.engalladofc.vector.model.SortField;
import com.engalladofc.vector.model.SortOrder;
import com.engalladofc.vector.dto.ApiResponse;
import com.engalladofc.vector.dto.SearchValidationResult;


import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;	
import java.time.temporal.ChronoUnit;

@Service
public class AnalysisService {
		
    //===============================//
    //           SEARCHING           //
    //===============================//
	
	public List<Task> searchTasks(List<Task> tasks, String subject, LocalDate minDate, LocalDate maxDate, Integer minDiff, Integer maxDiff, Status status, SortField field, SortOrder order) {
		return sort(
	    		filter(
	    				tasks, 
	   					subject, 
	  					minDate, 
	    				maxDate, 
	    				minDiff, 
	    				maxDiff,
	    				status
	    			), 
	   			field, 
	  			order
	   		);
	}
	
	
		
    //===============================//
    //           FILTERING           //
    //===============================//
	
	private List<Task> filter(List<Task> tasks, String subject, LocalDate minDate, LocalDate maxDate, Integer minDiff, Integer maxDiff, Status status) {
		
		List<Task> filtered = new ArrayList<>(tasks);
		
		filtered = filterBySubject(filtered, subject);
		filtered = filterByDate(filtered, minDate, maxDate);
		filtered = filterByDifficulty(filtered, minDiff, maxDiff);
		filtered = filterByStatus(filtered, status);
		
		return filtered;
	}
	
	
	private List<Task> filterBySubject(List<Task> tasks, String subject) {

		if(subject == null) {
			return new ArrayList<>(tasks);
		}

		List<Task> filtered = new ArrayList<>();
		for(Task task: tasks) {
			if(task.getSubject().equalsIgnoreCase(subject)) {
				filtered.add(task);
			}
		}
		return filtered;
		
	}
	
	private List<Task> filterByDate(List<Task> tasks, LocalDate minDate, LocalDate maxDate) {

		if(minDate == null && maxDate == null) {
			return new ArrayList<>(tasks);
		}
		
		List<Task> filtered = new ArrayList<>();
		
		Integer minDays = minDate != null ? Math.toIntExact(LocalDate.now().until(minDate, ChronoUnit.DAYS)): null;
		Integer maxDays = maxDate != null ? Math.toIntExact(LocalDate.now().until(maxDate, ChronoUnit.DAYS)): null;
		
		for(Task task: tasks) {
			
			Integer deadline = task.getDeadline() != null ? Math.toIntExact(LocalDate.now().until(minDate, ChronoUnit.DAYS)): null;
			
			if ((minDays != null && maxDays != null) && (deadline != null && deadline >= minDays && deadline <= maxDays)) {
				filtered.add(task);
			}
			else if((minDays == null && maxDays != null) && (deadline == null || deadline <= maxDays)) {
				filtered.add(task);
			}
			else if((minDays != null && maxDays == null) && (deadline == null || deadline >= minDays)) {
				filtered.add(task);
			}
		}
		return filtered;
	}
	
	
	private List<Task> filterByDifficulty(List<Task> tasks, Integer minDiff, Integer maxDiff) {
		
		List<Task> filtered = new ArrayList<>();
		
		for(Task task: tasks) {
			
			Integer difficulty = task.getDifficulty();
			
			if ((minDiff != null && maxDiff != null) && (difficulty != null && difficulty >= minDiff && difficulty <= maxDiff)) {
				filtered.add(task);
			}
			else if((minDiff == null && maxDiff != null) && (difficulty == null || difficulty <= maxDiff)) {
				filtered.add(task);
			}
			else if((minDiff != null && maxDiff == null) && (difficulty == null || difficulty >= minDiff)) {
				filtered.add(task);
			}
		}
		return filtered;
	}
	
	
	private List<Task> filterByStatus(List<Task> tasks, Status status) {
		
		if (status == null) {
		    return new ArrayList<>(tasks);
		}
		
		List<Task> filtered = new ArrayList<>();
		for(Task task: tasks) {
			if(task.getStatus() == status) {
				filtered.add(task);
			}
		}
		return filtered;
	}
	
	
	
    //===============================//
    //            SORTING            //
    //===============================//
	private List<Task> sort(List<Task> tasks, SortField field, SortOrder order){

		List<Task> sorted = new ArrayList<>(tasks);
		
		switch(field) {
		case DEADLINE:
			sorted = sortByDeadline(sorted, order);
			break;
			
		case PRIORITY:
			sorted = sortByPriority(sorted, order);
			break;
		}
		
		return sorted;
	}
	
	private List<Task> sortByDeadline(List<Task> tasks, SortOrder order) {
		List<Task> sorted = new ArrayList<>(tasks);

		sorted.sort((a, b) -> {
			LocalDate da = a.getDeadline();
			LocalDate db = b.getDeadline();
			Integer difa = a.getDifficulty();
			Integer difb = b.getDifficulty();
	
			int result;

			if (da != null && db != null) result = da.compareTo(db);
			else if (da != null) result = -1;
			else if (db != null) result = 1;
			else if (difa != null && difb != null) result = difb.compareTo(difa);
			else if (difa != null) result = -1;
			else if (difb != null) result = 1;
			else result = 0;
		
			return order == SortOrder.DESCENDING ? -result : result;
		});

	return sorted;
	}


	private List<Task> sortByPriority(List<Task> tasks, SortOrder order) {
		List<Task> sorted = new ArrayList<>(tasks);
	
		sorted.sort((a, b) -> {
			double scoreA = calculatePriority(a);
			double scoreB = calculatePriority(b);
			int result = Double.compare(scoreA, scoreB);
			return order == SortOrder.DESCENDING ? -result : result;
		});
		
		return sorted;
	}
    
    
    //===============================//
    //     PRIORITY CALCULATION      //
    //===============================//
	private double calculatePriority(Task task) {
	    double score = 0;

	    if (task.getDeadline() != null) {
	        long daysUntil = LocalDate.now().until(task.getDeadline(), ChronoUnit.DAYS);
	        score += Math.max(0, 100 - daysUntil);
	    }

	    if (task.getDifficulty() != null) {
	        score += task.getDifficulty() * 5;
	    }

	    return score;
	}
}
