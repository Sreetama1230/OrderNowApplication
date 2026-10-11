package com.app.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.order.client.ProductClient;
import com.app.order.dto.OrderRequest;
import com.app.order.dto.ProductDTO;
import com.app.order.events.OrderEvent;
import com.app.order.exception.ProductDetailsInvalid;
import com.app.order.jwt.JwtUserPrincipal;
import com.app.order.model.Order;
import com.app.order.model.OrderItem;
import com.app.order.producer.OrderEventProducer;
import com.app.order.service.dao.OrderDao;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private ProductClient productInterface;
	@Autowired
	private OrderEventProducer eventProducer;

	public Order placeOrder(OrderRequest order, JwtUserPrincipal principal) {

		// order service will call product service via feign client
		for (OrderItem orderItem : order.getItems()) {
			ProductDTO productDTO = getById(orderItem.getProductId());
			if (productDTO == null || orderItem.getQuantity() > productDTO.getQuantity()) {
				throw new ProductDetailsInvalid("product details is not correct");
			}

		}
		Order savedOrder = dao.save(OrderRequest.convertToOrder(order, principal));

		OrderEvent event = OrderEvent.convertFromOrderToOrderEvent(savedOrder);
		eventProducer.publishOrderCreatedEvent(event);

		return savedOrder;
	}

	public ProductDTO getById(long id) {
		return productInterface.getProductById(id);
	}
}
