package com.restaurant.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.order.entity.MenuItem;
import com.restaurant.order.repository.MenuItemRepository;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems(MenuItem.Category category) {
        if (category != null) {
            return menuItemRepository.findByCategory(category);
        }
        return menuItemRepository.findAll();
    }
}