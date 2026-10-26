package com.app.product.controller;

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

import com.app.product.jwt.dto.JwtUserPrincipal;
import com.app.product.model.Product;
import com.app.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/available")
	public ResponseEntity<List<Product>> getAvailableProduct() {
		return new ResponseEntity<>(productService.getAvailableProduct(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id) {
		return new ResponseEntity<Product>(productService.getById(id), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Product> addProduct(@RequestBody Product product,
			@AuthenticationPrincipal JwtUserPrincipal jwtUserPrincipal) {
		// setting the seller id from jwt token
		product.setSeller(jwtUserPrincipal.getUserId());
		return new ResponseEntity<Product>(productService.addProduct(product), HttpStatus.CREATED);
	}

}
