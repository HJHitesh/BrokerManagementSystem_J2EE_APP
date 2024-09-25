package com.ibms.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;                         


import com.ibms.model.User;

public class UserRepository {
	
	public static final String JSON_FILE ="customers.json";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//Add the customer
	public void saveCustomer(User customer) {
		
		List<User> customers = fetchAllCustomer();
		
		//Add the new Customer
		customers.add(customer);
		
		//Write the customer to the file
		customerAddToFile(customers);
		
	}
	
	public List<User> fetchAllCustomer(){
		try {
			File file = new File(JSON_FILE);
			if(!file.exists()) {
				return new ArrayList<>();		
			}
			
			return (List<User>) objectMapper.readValue(file, new TypeReference<List<User>>(){});
			
		}catch(IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public void customerAddToFile(List<User> customers) {
		
		try {
			objectMapper.writeValue(new File(JSON_FILE),customers);
		}catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	

}                                                            
