package com.example.socialmedia.controller;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam String content,
            @RequestParam Long userId) {

        Post post = postService.createPost(content, userId);

        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {

        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
public ResponseEntity<Post> updatePost(
        @PathVariable UUID id,
        @RequestParam String content) {

    return ResponseEntity.ok(postService.updatePost(id, content));
}

@DeleteMapping("/{id}")
public ResponseEntity<String> deletePost(@PathVariable UUID id) {

    postService.deletePost(id);

    return ResponseEntity.ok("Post deleted successfully");
}
}