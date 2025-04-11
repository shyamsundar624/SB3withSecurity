package com.ims.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ims.enums.TransactionStatus;
import com.ims.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer totalProduct;

	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	private String description;

	private final LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime updatedAt;

	@ManyToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product product;
	
	@ManyToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", totalProduct=" + totalProduct + ", totalPrice=" + totalPrice
				+ ", transactionType=" + transactionType + ", transactionStatus=" +status + ", description="
				+ description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
