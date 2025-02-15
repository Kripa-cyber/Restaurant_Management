package com.restaurant.order.service;

import java.util.Optional;

import com.restaurant.order.entity.Order;

import jakarta.validation.Valid;

public interface OrderService {

	Order createOrder(@Valid Order order);

	Order getOrderById(Long id);



}
