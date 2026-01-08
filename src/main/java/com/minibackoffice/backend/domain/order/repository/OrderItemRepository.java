package com.minibackoffice.backend.domain.order.repository;

import com.minibackoffice.backend.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
