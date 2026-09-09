package com.example.socialmedia.service;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.repository.PostRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostCleanupService {

    private final PostRepository postRepository;

    public PostCleanupService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void permanentlyDeleteExpiredPosts() {

        LocalDateTime expiryTime = LocalDateTime.now().minusDays(25);

        List<Post> expiredPosts =
                postRepository.findByIsDeletedTrueAndDeletedAtBefore(expiryTime);

        if (!expiredPosts.isEmpty()) {
            postRepository.deleteAll(expiredPosts);
        }
    }
}