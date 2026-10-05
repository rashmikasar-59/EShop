package com.shoppingCart.service.product;

import java.util.List;

import com.shoppingCart.model.Product;
import com.shoppingCart.request.AddProductRequest;
import com.shoppingCart.request.ProductUpdateRequest;

public interface IProductService {

	Product addProduct(AddProductRequest product);

	Product getProductById(Long id);

	void deleteProductById(Long id);

	Product updateProduct(ProductUpdateRequest request, Long productId);

	List<Product> getAllProducts();

	List<Product> getProductsByCategory(String category);

	List<Product> getProductsByBrand(String brand);

	List<Product> getProductsByCategoryAndBrand(String category, String brand);

	List<Product> getProductsByName(String name);

	List<Product> getProductsByBrandAndName(String brand, String name);
	
	Long countProductsByBrandAndName(String brand,String name);

}
