package com.app.product.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.ProductReservationResultEvent;
import com.app.product.exception.InvalidOrderException;
import com.app.product.service.ProductService;

@Service
public class ProductKafkaService {

	@Autowired
	private ProductService productService;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${app.kafka.topic.product-reservation-result}")
	private String topic;

	Logger logger = LoggerFactory.getLogger(ProductKafkaService.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = { "${app.kafka.topic.order-created}" })
	public void consume(OrderEvent orderEvent) {

		boolean isSuccessful = false;

		isSuccessful = productService.isOrderCanBePlaced(orderEvent);
		// publish an event to order service - the order can be placed
		ProductReservationResultEvent productReservationResultDTO = new ProductReservationResultEvent(
				orderEvent.getOrderId(), isSuccessful);

		kafkaTemplate.send(topic, String.valueOf(orderEvent.getOrderId()), productReservationResultDTO)
				.whenComplete((result, exception) -> {
					if (exception != null) {
						logger.error("exception happened: "+exception.getMessage());
						throw new InvalidOrderException(exception.getMessage());
					} else {
						logger.info("product reservation event published successfully, order id: "
								+ productReservationResultDTO.getOrderId());
					}
				});

	}
}
