package com.ibms.controller;
import java.io.IOException;

import com.insurance.repository.PolicyRepository;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolicyQuoteServlet
 */
public class PolicyQuoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyRepository policyRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PolicyQuoteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		policyRepository = new PolicyRepository();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String customerName = req.getParameter("customerName");
		int age = Integer.parseInt(req.getParameter("age"));
		String policyType = req.getParameter("policyType");
		double coverageAmount = Double.parseDouble(req.getParameter("coverageAmount"));
		int termLength = Integer.parseInt(req.getParameter("termLength"));
		double premium = policyRepository.calculatePremium(policyType, coverageAmount, termLength);

		req.setAttribute("customerName", customerName);
		req.setAttribute("age", age);
		req.setAttribute("policyType", policyType);
		req.setAttribute("coverageAmount", coverageAmount);
		req.setAttribute("termLength", termLength);
		req.setAttribute("premium", premium);

		req.getRequestDispatcher("policyQuote.jsp").forward(req, resp);

	}

}
