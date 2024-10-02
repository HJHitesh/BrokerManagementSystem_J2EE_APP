package com.ibms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibms.model.Policy;
import com.ibms.repository.PolicyRepository;
import java.util.List;

/**
 * Servlet implementation class PolicyController
 */
public class PolicyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private PolicyRepository policyRepository;
	private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
    	policyRepository = new PolicyRepository(getServletContext());
        objectMapper = new ObjectMapper();
    }
    
    


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Policy> policies = policyRepository.getAllPolicies();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        objectMapper.writeValue(out, policies);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    if ("create".equals(action)) {
	        // Handle the form submission to create a new policy
	        String policyName = request.getParameter("policyName");
	        String policyType = request.getParameter("policyType");
	        double premium = Double.parseDouble(request.getParameter("premium"));

	        Policy newPolicy = new Policy(policyName, policyType, premium);
	        policyRepository.savePolicy(newPolicy); 

	        // Redirect to the dashboard after creating the policy
	        response.sendRedirect(request.getContextPath() + "/dashboard.html");
	        
	    } else if ("assign".equals(action)) {
	        // Handle the form submission to assign a policy
	        String policyName = request.getParameter("policyName");
	        String userId = request.getParameter("userId");
	        
	        response.sendRedirect(request.getContextPath() + "/dashboard.html");
	        
	    } else if ("update".equals(action)) {
	        // Handle updating policy logic
	        String policyName = request.getParameter("policyName");
	        String policyType = request.getParameter("policyType");
	        String premium = request.getParameter("premium");
	        
	        Policy newPolicy = new Policy(policyName, policyType, Double.parseDouble(premium));
	        policyRepository.updatePolicy(newPolicy);
	        
	        // Log or print the incoming parameters to debug
	        System.out.println("Updating Policy - Name: " + policyName + ", Type: " + policyType + ", Premium: " + premium);
	        
	       
	    }else if ("delete".equals(action)) {
	        String policyName = request.getParameter("name");
	        policyRepository.deletePolicy(policyName); 
	        response.sendRedirect(request.getContextPath() + "/dashboard.html"); 
	    } else {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
	    }
	}


}
