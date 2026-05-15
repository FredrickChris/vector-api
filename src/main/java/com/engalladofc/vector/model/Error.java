package com.engalladofc.vector.model;

public enum Error {

    TITLE("Title cannot be empty", 1),
    SUBJECT("Subject cannot be empty", 2),
    DESCRIPTION("Max limit of 300 characters", 3),
    DEADLINE("Invalid date Format (YYYY-MM-DD)", 4),
    DIFFICULTY("Not in Range (1-5)", 5);

    private String message;
    private int num;

    Error(String message, int num) {
        this.message = message;
        this.num = num;
    }

    public String getMessage() {
        return message;
    }

    public int getNum() {
        return num;
    }
}