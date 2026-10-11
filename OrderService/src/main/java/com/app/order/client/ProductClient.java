package com.app.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.order.dto.ProductDTO;

@FeignClient(name = "ProductService", url = "${services.product.url}")
public interface ProductClient {

	@GetMapping("/product/{id}")
	public  ProductDTO getProductById( @PathVariable("id") long id);
}
