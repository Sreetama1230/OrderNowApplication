package com.app.payment.kafka.consumer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.app.payment.dto.PaymentRequest;
import com.app.payment.service.PaymentService;
import com.app.core.entity.event.PaymentRequestEvent;

@Service
public class PaymentRequestConsumer {

	@Autowired
	private PaymentService paymentService;

	Logger logger = LoggerFactory.getLogger(PaymentRequestConsumer.class);

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = { "${app.kafka.topic.payment-request}" })
	public void consume(PaymentRequestEvent paymentRequestEvent) {

		paymentService.createPayment(paymentRequestEvent);
	}
}
