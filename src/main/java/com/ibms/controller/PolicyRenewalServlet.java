package com.ibms.controller;

import java.io.IOException;
import java.util.List;

import com.insurance.model.Policy;
import com.insurance.repository.PolicyRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolicyRenewalServlet
 */
public class PolicyRenewalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyRepository policyRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PolicyRenewalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		policyRepository = new PolicyRepository();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Policy> allPolicies = policyRepository.getAllPolicies();

		req.setAttribute("allPolicies", allPolicies);

		req.getRequestDispatcher("renewPolicy.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String policyId = req.getParameter("policyId");
		int newTermLength = Integer.parseInt(req.getParameter("newTermLength"));

		try {
			Policy policy = policyRepository.findPolicyById(policyId);
			if (policy == null) {
				req.setAttribute("errorMessage", "Policy not found.");
			} else if (policy.isExpired()) {
				req.setAttribute("errorMessage", "Cannot renew expired policy.");
			} else {
				double baseRate = 50;
				double newPremium = baseRate * policy.getCoverageAmount() * (newTermLength == 1 ? 1.0 : 0.95);
				policy.setTermLength(newTermLength);
				policy.setPremium(newPremium);
				policyRepository.updatePolicy(policy);

				req.setAttribute("successMessage", "Policy renewed successfully!");
			}

			List<Policy> allPolicies = policyRepository.getAllPolicies();
			req.setAttribute("allPolicies", allPolicies);

			req.getRequestDispatcher("renewPolicy.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error processing policy renewal", e);
		}
	}

}
