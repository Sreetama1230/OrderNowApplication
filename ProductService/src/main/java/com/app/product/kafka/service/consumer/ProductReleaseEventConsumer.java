package com.app.product.kafka.service.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderCancellationEvent;
import com.app.core.entity.event.OrderItemEvent;
import com.app.core.entity.event.ProductReleaseEvent;
import com.app.core.entity.event.ProductReservationResultEvent;
import com.app.product.kafka.service.producer.ProductReleaseEventResultProducer;
import com.app.product.model.Product;
import com.app.product.service.ProductService;

@Service
public class ProductReleaseEventConsumer {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductReleaseEventResultProducer productReleaseEventResultProducer;

	Logger logger = LoggerFactory.getLogger(ProductReleaseEventConsumer.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
			"${app.kafka.topic.product-release-request}" })
	public void consume(ProductReleaseEvent productReleaseEvent) {
		ProductReservationResultEvent productReservationResultEvent = new ProductReservationResultEvent();
		productReservationResultEvent.setOrderId(productReleaseEvent.getOrderId());
		
		try {
			for (OrderItemEvent orderItemEvent : productReleaseEvent.getItems()) {
				Product product = productService.getById(orderItemEvent.getProductId());
				product.setQuantity(product.getQuantity() + orderItemEvent.getQuantity());
				productService.updateProduct(product);
			}
			productReservationResultEvent.setSuccessful(true);

		} catch (Exception exception) {
			logger.error("exception occured while iterating the ordered items "+exception.getMessage());
			productReservationResultEvent.setSuccessful(false);
		}

		productReleaseEventResultProducer.publishProductReservationResultEvent(productReservationResultEvent);

	}

}
