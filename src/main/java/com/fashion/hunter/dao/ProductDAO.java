package com.fashion.hunter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fashion.hunter.dto.ProductDTO;

@Repository
public interface ProductDAO {

	void insertProductImage(ProductDTO requestDTO);

	List<ProductDTO> getAllProductRecords(ProductDTO requestDTO);

	int deleteProductImage(String productId);

	int updateProductImage(ProductDTO requestDTO);
}