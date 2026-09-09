package com.example.socialmedia.controller;

import com.example.socialmedia.dto.PostResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
public ResponseEntity<PostResponse> createPost(
        @RequestParam String content,
        Authentication authentication) {

    String email = authentication.getName();

    Post post = postService.createPost(content, email);

    PostResponse response = new PostResponse(
            post.getId(),
            post.getContent(),
            post.getCreatedAt()
    );

    return ResponseEntity.ok(response);
}
    

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {

        List<PostResponse> posts = postService.getAllPosts()
                .stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getContent(),
                        post.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id) {

        Post post = postService.getPostById(id);

        PostResponse response = new PostResponse(
                post.getId(),
                post.getContent(),
                post.getCreatedAt()
        );

        return ResponseEntity.ok(response);
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

    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restorePost(@PathVariable UUID id) {

        postService.restorePost(id);

        return ResponseEntity.ok("Post restored successfully");
    }
}