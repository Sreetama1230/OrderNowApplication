package com.app.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.core.entity.event.OrderEvent;
import com.app.core.entity.event.OrderItemEvent;
import com.app.product.exception.InsufficientQuantityException;
import com.app.product.exception.InvalidProductOrQuantityException;
import com.app.product.exception.ProductNotFoundException;
import com.app.product.model.Product;
import com.app.product.repo.ProductDao;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public Product addProduct(Product product) {
		Optional<Product> dbProduct = productDao.findByNameAndBrandNameAndSellerAndPrice(product.getName(),
				product.getBrandName(), product.getSeller(), product.getPrice());
		if (dbProduct.isPresent()) {
			Product pd = dbProduct.get();
			pd.setQuantity(pd.getQuantity() + product.getQuantity());
			return productDao.save(pd);
		}
		return productDao.save(product);
	}

	public List<Product> getAvailableProduct() {
		return productDao.findByQuantity();
	}

	public Product getById(Long id) {
		Product pd = productDao.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("No product is present with the id"));

		return pd;

	}

	public Product updateProduct(Product product) {

		return productDao.save(product);

	}

	@Transactional
	public boolean isOrderCanBePlaced(OrderEvent event) {
		Map<Long, Long> ids = new HashMap<>();

		for (OrderItemEvent e : event.getItems()) {
			if (e.getProductId() <= 0 || e.getQuantity() <= 0) {
				throw new InvalidProductOrQuantityException("either product or quantity is not accurate");
			}
			ids.put(e.getProductId(), ids.getOrDefault(e.getProductId(), 0L) + e.getQuantity());

		}
		// to reducing the deadlock risks sorting it
		List<Product> products = productDao.findAllByIdForUpdate(ids.keySet().stream().sorted().toList());

		if (ids.size() != products.size()) {
			throw new ProductNotFoundException("One or more products do not exist");
		}
		List<Product> updatedProducts = new ArrayList<>();

		for (Product product : products) {

			if (product.getQuantity() < ids.get(product.getId())) {
				throw new InsufficientQuantityException("Don't have enough quntity");
			}
			product.setQuantity(product.getQuantity() - ids.get(product.getId()));
			updatedProducts.add(product);
		}
		productDao.saveAll(updatedProducts);
		return true;
	}
}
