package com.workintech.ecommerce.E_Commerce_Backend.service;

import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressCreateDTO;
import com.workintech.ecommerce.E_Commerce_Backend.dto.AddressDTO;
import com.workintech.ecommerce.E_Commerce_Backend.entity.Address;
import com.workintech.ecommerce.E_Commerce_Backend.entity.User;
import com.workintech.ecommerce.E_Commerce_Backend.repository.AddressRepository;
import com.workintech.ecommerce.E_Commerce_Backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;
    private UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressCreateDTO addUserAddress(Long userId, AddressDTO addressDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = new Address();
        address.setAddressLine(addressDTO.addressLine());
        address.setCity(addressDTO.city());
        address.setCountry(addressDTO.country());
        address.setPostalCode(addressDTO.postalCode());
        address.setStreetName(addressDTO.streetName());
        address.setBuildingName(addressDTO.buildingName());


        address.getUsers().add(user);
        user.getAddresses().add(address);


        addressRepository.save(address);


        userRepository.save(user);
        return new AddressCreateDTO(address.getId(), userId, address.getAddressLine(), address.getCity(), address.getCountry(), address.getPostalCode(), address.getStreetName(), address.getBuildingName());
    }

    @Override
    public List<AddressDTO> getUserAddresses(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAddresses().stream().map(this::convertToAddressDTO).toList();
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream().map(this::convertToAddressDTO).toList();
    }

    @Override
    public AddressDTO getAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
        return convertToAddressDTO(address);
    }

    @Override
    public AddressDTO updateUserAddress(Long userId, Long addressId, AddressDTO addressDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = user.getAddresses().stream().filter(a -> a.getId().equals(addressId)).findFirst().orElseThrow(() -> new RuntimeException("Address not found"));
        address.setAddressLine(addressDTO.addressLine());
        address.setCity(addressDTO.city());
        address.setCountry(addressDTO.country());
        address.setPostalCode(addressDTO.postalCode());
        address.setStreetName(addressDTO.streetName());
        address.setBuildingName(addressDTO.buildingName());
        addressRepository.save(address);
        userRepository.save(user);
        return convertToAddressDTO(address);
    }

    @Override
    public AddressDTO deleteUserAddress(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = user.getAddresses().stream().filter(a -> a.getId().equals(addressId)).findFirst().orElseThrow(() -> new RuntimeException("Address not found"));

        user.getAddresses().remove(address);

        userRepository.save(user);

        addressRepository.delete(address);
        return convertToAddressDTO(address);
    }

//    @Override
//    public boolean isDefaultAddress(Long userId, Long addressId) {
//        return addressRepository.isDefaultAddress(userId, addressId);
//    }

    @Override
    public AddressDTO convertToAddressDTO(Address address) {
        return new AddressDTO(address.getAddressLine(), address.getCity(), address.getCountry(), address.getPostalCode(), address.getStreetName(), address.getBuildingName());
    }
}
