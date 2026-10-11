package com.app.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.order.dto.OrderRequest;
import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.model.Order;
import com.app.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/place")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest order,
			@AuthenticationPrincipal JwtUserPrincipal principal) {
		return new ResponseEntity<Order>(orderService.placeOrder(order, principal), HttpStatus.CREATED);
	}

}
