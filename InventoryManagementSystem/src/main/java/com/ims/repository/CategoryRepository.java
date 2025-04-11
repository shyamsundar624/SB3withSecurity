package com.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

	
}
