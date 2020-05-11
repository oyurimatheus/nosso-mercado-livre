package me.oyurimatheus.nossomercadolivre.purchase;

import io.jsonwebtoken.lang.Assert;
import me.oyurimatheus.nossomercadolivre.products.Product;
import me.oyurimatheus.nossomercadolivre.users.User;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static io.jsonwebtoken.lang.Assert.notNull;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static me.oyurimatheus.nossomercadolivre.purchase.Status.INICIADA;

@Table(name = "purchase")
@Entity
public
class Purchase {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    @Column(name = "purchase_quantity")
    private int quantity;

    @Enumerated(value = STRING)
    @Column(name = "purchase_payment_gateway")
    private PaymentGateway paymentGateway;

    @Enumerated(value = STRING)
    @Column(name = "purchase_status")
    private Status status;

    @Column(name = "purchase_total")
    private BigDecimal total;


    Purchase(@NotNull User user,
             @NotNull Product product,
             @Min(1) int quantity,
             @NotNull PaymentGateway paymentGateway) {

        notNull(user, "user must not be null");
        notNull(product, "product must not be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must not be less than 0");
        }
        notNull(paymentGateway, "paymentGateway must not be null");

        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.paymentGateway = paymentGateway;
        this.status = INICIADA;
        this.total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String paymentUrl(@URL String redirectUrl) {
        return paymentGateway.paymentUrl(this, redirectUrl);
    }
}
