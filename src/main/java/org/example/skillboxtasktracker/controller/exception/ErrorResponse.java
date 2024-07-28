package org.example.skillboxtasktracker.controller.exception;

public record ErrorResponse(String message) {
    public ErrorResponse(Exception e) {
        this(e.getMessage());
    }
}
