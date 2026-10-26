package com.app.order.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.PaymentResultEvent;
import com.app.core.entity.event.enums.PaymentStatus;
import com.app.order.kafka.producer.OrderCreatedEventProducer;
import com.app.order.model.Order;
import com.app.order.model.OrderStatus;
import com.app.order.service.OrderService;

@Service
public class PaymentResultConsumer {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderCreatedEventProducer orderCreatedEventProducer;

	Logger logger = LoggerFactory.getLogger(PaymentResultConsumer.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = { "${app.kafka.topic.payment-result}" })
	public void consume(PaymentResultEvent paymentResultEvent) {

		Order dbOrder = orderService.getOrderByIdWithItems(paymentResultEvent.getOrderId());
		if (PaymentStatus.FAILED.equals(paymentResultEvent.getPaymentStatus())) {

			dbOrder.setOrderStatus(OrderStatus.PAYMENT_FAILED);

		} else {
			//if the order is confirmed then only products quantity will be reduced
			dbOrder.setOrderStatus(OrderStatus.CONFIRMED);
			OrderEvent orderEvent = OrderService.convertFromOrderToOrderEvent(dbOrder);
			orderCreatedEventProducer.publishOrderCreatedEvent(orderEvent);
			
		}
		
		orderService.updateOrder(dbOrder);

	}

}
