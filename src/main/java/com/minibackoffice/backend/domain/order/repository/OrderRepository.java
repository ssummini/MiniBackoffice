package com.minibackoffice.backend.domain.order.repository;

import com.minibackoffice.backend.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
