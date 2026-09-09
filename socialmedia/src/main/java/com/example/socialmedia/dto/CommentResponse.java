package com.example.socialmedia.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private boolean hidden;

    public CommentResponse(
            Long id,
            String content,
            LocalDateTime createdAt,
            boolean hidden) {

        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.hidden = hidden;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isHidden() {
        return hidden;
    }
}