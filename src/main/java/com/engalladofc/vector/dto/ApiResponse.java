package com.engalladofc.vector.dto;

import java.util.List;

public class ApiResponse<T> {
    private boolean success;
    private List<String> messages;
    private T data;

    public ApiResponse(boolean success, List<String> messages, T data) {
        this.success = success;
        this.messages = messages;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public List<String> getMessages() { return messages; }
    public T getData() { return data; }
}