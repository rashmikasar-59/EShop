package com.shoppingCart.service.order;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.shoppingCart.model.Order;

public interface IOrderService {
	  Order placeOrder(Long userId);
	    OrderDto getOrder(Long orderId);
	    List<OrderDto> getUserOrders(Long userId);
}
