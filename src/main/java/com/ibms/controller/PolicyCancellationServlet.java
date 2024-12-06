package com.ibms.controller;

import java.io.IOException;
import java.util.List;

import com.insurance.model.Policy;
import com.insurance.repository.PolicyRepository;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolicyCancellationServlet
 */
public class PolicyCancellationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PolicyRepository policyRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PolicyCancellationServlet() {
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
		try {
			List<Policy> allPolicies = policyRepository.getAllPolicies();

			request.setAttribute("allPolicies", allPolicies);

			request.getRequestDispatcher("/policyList.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("Error fetching policies", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String policyId = request.getParameter("policyId");
		String cancellationReason = request.getParameter("cancellationReason");
		try {
			if (policyId == null || policyId.isEmpty() || cancellationReason == null || cancellationReason.isEmpty()) {
				request.setAttribute("errorMessage", "Policy ID and cancellation reason are required.");
				doGet(request, response);
				return;
			}

			boolean isCanceled = policyRepository.cancelPolicy(policyId, cancellationReason);

			if (isCanceled) {
				request.setAttribute("successMessage", "Policy successfully canceled.");
			} else {
				request.setAttribute("errorMessage", "Error in canceling the policy.");
			}

			doGet(request, response);
		} catch (Exception e) {
			throw new ServletException("Error canceling policy", e);
		}

	}

}
