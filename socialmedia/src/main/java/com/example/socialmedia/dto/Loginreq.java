package com.example.socialmedia.dto;

public class Loginreq {

    private String email;
    private String password;

    public Loginreq() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}