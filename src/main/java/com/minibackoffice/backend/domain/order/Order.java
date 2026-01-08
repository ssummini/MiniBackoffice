package com.minibackoffice.backend.domain.order;

import com.minibackoffice.backend.domain.user.User;
import com.minibackoffice.backend.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // orders.user_id (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 20)
    private String status; // CREATED / PAID / CANCELLED

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    protected Order() { } // JPA 기본 생성자

    public Order(User user, String status, Integer totalAmount) {
        this.user = user;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getStatus() { return status; }
    public Integer getTotalAmount() { return totalAmount; }
}
