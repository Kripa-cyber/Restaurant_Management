package com.restaurant.order.service;

import java.util.List;

import com.restaurant.order.entity.MenuItem;
import com.restaurant.order.entity.MenuItem.Category;

import jakarta.validation.Valid;

public interface MenuItemService {


	MenuItem createMenuItem(MenuItem menuItem);

	List<MenuItem> getAllMenuItems(Category category);



}
