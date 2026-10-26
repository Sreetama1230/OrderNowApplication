package com.app.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.OrderItemEvent;
import com.app.core.entity.event.PaymentRequestEvent;
import com.app.core.entity.event.PaymentResultEvent;
import com.app.core.entity.event.enums.PaymentStatus;
import com.app.order.client.ProductClient;
import com.app.order.dto.OrderCheckOutRequest;
import com.app.order.dto.OrderItemRequest;
import com.app.order.dto.OrderRequest;
import com.app.order.dto.CheckOutResponse;
import com.app.order.dto.ProductDTO;
import com.app.order.exception.OrderNotFoundException;
import com.app.order.exception.ProductDetailsInvalid;
import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.kafka.producer.OrderCreatedEventProducer;
import com.app.order.kafka.producer.PaymentRequestEventProducer;
import com.app.order.model.Order;
import com.app.order.model.OrderItem;
import com.app.order.service.dao.OrderDao;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private ProductClient productInterface;

	@Autowired
	private PaymentRequestEventProducer paymentRequestEventProducer;

	@Autowired
	private OrderCreatedEventProducer orderCreatedEventProducer;

	@Transactional
	public Order addToCartOrder(OrderRequest order, JwtUserPrincipal principal) {

		// order service will call product service via feign client
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItemRequest orderItemRequest : order.getItems()) {
			ProductDTO productDTO = getProductById(orderItemRequest.getProductId());
			if (productDTO == null || orderItemRequest.getQuantity() > productDTO.getQuantity()) {
				throw new ProductDetailsInvalid("product quantity is not correct");
			}

			orderItems.add(OrderItem.convertToOrderItem(orderItemRequest, principal, productDTO));

		}

		Order savedOrder = dao.save(OrderRequest.convertToOrder(principal, orderItems));

//		OrderEvent orderEvent = convertFromOrderToOrderEvent(savedOrder);
//		orderCreatedEventProducer.publishOrderCreatedEvent(orderEvent);
		return savedOrder;
	}

	public CheckOutResponse checkout(OrderCheckOutRequest checkoutReq, JwtUserPrincipal principal) {
		// publish an event
		PaymentRequestEvent paymentRequestEvent = convertToPaymentRequestEvent(checkoutReq, principal);

		Order order = findPaymentIncompleteOrder(checkoutReq.getOrderId());
		paymentRequestEvent.setTotalAmount(new BigDecimal(order.getTotalAmount()));
		paymentRequestEvent.setUniqueKey(UUID.randomUUID().toString());
		paymentRequestEventProducer.publishPaymentRequestEvent(paymentRequestEvent);
		CheckOutResponse checkOutResponse = new CheckOutResponse(checkoutReq.getOrderId(), PaymentStatus.PENDING,
				"payment initiated");
		return checkOutResponse;
	}

	public Order findPaymentIncompleteOrder(long orderId) {
		Optional<Order> optionalOrder = dao.findPaymentIncompleteOrder(orderId);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get();
		}
		throw new OrderNotFoundException("order id is not correct: " + orderId);
	}

	public Order getOrderById(long id) {
		Optional<Order> optionalOrder = dao.findByOrderId(id);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get();
		}
		throw new OrderNotFoundException("order id is not correct: " + id);
	}

	public Order getOrderByUserId(long id, long userId) {
		Optional<Order> optionalOrder = dao.findByOrderIdAndUserId(id, userId);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get();
		}
		throw new OrderNotFoundException("order id is not correct");
	}

	@Transactional
	public Order updateOrder(Order order) {
		return dao.save(order);
	}

	@Transactional
	public Order getOrderByIdWithItems(long id) {
		return dao.findByIdWithItems(id).orElseThrow(() -> new OrderNotFoundException("Order not found: " + id));
	}

	public ProductDTO getProductById(long id) {
		try {
			return productInterface.getProductById(id);
		} catch (Exception exception) {
			throw new ProductDetailsInvalid("provided product id(s) are not correct ");

		}

	}

	public static OrderEvent convertFromOrderToOrderEvent(Order order) {
		OrderEvent event = new OrderEvent();

		List<OrderItemEvent> itemEvents = order.getItems().stream().map(e -> OrderService.convertToOrderItemEvent(e))
				.toList();

		int totalAmount = 0;
		for (OrderItemEvent itemEvent : itemEvents) {
			totalAmount += itemEvent.getQuantity() * itemEvent.getUnitPrice();
		}
		event.setItems(itemEvents);
		event.setOrderId(order.getOrderId());
		event.setUserId(order.getUserId());
		event.setTotalAmount(totalAmount);
		return event;

	}

	public static PaymentRequestEvent convertToPaymentRequestEvent(OrderCheckOutRequest orderCheckOutRequest,
			JwtUserPrincipal principal) {
		PaymentRequestEvent event = new PaymentRequestEvent();

		event.setAccountId(orderCheckOutRequest.getAccountId());
		event.setOrderId(orderCheckOutRequest.getOrderId());
		event.setPaymentType(orderCheckOutRequest.getPaymentType());
		event.setUserId(principal.getUserId());
		return event;

	}

	public static OrderItemEvent convertToOrderItemEvent(OrderItem item) {

		return new OrderItemEvent(item.getProductId(), item.getQuantity(), item.getUnitPrice());
	}
}
