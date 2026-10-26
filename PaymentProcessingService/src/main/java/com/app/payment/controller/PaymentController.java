package com.app.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payment.dto.PaymentRequest;
import com.app.payment.jwt.JwtUserPrincipal;
import com.app.payment.model.Payment;
import com.app.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<List<Payment>> getPaymentByUserId(@AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal){
		return new ResponseEntity<List<Payment>>(
				paymentService
				.getPaymentByUserId(jwtUserPrincipal.getUserId()), HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<List<Payment>> getPaymentByUserIdAndOrderId(
			@AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal ,  @PathVariable long orderId ){
		return new ResponseEntity<List<Payment>>(
				paymentService
				.getPaymentByUserIdAndOrderId(jwtUserPrincipal.getUserId() , orderId), HttpStatus.OK);
	}
//	@PostMapping
//	public ResponseEntity<Payment> refund(@AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal, @PathVariable long orderId ){
//		
//	}

}
