package com.ibms.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibms.model.ClaimStatus;
import com.ibms.model.Customer;

@WebServlet("/report")
public class ReportingServlet extends HttpServlet {
	private PolicyRepository policyRepository;

	@Override
	public void init() throws ServletException {
		policyRepository = new PolicyRepository();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long totalPolicies = policyRepository.countPolicies();
		long approvedClaims = policyRepository.countClaimsByStatus(ClaimStatus.APPROVED);
		long rejectedClaims = policyRepository.countClaimsByStatus(ClaimStatus.REJECTED);
		Map<Customer, Long> customersPerBroker = policyRepository.countCustomersByBroker();

		request.setAttribute("totalPolicies", totalPolicies);
		request.setAttribute("approvedClaims", approvedClaims);
		request.setAttribute("rejectedClaims", rejectedClaims);
		request.setAttribute("customersPerBroker", customersPerBroker);

		request.getRequestDispatcher("/WEB-INF/views/report.jsp").forward(request, response);
	}
}
