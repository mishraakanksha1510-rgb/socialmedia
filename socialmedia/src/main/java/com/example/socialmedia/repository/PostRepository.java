package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByIsDeletedFalse();
    List<Post> findByIsDeletedTrueAndDeletedAtBefore(LocalDateTime dateTime);
}