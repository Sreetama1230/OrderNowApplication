package com.app.order.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;

@Service
public class OrderCreatedEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(OrderCreatedEventProducer.class);
	
	public OrderCreatedEventProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.order-created}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishOrderCreatedEvent(OrderEvent event) {
		kafkaTemplate.send(topic , String.valueOf(event.getOrderId())  
				, event).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error(exception.getMessage());
					}else {
						logger.info("published order id: "+event.getOrderId());
					}
				});
		
		
	}
}
