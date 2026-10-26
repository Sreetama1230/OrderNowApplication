package com.app.payment.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.PaymentResultEvent;


@Service
public class PaymentResultProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final String topic;
	
	Logger logger = LoggerFactory.getLogger(PaymentResultProducer.class);
	
	public PaymentResultProducer(KafkaTemplate<String, Object> kafkaTemplate, 
		@Value("${app.kafka.topic.payment-result}")	String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void publishPaymentResult(PaymentResultEvent event) {
		kafkaTemplate.send(topic , String.valueOf(event.getPaymentId())  
				, event).whenComplete((result , exception)->{
					if(exception != null) {
						logger.error(exception.getMessage());
					}else {
						logger.info("published payment id: "+event.getPaymentId());
					}
				});
		
		
	}
}
