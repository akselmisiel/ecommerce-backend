package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressCreateDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Address;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getUserAddresses(Long userId);
    List<AddressDTO> getAllAddresses();
    AddressCreateDTO addUserAddress(Long userId, AddressDTO addressDTO);
    AddressDTO getAddress(Long addressId);
    AddressDTO updateUserAddress(Long userId, Long addressId, AddressDTO addressDTO);
    AddressDTO deleteUserAddress(Long userId, Long addressId);
    //boolean isDefaultAddress(Long userId, Long addressId);
    AddressDTO convertToAddressDTO(Address address);
}
