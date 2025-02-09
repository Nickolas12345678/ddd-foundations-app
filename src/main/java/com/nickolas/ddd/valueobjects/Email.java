package com.nickolas.ddd.valueobjects;

public final class Email {
    private final String email;

    public Email(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
