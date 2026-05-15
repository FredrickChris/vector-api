package com.engalladofc.vector.model;

public enum Status {
	
	COMPLETED("Completed"),
	IN_PROGRESS("In Progress"),
	PENDING("Pending");
	
	private String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
