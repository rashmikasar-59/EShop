package com.shoppingCart.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shoppingCart.dto.ImageDto;
import com.shoppingCart.dto.ProductDto;
import com.shoppingCart.exceptions.ProductNotFoundException;
import com.shoppingCart.model.Category;
import com.shoppingCart.model.Image;
import com.shoppingCart.model.Product;
import com.shoppingCart.repository.CategoryRepository;
import com.shoppingCart.repository.ImageRepository;
import com.shoppingCart.repository.ProductRepository;
import com.shoppingCart.request.AddProductRequest;
import com.shoppingCart.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepo;
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		// TODO Auto-generated method stub

		// optional - explicitly tells you:

		// "A Product might exist, or it might not."

		/*
		 * 
		 * Optional<Product> means:
		 * 
		 * "There may or may not be a Product object."
		 * 
		 * Think of Optional as a container that can contain a Product or be empty.
		 * 
		 */

		Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepo.save(newCategory);
				});
		request.setCategory(category);

		return productRepository.save(createProduct(request, category));

	}

	private Product createProduct(AddProductRequest request, Category category) {
		// TODO Auto-generated method stub
		return new Product(request.getName(), request.getBrand(), request.getPrice(), request.getInventory(),
				request.getDescription(), category);

	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub

		return productRepository.findById(id).orElseThrow(() -> {
			throw new ProductNotFoundException("Product not found!");
		});
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
			throw new ProductNotFoundException("product not found!");
		});
		;
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		// TODO Auto-generated method stub

		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request)).map(productRepository::save)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));

	}

	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		// TODO Auto-generated method stub

		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setDescription(request.getDescription());

		Category category = categoryRepo.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		// TODO Auto-generated method stub
		return productRepository.countByBrandAndName(brand, name);

	}

	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this::convertToDto).toList();
	}

	@Override
    public ProductDto convertToDto(Product product) {
    ProductDto productDto = modelMapper.map(product, ProductDto.class);
    List<Image> images = imageRepository.findByProductId(product.getId());
    List<ImageDto> imageDtos = images.stream()
            .map(image -> modelMapper.map(image, ImageDto.class))
            .toList();
    productDto.setImages(imageDtos);
    return productDto;
}
}
