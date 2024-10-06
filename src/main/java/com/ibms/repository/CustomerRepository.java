package com.ibms.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibms.model.Customer;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerRepository {
    private static final Logger LOGGER = Logger.getLogger(CustomerRepository.class.getName());

    private final ObjectMapper objectMapper;
    //private static final String FILE_PATH = "C:\\Users\\neelk\\eclipse-workspace\\BrokerManagementApp\\src\\main\\webapp\\customers.json";  // Replace with your actual file path

    private final String FILE_PATH;
    
    public CustomerRepository(ServletContext context) {
        this.objectMapper = new ObjectMapper();
        this.FILE_PATH = context.getRealPath("/customers.json");
        
    }

    public List<Customer> getAllCustomers() {
        try {
            return objectMapper.readValue(new File(FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); 
        }
    }

    public void saveCustomer(Customer customer) {
        List<Customer> customers = getAllCustomers();
        LOGGER.info("Current Customers: " + customers);

        customers.add(customer);
        try {
            objectMapper.writeValue(new File(FILE_PATH), customers);
            LOGGER.info("Customers saved to file: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean deleteUserById(String userId) {
        List<Customer> customers = getAllCustomers();
        boolean removed = customers.removeIf(customer -> customer.getId().equals(userId));

        if (removed) {
            try {               
                objectMapper.writeValue(new File(FILE_PATH), customers);
                LOGGER.info("Customer with ID " + userId + " deleted successfully.");
                return true;
            } catch (IOException e) {
                LOGGER.severe("Error saving customers after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            LOGGER.warning("Customer with ID " + userId + " not found.");
        }
        return false;
    }
    
    public Customer getCustomerById(String userId) {
        List<Customer> customers = getAllCustomers();
        return customers.stream()
            .filter(customer -> customer.getId().equals(userId))
            .findFirst()
            .orElse(null); 
    }

    public void updateCustomer(Customer updatedCustomer) {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        try {
            objectMapper.writeValue(new File(FILE_PATH), customers);
            LOGGER.info("Customer updated successfully.");
        } catch (IOException e) {
            LOGGER.severe("Error saving customers after update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
