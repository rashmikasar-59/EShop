package com.shoppingCart.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingCart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
