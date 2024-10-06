/**
 * 
 */
package com.ibms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibms.model.Customer;
import com.ibms.repository.CustomerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 * 
 */
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CustomerRepository customerRepository;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		customerRepository = new CustomerRepository(getServletContext());
		objectMapper = new ObjectMapper();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Customer> customers = customerRepository.getAllCustomers();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		objectMapper.writeValue(out, customers);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("add".equals(action)) {
			String id = UUID.randomUUID().toString();
			String name = request.getParameter("username");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			Customer newCustomer = new Customer(id, name, email, phone);
			customerRepository.saveCustomer(newCustomer);

			response.sendRedirect(request.getContextPath() + "/dashboard.html");
		} else if ("delete".equals(request.getParameter("action"))) {
			String userId = request.getParameter("userId");
			boolean deletionSuccess = customerRepository.deleteUserById(userId);
			response.setContentType("application/json");
			response.getWriter().write("{\"success\": " + deletionSuccess + "}");
		} else if ("edit".equals(action)) {
			String userId = request.getParameter("userId");
			String name = request.getParameter("username");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			Customer updatedCustomer = new Customer(userId, name, email, phone);
			customerRepository.updateCustomer(updatedCustomer);

			response.sendRedirect(request.getContextPath() + "/dashboard.html");
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
		}
	}
}
