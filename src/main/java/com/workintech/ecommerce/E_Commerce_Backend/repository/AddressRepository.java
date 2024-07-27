package com.workintech.ecommerce.E_Commerce_Backend.repository;

import com.workintech.ecommerce.E_Commerce_Backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
