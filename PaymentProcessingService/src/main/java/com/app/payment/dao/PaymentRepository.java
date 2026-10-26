package com.app.payment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.payment.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public Optional<Payment> findByPaymentIdAndUserId(long paymentId, long userId);
	
	public List<Payment> findByUserId(long userId);
	
	public List<Payment> findByUserIdAndOrderId(long userId , long orderId);
	public Optional<Payment> findByUniqueKey(String uniqueKey);
}
