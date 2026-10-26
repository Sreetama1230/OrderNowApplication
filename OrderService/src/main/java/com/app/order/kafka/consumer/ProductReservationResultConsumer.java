package com.app.order.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.ProductReservationResultEvent;
import com.app.order.model.Order;
import com.app.order.model.OrderStatus;
import com.app.order.service.OrderService;

@Service
public class ProductReservationResultConsumer {

	@Autowired
	private OrderService orderService;

	Logger logger = LoggerFactory.getLogger(ProductReservationResultConsumer.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
			"${app.kafka.topic.product-reservation-result}" })
	public void consume(ProductReservationResultEvent orderEvent) {

		Order order = orderService.getOrderById(orderEvent.getOrderId());
		if (orderEvent.isSuccessful()) {
			logger.info("order is confirmed");
			order.setOrderStatus(OrderStatus.CONFIRMED);

		} else {
			logger.info("order is cancelled");
			order.setOrderStatus(OrderStatus.CANCELLED);

		}
		orderService.updateOrder(order);

	}
}
