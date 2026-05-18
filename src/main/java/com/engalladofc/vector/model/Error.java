package com.engalladofc.vector.model;

public enum Error {

    TITLE("Title cannot be empty"),
    SUBJECT("Subject cannot be empty"),
    DESCRIPTION("Max limit of 300 characters"),
    DEADLINE("Invalid date Format (YYYY-MM-DD)"),
    DIFFICULTY("Not in Range (1-5)");

    private String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}