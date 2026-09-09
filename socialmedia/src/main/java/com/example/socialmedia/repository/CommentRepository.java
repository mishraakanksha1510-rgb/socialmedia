package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdAndIsHiddenFalse(UUID postId);

}