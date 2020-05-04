package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

class QuestionResponse {

    private Long id;

    private String title;

    private String user;
    
    private LocalDateTime createdAt;

    private QuestionResponse(Question question) {
        User user = question.getUser();

        this.id = question.getId();
        this.title = question.getTitle();
        this.user = user.getUsername();
        this.createdAt = question.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static List<QuestionResponse> from(List<Question> questions) {

        return questions.stream()
                        .map(QuestionResponse::new)
                        .collect(toList());
    }
}
