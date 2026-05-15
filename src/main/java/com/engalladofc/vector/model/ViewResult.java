package com.engalladofc.vector.model;

import java.util.ArrayList;

public class ViewResult {
    private ArrayList<Task> tasks;
    private boolean back;
    
    public ViewResult(ArrayList<Task> tasks, boolean back) {
    	this.tasks = tasks;
    	this.back = back;
    }
    
    public ArrayList<Task> getTasks() {
    	return tasks;
    }
    
    public boolean getBack() {
    	return back;
    }
}
