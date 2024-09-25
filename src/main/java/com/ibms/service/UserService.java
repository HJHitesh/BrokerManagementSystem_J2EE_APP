package com.ibms.service;

import com.ibms.model.User;
import com.ibms.repository.UserRepository;

public class UserService {

	private UserRepository customerRepository = new UserRepository();

	// Add New Customer
	public void addCustomer(User customer) {

		// Calling Customer to add the customer
		this.customerRepository.saveCustomer(customer);
	}
	// View Customer

	// Update Customer

	// Delete Customer

}
