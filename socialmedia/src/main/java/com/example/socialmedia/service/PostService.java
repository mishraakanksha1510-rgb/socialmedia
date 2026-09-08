package com.example.socialmedia.service;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(String content, Long userId) {
        if (content == null || content.trim().isEmpty()) {
           throw new ContentNotBlankException("Content cannot be empty");
   }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();

        post.setId(UUID.randomUUID());
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public Post updatePost(UUID id, String content) {

    Post post = postRepository.findById(id)
           .orElseThrow(() -> new PostNotFoundException("Post not found"));

    post.setContent(content);

    return postRepository.save(post);
}

public void deletePost(UUID id) {

    Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));
    postRepository.delete(post);
}
}