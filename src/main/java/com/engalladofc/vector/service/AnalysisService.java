package com.engalladofc.vector.service;

import org.springframework.stereotype.Service;

import com.engalladofc.vector.model.Task;
import com.engalladofc.vector.model.Status;


import java.util.ArrayList;
import java.time.LocalDate;	
import java.time.temporal.ChronoUnit;

@Service
public class AnalysisService {
	
    //===============================//
    //           FILTERING           //
    //===============================//
	public ArrayList<Task> filterByDate(ArrayList<Task> tasks, LocalDate date) {
		ArrayList<Task> filtered = new ArrayList<>();
		for(Task task: tasks) {
			if(task.getDeadline() == date) {
				filtered.add(task);
			}
		}
		return filtered;
	}
	
	
	public ArrayList<Task> filterByDays(ArrayList<Task> tasks, Integer min, Integer max) {
		ArrayList<Task> filtered = new ArrayList<>();
		for(Task task: tasks) {
			if(task.getDeadline() != null) {
				long deadline = LocalDate.now().until(task.getDeadline(), ChronoUnit.DAYS);
				if (deadline >= min && deadline <= max) {
					filtered.add(task);
				}
			}
		}
		return filtered;
	}
	
	
	public ArrayList<Task> filterByDifficulty(ArrayList<Task> tasks, Integer min, Integer max) {
		ArrayList<Task> filtered = new ArrayList<>();
		for(Task task: tasks) {
			Integer difficulty = task.getDifficulty();
			if(difficulty != null && difficulty >= min && difficulty <= max) {
				filtered.add(task);
			}
		}
		return filtered;
	}
	
	
	public ArrayList<Task> filterByStatus(ArrayList<Task> tasks, Status status) {
		ArrayList<Task> filtered = new ArrayList<>();
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
	public ArrayList<Task> sortByDeadline(ArrayList<Task> tasks) {
	    ArrayList<Task> sorted = new ArrayList<>(tasks);
	    
	    sorted.sort((a, b) -> {
	    	
	        LocalDate da = a.getDeadline();
	        LocalDate db = b.getDeadline();
	        Integer difa = a.getDifficulty();
	        Integer difb = b.getDifficulty();

	        if (da != null && db != null) return da.compareTo(db);
	        if (da != null) return -1;
	        if (db != null) return 1;

	        if (difa != null && difb != null) return difb.compareTo(difa);
	        if (difa != null) return -1;
	        if (difb != null) return 1;

	        return 0;
	    });
	    return sorted;
	}
	
	
	public ArrayList<Task> sortByPriority(ArrayList<Task> tasks) {
	    ArrayList<Task> sorted = new ArrayList<>(tasks);
	    
	    sorted.sort((a, b) -> {
	        double scoreA = calculatePriority(a);
	        double scoreB = calculatePriority(b);
	        return Double.compare(scoreB, scoreA); // highest score first
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
