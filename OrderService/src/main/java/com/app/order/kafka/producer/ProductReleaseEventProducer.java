package com.app.order.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.ProductReleaseEvent;

@Service
public class ProductReleaseEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(ProductReleaseEventProducer.class);
	
	public ProductReleaseEventProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.product-release-request}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishProductReleaseEvent(ProductReleaseEvent event) {
		
		kafkaTemplate.send(topic , String.valueOf(event.getOrderId())  
				, event).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error( "exception happened while publishing the product release event: "+ exception.getMessage());
					}else {
						logger.info("product release event has been publised for order id: "+event.getOrderId()+" and number of items are "+event.getItems().size());
					}
				});

	}
}
