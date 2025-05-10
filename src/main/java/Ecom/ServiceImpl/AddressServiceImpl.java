package Ecom.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Ecom.Exception.AddressException;
import Ecom.Exception.UserException;
import Ecom.Model.Address;
import Ecom.Model.User;
import Ecom.Repository.AddressRepository;
import Ecom.Repository.UserRepository;
import Ecom.Service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // Constructor for dependency injection
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Address addAddressToUser(Integer userId, Address address) throws AddressException {
        // Fetch the user
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found"));

        // Set the user to the address before saving
        address.setUser(existingUser);
        
        // Save the address
        Address savedAddress = addressRepository.save(address);

        // Add the saved address to the user's address list
        if (existingUser.getAddress() == null) {
            existingUser.setAddress(new ArrayList<>());
        }
        existingUser.getAddress().add(savedAddress);

        // Save the user with the new address
        userRepository.save(existingUser);

        return savedAddress;
    }

    @Override
    public Address updateAddress(Address address, Integer addressId) throws AddressException {
        // Find the existing address by ID
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        // Update the address fields
        existingAddress.setFlatNo(address.getFlatNo());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());

        // Save the updated address and return it
        return addressRepository.save(existingAddress);
    }

    @Override
    public void removeAddress(Integer addressId) throws AddressException {
        // Find the address by ID
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException("Address not found"));

        // Delete the address
        addressRepository.delete(existingAddress);
    }

    @Override
    public List<Address> getAllUserAddress(Integer userId) throws AddressException {
        // Retrieve the list of addresses for the user
        List<Address> userAddressList = addressRepository.getUserAddressList(userId);

        // If no addresses are found, throw an exception
        if (userAddressList == null || userAddressList.isEmpty()) {
            log.warn("No addresses found for userId: {}", userId);
            throw new AddressException("User does not have any address");
        }

        return userAddressList;
    }
}
