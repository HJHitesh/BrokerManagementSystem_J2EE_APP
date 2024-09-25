package com.ibms.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import com.ibms.model.User;
import com.ibms.service.UserService;

/**
 * Servlet implementation class CustomerServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService customerService = new UserService();
	
	public static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());
       
 
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //@TODO based on the type of registration we change the type as { CUSTOMER ,BROKER ,ADMIN }
        User customer = new User(UUID.randomUUID().getLeastSignificantBits(), name, email, password, "Customer", "Active");
        customerService.addCustomer(customer);  // Save to JSON file

        response.getWriter().write("Customer registered successfully!");
		doGet(request, response);
	}

}
