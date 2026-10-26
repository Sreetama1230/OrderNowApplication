package com.app.order.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.PaymentRequestEvent;

@Service
public class PaymentRequestEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(PaymentRequestEventProducer.class);
	
	public PaymentRequestEventProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.payment-request}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishPaymentRequestEvent(PaymentRequestEvent event) {
		kafkaTemplate.send(topic , String.valueOf(event.getOrderId())  
				, event).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error(exception.getMessage());
					}else {
						logger.info("published a checkout request for order id: "+event.getOrderId());
					}
				});
		
		
	}
	
}
