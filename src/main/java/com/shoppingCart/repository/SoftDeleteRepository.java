package com.shoppingCart.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingCart.model.User;

public interface SoftDeleteRepository extends JpaRepository<User, Long> {
	 @Modifying
	    @Query(value = """
	            DELETE ci FROM cart_item ci
	            LEFT JOIN cart c ON ci.cart_id = c.id
	            WHERE (ci.deleted = true AND ci.deleted_at < :cutoff)
	               OR (c.deleted = true AND c.deleted_at < :cutoff)
	            """, nativeQuery = true)
	    int purgeCartItems(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = """
	            DELETE c FROM cart c
	            LEFT JOIN `user` u ON c.user_id = u.id
	            WHERE (c.deleted = true AND c.deleted_at < :cutoff)
	               OR (u.deleted = true AND u.deleted_at < :cutoff)
	            """, nativeQuery = true)
	    int purgeCarts(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = """
	            DELETE oi FROM order_item oi
	            INNER JOIN orders o ON oi.order_id = o.order_id
	            WHERE (oi.deleted = true AND oi.deleted_at < :cutoff)
	               OR (o.deleted = true AND o.deleted_at < :cutoff)
	            """, nativeQuery = true)
	    int purgeOrderItems(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = """
	            DELETE o FROM orders o
	            LEFT JOIN `user` u ON o.user_id = u.id
	            WHERE (o.deleted = true AND o.deleted_at < :cutoff)
	               OR (u.deleted = true AND u.deleted_at < :cutoff)
	            """, nativeQuery = true)
	    int purgeOrders(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = """
	            DELETE i FROM image i
	            LEFT JOIN product p ON i.product_id = p.id
	            WHERE (i.deleted = true AND i.deleted_at < :cutoff)
	               OR (p.deleted = true AND p.deleted_at < :cutoff)
	            """, nativeQuery = true)
	    int purgeImages(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = "DELETE FROM product WHERE deleted = true AND deleted_at < :cutoff", nativeQuery = true)
	    int purgeProducts(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = "DELETE FROM category WHERE deleted = true AND deleted_at < :cutoff", nativeQuery = true)
	    int purgeCategories(@Param("cutoff") LocalDateTime cutoff);

	    @Modifying
	    @Query(value = "DELETE FROM `user` WHERE deleted = true AND deleted_at < :cutoff", nativeQuery = true)
	    int purgeUsers(@Param("cutoff") LocalDateTime cutoff);
	

}
