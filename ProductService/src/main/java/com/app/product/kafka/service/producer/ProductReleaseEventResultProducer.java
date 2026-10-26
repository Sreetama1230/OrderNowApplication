package com.app.product.kafka.service.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.app.core.entity.event.ProductReservationResultEvent;

@Service
public class ProductReleaseEventResultProducer {

	
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(ProductReleaseEventResultProducer.class);
	
	public ProductReleaseEventResultProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.product-release-result}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishProductReservationResultEvent( ProductReservationResultEvent productReservationResultEvent) {
		kafkaTemplate.send(topic , String.valueOf(productReservationResultEvent.getOrderId())  
				, productReservationResultEvent).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error(exception.getMessage());
					}else {
						logger.info("ordered stocks have been released FYI the order id is "+productReservationResultEvent.getOrderId());
					}
				});
		
		
	}
}
