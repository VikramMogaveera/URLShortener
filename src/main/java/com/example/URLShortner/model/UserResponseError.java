package com.example.URLShortner.model;

public class UserResponseError {

    private String status;
    private String error;

    public UserResponseError() {
    }

    public UserResponseError(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "UserResponseError{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
