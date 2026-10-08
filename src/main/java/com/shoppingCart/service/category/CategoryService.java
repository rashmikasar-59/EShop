package com.shoppingCart.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingCart.exceptions.AlreadyExistsException;
import com.shoppingCart.exceptions.ResourceNotFoundException;
import com.shoppingCart.model.Category;
import com.shoppingCart.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
		.map(categoryRepository::save)
		.orElseThrow(()->new AlreadyExistsException(category.getName()+" Already exits"));
	
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getCategoryById(id)).map(oldcategory -> {

			oldcategory.setName(category.getName());
			return categoryRepository.save(oldcategory);
		}).orElseThrow(() -> new ResourceNotFoundException("category not found "));
	}

	@Override
	public void deleteCategoryById(Long id) {

	    categoryRepository.findById(id).ifPresentOrElse(category -> {
	        category.softDelete();
	        categoryRepository.save(category);
	    }, () -> {
	        throw new ResourceNotFoundException("Category not found");
	    });
	}
	}


