package com.app.order.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.order.model.Order;
import com.app.order.model.OrderStatus;

import feign.Param;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

	public Optional<Order> findByOrderIdAndUserId(long orderId, long userId);
	public Optional<Order> findByOrderId(long orderId);

	@Query("""
		    SELECT DISTINCT o
		    FROM Order o
		    LEFT JOIN FETCH o.items
		    WHERE o.orderId = :id
		    """)
		Optional<Order> findByIdWithItems(@Param("id") Long id);
	
	
	@Query("SELECT o FROM Order o WHERE o.orderId = :orderId AND o.orderStatus IN ('PAYMENT_PENDING', 'PAYMENT_FAILED')")
	Optional<Order> findPaymentIncompleteOrder( @Param( "orderId") long orderId);
}
