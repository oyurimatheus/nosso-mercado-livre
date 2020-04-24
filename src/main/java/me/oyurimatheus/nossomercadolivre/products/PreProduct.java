package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import me.oyurimatheus.nossomercadolivre.users.User;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * A previous state of a product with its basic information
 */
class PreProduct {

    private final UUID id;
    private final User user;
    private final Category category;
    private final String name;
    private final BigDecimal price;
    private final Integer stockQuantity;
    private final String description;

    /**
     *
     * @param user who is creating the product
     * @param category which products belongs to
     * @param name products name
     * @param price products price
     * @param stockQuantity product stock quantity
     * @param description product description
     */
    public PreProduct(User user,
                      Category category,
                      String name,
                      BigDecimal price,
                      Integer stockQuantity,
                      String description) {

        this.id = UUID.randomUUID();
        this.user = user;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getDescription() {
        return description;
    }
}
