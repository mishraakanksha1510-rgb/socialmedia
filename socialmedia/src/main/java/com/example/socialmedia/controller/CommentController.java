package com.example.socialmedia.controller;

import com.example.socialmedia.dto.CommentResponse;
import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
public ResponseEntity<CommentResponse> addComment(
        @PathVariable UUID postId,
        @RequestParam String content,
        Authentication authentication) {

    String email = authentication.getName();

    Comment comment = commentService.addComment(
            email,
            content,
            postId
    );

        CommentResponse response = new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.isHidden()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable UUID postId) {

        List<CommentResponse> comments =
                commentService.getComments(postId)
                        .stream()
                        .map(comment -> new CommentResponse(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.isHidden()
                        ))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }
}