package com.workintech.ecommerce.E_Commerce_Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order", schema = "ecommerce")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Order date is required")
    @Column(name = "order_date")
    private String orderDate;

//    @NotNull(message = "Order date is required")
//    @PastOrPresent(message = "Order date cannot be in the future")
//    @Column(name = "order_date")
//    private LocalDate orderDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    @Column(name = "total_price")
    private Double totalPrice;

    @NotNull(message = "Order items cannot be null")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
