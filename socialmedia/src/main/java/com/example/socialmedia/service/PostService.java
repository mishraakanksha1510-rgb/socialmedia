
package com.example.socialmedia.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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

    public Post createPost(String content, String email) {

        if (content == null || content.trim().isEmpty()) {
            throw new ContentNotBlankException("Content cannot be empty");
        }

        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();

        post.setId(UUID.randomUUID());
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findByIsDeletedFalse();
    }

    public Post getPostById(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public Post updatePost(UUID id, String content) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String loggedInEmail = authentication.getName();

        if (!post.getUser().getEmail().equals(loggedInEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to update this post"
            );
        }

        if (content == null || content.trim().isEmpty()) {
            throw new ContentNotBlankException("Content cannot be empty");
        }

        post.setContent(content);

        return postRepository.save(post);
    }

    public void deletePost(UUID id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String loggedInEmail = authentication.getName();

        if (!post.getUser().getEmail().equals(loggedInEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this post"
            );
        }

        post.setDeleted(true);
        post.setDeletedAt(LocalDateTime.now());

        postRepository.save(post);
    }

   public void restorePost(UUID id) {

    Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String loggedInEmail = authentication.getName();

    if (!post.getUser().getEmail().equals(loggedInEmail)) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to restore this post"
        );
    }

    if (!post.isDeleted()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Post is not deleted"
        );
    }

    if (post.getDeletedAt() == null ||
            post.getDeletedAt().isBefore(LocalDateTime.now().minusDays(25))) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Restore period of 25 days has expired"
        );
    }

    post.setDeleted(false);
    post.setDeletedAt(null);

    postRepository.save(post);
}
}