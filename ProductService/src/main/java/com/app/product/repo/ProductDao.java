package com.app.product.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.product.model.Product;

import jakarta.persistence.LockModeType;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	public Optional<Product> findByNameAndBrandNameAndSellerAndPrice(String name, String brandName, long seller,
			double price);

	@Query("SELECT p FROM Product p where p.quantity > 0")
	public List<Product> findByQuantity();

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM Product p WHERE p.id IN :ids")
	List<Product> findAllByIdForUpdate(@Param("ids") Collection<Long> ids);

}
