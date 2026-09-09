package com.example.socialmedia.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostResponse {

    private UUID id;
    private String content;
    private LocalDateTime createdAt;

    public PostResponse(UUID id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}