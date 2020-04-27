package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class QuestionResponse {

    private Long id;

    private String title;

    private String user;

    private Map<String, Object> product;

    private LocalDateTime createdAt;

    private QuestionResponse(Long id,
                            String title,
                            String user,
                            Map<String, Object> product,
                            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.product = product;
        this.createdAt = createdAt;
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

    public Map<String, Object> getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static QuestionResponse from(Question question) {
        User user = question.getUser();

        HashMap<String, Object> productResponse = new HashMap<>();
        Product product = question.getProduct();
        productResponse.put("product_id", product.getId());
        productResponse.put("product_name", product.getName());

        return new QuestionResponse(question.getId(), question.getTitle(), user.getUsername(), productResponse, question.getCreatedAt());
    }
}
