package com.example.socialmedia.controller;

import com.example.socialmedia.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}/like")
public ResponseEntity<String> likePost(
        @PathVariable UUID postId,
        Authentication authentication) {

    String email = authentication.getName();

    likeService.likePost(email, postId);

    return ResponseEntity.ok("Post liked successfully");
}

    @DeleteMapping("/{postId}/like")
public ResponseEntity<String> unlikePost(
        @PathVariable UUID postId,
        Authentication authentication) {

    String email = authentication.getName();

    likeService.unlikePost(email, postId);

    return ResponseEntity.ok("Post unliked successfully");
}
}