package com.ims.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Name is Required")
	private String name;

	@NotBlank(message = "SKU is Required")
	@Column(unique = true)
	private String sku;

	@Positive(message = "Product Price must be positive Value")

	private BigDecimal price;
	@Min(value = 0, message = "Stock Quantity cannot be lesser than Zero")
	private Integer stockQuantity;

	private String description;
	private String imageUrl;
	private LocalDateTime expiryDate;
	private final LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", sku=" + sku + ", price=" + price + ", stockQuantity="
				+ stockQuantity + ", description=" + description + ", imageUrl=" + imageUrl + ", expiryDate="
				+ expiryDate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	
}
