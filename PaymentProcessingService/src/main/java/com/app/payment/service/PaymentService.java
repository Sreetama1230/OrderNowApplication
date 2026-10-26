package com.app.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderCancellationEvent;
import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.PaymentRequestEvent;
import com.app.core.entity.event.PaymentResultEvent;
import com.app.core.entity.event.enums.PaymentStatus;
import com.app.core.entity.event.enums.PaymentType;
import com.app.payment.dao.PaymentRepository;
import com.app.payment.enums.AccountType;
import com.app.payment.exception.AccountNotFoundException;
import com.app.payment.exception.AccountTypeInvalidException;
import com.app.payment.exception.InsufficientAmountException;
import com.app.payment.exception.PaymentNotFoundException;
import com.app.payment.kafka.producer.PaymentResultProducer;
import com.app.payment.kafka.producer.OrderCancellationProducer;
import com.app.payment.model.Account;
import com.app.payment.model.Payment;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	private final PaymentRepository repository;
	private final AccountService accountService;
	private final PaymentResultProducer paymentResponseProducer;
	private final OrderCancellationProducer cancellationProducer;

	PaymentService(PaymentRepository repository, AccountService accountService,
			PaymentResultProducer paymentResponseProducer, OrderCancellationProducer cancellationProducer) {
		this.repository = repository;
		this.accountService = accountService;
		this.paymentResponseProducer = paymentResponseProducer;
		this.cancellationProducer = cancellationProducer;
	}

	Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Transactional
	public Payment createPayment(PaymentRequestEvent paymentRequest) {

		logger.info("FYI unique key is " + paymentRequest.getUniqueKey());
		Optional<Payment> optionalPayment = repository.findByUniqueKey(paymentRequest.getUniqueKey());
		if (!optionalPayment.isPresent()) {
			Payment payment = new Payment();
			PaymentResultEvent event = new PaymentResultEvent();
			payment.setAmount(paymentRequest.getTotalAmount());
			payment.setOrderId(paymentRequest.getOrderId());
			payment.setUserId(paymentRequest.getUserId());
			payment.setCreatedAt(LocalDateTime.now());
			payment.setPaymentType(paymentRequest.getPaymentType());
			payment.setUniqueKey(paymentRequest.getUniqueKey());
			try {
				if (paymentRequest.getAccountId() == 0) {
					if (paymentRequest.getPaymentType().equals(PaymentType.CASH_ON_DELIVERY)) {
						throw new AccountTypeInvalidException(
								"account can not be null as the payment mode is not cash on delivery");
					}
				} else {
					Account account = accountService.getByAccountIdAndUserId(paymentRequest.getAccountId(),
							paymentRequest.getUserId());

					if (account.getAmount().compareTo(payment.getAmount()) < 0) {
						throw new InsufficientAmountException("Insufficient Amount!");
					}
					boolean isvalidAccountType = false;

					if (paymentRequest.getPaymentType().equals(PaymentType.UPI)
							|| paymentRequest.getPaymentType().equals(PaymentType.DEBIT_CARD)
							|| paymentRequest.getPaymentType().equals(PaymentType.NET_BANKING)

					) {
						if (account.getAccountType().equals(AccountType.CURRENT)
								|| account.getAccountType().equals(AccountType.SAVINGS)) {
							isvalidAccountType = true;
						}
					} else {
						if (paymentRequest.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
							if (account.getAccountType().equals(AccountType.CREDIT_CARD_ACCOUNT)) {
								isvalidAccountType = true;
							}
						} else {
							if (paymentRequest.getPaymentType().equals(PaymentType.WALLET)) {
								if (account.getAccountType().equals(AccountType.DIGITAL_WALLET)) {
									isvalidAccountType = true;
								}
							}
						}
					}
					if (!isvalidAccountType) {
						throw new AccountTypeInvalidException("account type is not correct");
					}
					payment.setAccount(account);
					account.setAmount(account.getAmount().subtract(paymentRequest.getTotalAmount()));
					accountService.updateAccount(account);
				}

			} catch (AccountNotFoundException | InsufficientAmountException | AccountTypeInvalidException e) {

				payment.setStatus(PaymentStatus.FAILED);
				Payment createdFailedPayment = repository.save(payment);

				event = convertPaymentResultEvent(createdFailedPayment, e.getMessage());
				paymentResponseProducer.publishPaymentResult(event);

				// publish an extra order cancellation event for releasing the stock
				// cancellationProducer.publishOrderCancellationEvent(convertToCancellationEvent(event));
				// giving user another try
				return createdFailedPayment;
			}

			payment.setStatus(PaymentStatus.SUCCESS);
			Payment successfullPayment = repository.save(payment);

			event = convertPaymentResultEvent(successfullPayment, "payment successfull");
			paymentResponseProducer.publishPaymentResult(event);
			return successfullPayment;

		}

		return optionalPayment.get();

	}

	public static OrderCancellationEvent convertToCancellationEvent(PaymentResultEvent paymentResultEvent) {
		return new OrderCancellationEvent(paymentResultEvent.getOrderId(), paymentResultEvent.getPaymentId(),
				paymentResultEvent.getMessage());
	}

	public List<Payment> getPaymentByUserId(long userId) {
		List<Payment> payments = repository.findByUserId(userId);
		return payments;
	}

	public List<Payment> getPaymentByUserIdAndOrderId(long userId, long orderId) {
		List<Payment> payments = repository.findByUserIdAndOrderId(userId, orderId);
		return payments;
	}

	public static PaymentResultEvent convertPaymentResultEvent(Payment payment, String message) {
		PaymentResultEvent paymentResultEvent = new PaymentResultEvent();
		paymentResultEvent.setPaymentId(payment.getPaymentId());
		paymentResultEvent.setPaymentStatus(payment.getStatus());
		paymentResultEvent.setMessage(message);
		paymentResultEvent.setOrderId(payment.getOrderId());
		return paymentResultEvent;
	}
}
