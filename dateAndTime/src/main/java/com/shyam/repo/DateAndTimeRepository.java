package com.shyam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.entity.DateAndTime;

public interface DateAndTimeRepository extends JpaRepository<DateAndTime,Long>{

}
