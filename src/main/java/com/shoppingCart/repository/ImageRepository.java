package com.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingCart.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
