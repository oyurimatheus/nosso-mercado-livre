package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;

import javax.validation.constraints.NotBlank;

class NewQuestionRequest {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public Question toQuestion(User user, Product product) {
        return new Question(title, user, product);
    }
}
