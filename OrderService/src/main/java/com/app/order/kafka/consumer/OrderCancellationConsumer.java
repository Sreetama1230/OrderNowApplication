package com.app.order.kafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderCancellationEvent;
import com.app.core.entity.event.OrderItemEvent;
import com.app.core.entity.event.ProductReleaseEvent;
import com.app.order.kafka.producer.ProductReleaseEventProducer;
import com.app.order.model.Order;
import com.app.order.model.OrderItem;
import com.app.order.model.OrderStatus;
import com.app.order.service.OrderService;

@Service
public class OrderCancellationConsumer {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductReleaseEventProducer productReleaseEventProducer;

	Logger logger = LoggerFactory.getLogger(ProductReservationResultConsumer.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = { "${app.kafka.topic.order-cancel}" })
	public void consume(OrderCancellationEvent cancellationEvent) {

		Order order = orderService.getOrderByIdWithItems(cancellationEvent.getOrderId());

		logger.info("order is cancelled because: " + cancellationEvent.getReason());
		order.setOrderStatus(OrderStatus.CANCELLED);
		orderService.updateOrder(order);

		// publishing product release event
		ProductReleaseEvent productReleaseEvent = new ProductReleaseEvent();
		productReleaseEvent.setOrderId(cancellationEvent.getOrderId());
		List<OrderItemEvent> orderItemEvents = new ArrayList<>();
		
		try {
			for (OrderItem orderItem : order.getItems()) {
				orderItemEvents.add(
						new OrderItemEvent(orderItem.getProductId(), orderItem.getQuantity(), 
								orderItem.getUnitPrice()));
			
			}
			productReleaseEvent.setItems(orderItemEvents);
			
			
			logger.info("publishing the product release event and the number of products to be updated: "+orderItemEvents.size());
			productReleaseEventProducer.publishProductReleaseEvent(productReleaseEvent);
		}catch(Exception e) {
			logger.info("exception occured while publishing the product release event: "+e.getMessage());
			throw e;
		}
		
	}

}
