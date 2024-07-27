package com.workintech.ecommerce.E_Commerce_Backend.controller;

import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressCreateDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressDTO;
import com.workintech.ecommerce.E_Commerce_Backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressCreateDTO> addUserAddress(@PathVariable Long userId, @Valid @RequestBody AddressDTO addressDTO) {
        AddressCreateDTO address = addressService.addUserAddress(userId, addressDTO);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(@PathVariable Long userId) {
        List<AddressDTO> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
        AddressDTO address = addressService.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/user/{userId}/{addressId}")
    public ResponseEntity<AddressDTO> updateUserAddress(@PathVariable Long userId, @PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO address = addressService.updateUserAddress(userId, addressId, addressDTO);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/user/{userId}/{addressId}")
    public ResponseEntity<AddressDTO> deleteUserAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        AddressDTO deletedAddress = addressService.deleteUserAddress(userId, addressId);
        return ResponseEntity.ok(deletedAddress);
    }


}
