package com.app.payment.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderCancellationEvent;
import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.PaymentRequestEvent;
import com.app.core.entity.event.PaymentResultEvent;

@Service
public class OrderCancellationProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(OrderCancellationProducer.class);
	
	public OrderCancellationProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.order-cancel}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishOrderCancellationEvent(OrderCancellationEvent cancellationEvent) {
		kafkaTemplate.send(topic , String.valueOf(cancellationEvent.getOrderId())  
				, cancellationEvent).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error(exception.getMessage());
					}else {
						logger.info("published order calcellation event for order id: "+cancellationEvent.getOrderId());
					}
				});
		
		
	}
}
