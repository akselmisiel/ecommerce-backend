package com.workintech.ecommerce.E_Commerce_Backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product", schema = "ecommerce")
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be less than 100 characters")
        @Column(name = "product_name")
        private String productName;

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or positive")
        @Column(name = "price")
        private Double price;

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be zero or positive")
        @Column(name = "quantity")
        private Integer quantity;

        @Size(max = 500, message = "Description must be less than 500 characters")
        @Column(name = "description")
        private String description;

        @Size(max = 255, message = "Product image URL must be less than 255 characters")
        @Column(name = "product_image")
        private String productImage;

        @NotNull(message = "Category is required")
        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

//        @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
//        private List<OrderItem> orderItems = new ArrayList<>();

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<CartItem> cartItems = new ArrayList<>();

        public Product(String productName, Double price, Integer quantity, String description, String productImage, Category category) {
                this.productName = productName;
                this.price = price;
                this.quantity = quantity;
                this.description = description;
                this.productImage = productImage;
                this.category = category;
        }


}
