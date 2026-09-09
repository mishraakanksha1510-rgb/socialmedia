package com.example.socialmedia.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class CommentModerationService {

    private final OpenAIClient openAIClient;

    public CommentModerationService(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public boolean isInappropriate(String content) {

    try {

        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_5_2)
                        .addSystemMessage(
                                "You are a comment moderation system. " +
                                "Classify the comment as appropriate or inappropriate. " +
                                "Reply with only APPROPRIATE or INAPPROPRIATE."
                        )
                        .addUserMessage(content)
                        .build();

        var response = openAIClient.chat()
                .completions()
                .create(params);

        String result = response.choices()
                .get(0)
                .message()
                .content()
                .orElse("")
                .trim()
                .toUpperCase();

        System.out.println("AI RESULT: " + result);
        return result.contains("INAPPROPRIATE");

    } catch (com.openai.errors.RateLimitException e) {

        System.out.println("OpenAI rate limit or credits exhausted.");

        return true;

    } catch (Exception e) {

        System.out.println("AI moderation failed: " + e.getMessage());

        return true;
    }
  }
}