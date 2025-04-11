package com.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ims.entity.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	@Query("SELECT t FROM Transaction t " +
		       "WHERE YEAR(t.createdAt) = :year AND MONTH(t.createdAt) = :month")
	List<Transaction> findAllByMonthAndYear(@Param("year") int year,@Param("month") int month);


	@Query("SELECT t FROM Transaction t " +
		       "LEFT JOIN t.product p " +
		       "WHERE (:searchText IS NULL OR " +
		       "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
		       "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
		       "LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchText, '%')))")
		Page<Transaction> searchTransaction(@Param("searchText") String searchText, Pageable pageable);

}
