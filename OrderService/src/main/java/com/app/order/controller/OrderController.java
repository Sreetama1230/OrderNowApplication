package com.app.order.controller;

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

import com.app.order.dto.OrderCheckOutRequest;
import com.app.order.dto.OrderRequest;
import com.app.order.dto.CheckOutResponse;
import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.model.Order;
import com.app.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

	OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/addtocart")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest order,
			@AuthenticationPrincipal JwtUserPrincipal principal) {
		return new ResponseEntity<Order>(orderService.addToCartOrder(order, principal), HttpStatus.CREATED);
	}

	@PostMapping("/checkout")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<CheckOutResponse> checkoutOrder(@RequestBody OrderCheckOutRequest checkOutRequest,
			@AuthenticationPrincipal JwtUserPrincipal principal) {
		return new ResponseEntity<CheckOutResponse>(orderService.checkout(checkOutRequest, principal),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('BUYER')")
	public ResponseEntity<Order> getById(@PathVariable long id,
			@AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal) {
		return new ResponseEntity<Order>(orderService.getOrderByUserId(id, jwtUserPrincipal.getUserId()),
				HttpStatus.OK);
	}
	
//	@GetMapping("/{id}")
//	@PreAuthorize("hasRole('BUYER')")
//	public ResponseEntity<Order> getById(@PathVariable long id) {
//		return new ResponseEntity<Order>(orderService.getOrderById(id),
//				HttpStatus.OK);
//	}
}
