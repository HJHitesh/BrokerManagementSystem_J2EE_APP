package com.ibms.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibms.model.Policy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletContext;

public class PolicyRepository {
	
	private static final Logger LOGGER = Logger.getLogger(PolicyRepository.class.getName());

	
    private final ObjectMapper objectMapper;
    
    private final String FILE_PATH;
    
    public PolicyRepository(ServletContext context) {
        this.objectMapper = new ObjectMapper();
        this.FILE_PATH = context.getRealPath("/policies.json");
        
    }

    public List<Policy> getAllPolicies() {
    	try {
            // Read the JSON file into a list of Policy objects
            return objectMapper.readValue(new File(FILE_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Policy.class));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if there are errors
        }
    }

    public void savePolicy(Policy policy) {
    	List<Policy> policies = getAllPolicies();
        System.out.println("Current Policies: " + policies); 
        
        LOGGER.info("Current Policies: " + policies);
        // Debugging line
        policies.add(policy);
        System.out.println("Adding Policy: " + policy); // Debugging line
        try {
            objectMapper.writeValue(new File(FILE_PATH), policies);
            System.out.println("Policies saved to file: " + FILE_PATH); // Debugging line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePolicy(Policy updatedPolicy) {
		List<Policy> policies = getAllPolicies();
		boolean policyFound = false;

		for (int i = 0; i < policies.size(); i++) {
			Policy policy = policies.get(i);
			if (policy.getPolicyName().equals(updatedPolicy.getPolicyName())) {
				policies.set(i, updatedPolicy); 
				policyFound = true;
				System.out.println("Updated Policy: " + updatedPolicy);
				break;
			}
		}

		if (policyFound) {
			try {
				objectMapper.writeValue(new File(FILE_PATH), policies);
				System.out.println("Policies updated and saved to file: " + FILE_PATH); // Debugging line
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Policy with ID " + updatedPolicy.getPolicyName() + " not found."); // Debugging line
		}
	}
    
    public void deletePolicy(String policyName) {
        List<Policy> policies = getAllPolicies();
        boolean policyFound = false;

        for (int i = 0; i < policies.size(); i++) {
            Policy policy = policies.get(i);
            if (policy.getPolicyName().equals(policyName)) { 
                policies.remove(i);
                policyFound = true;
                System.out.println("Deleted Policy with Name: " + policyName); 
                break;
            }
        }

        if (policyFound) {
            try {
                objectMapper.writeValue(new File(FILE_PATH), policies);
                System.out.println("Policies updated and saved to file: " + FILE_PATH); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Policy with ID " + policyName + " not found."); 
        }
    }

}

