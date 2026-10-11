package com.app.order.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.order.model.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

}
