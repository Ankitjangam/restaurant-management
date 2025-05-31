package com.restaurant.restaurant_management.repository;


import com.restaurant.restaurant_management.model.Category;
import com.restaurant.restaurant_management.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);

    List<MenuItem> findByCategory(Category category);
}
