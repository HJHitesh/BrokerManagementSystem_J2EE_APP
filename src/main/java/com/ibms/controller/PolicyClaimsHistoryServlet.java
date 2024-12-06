package com.ibms.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.insurance.model.Claim;
import com.insurance.repository.ClaimRepository;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolicyClaimsHistoryServlet
 */
public class PolicyClaimsHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClaimRepository claimRepository;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PolicyClaimsHistoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		claimRepository = new ClaimRepository();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String policyId = req.getParameter("policyId");
		String statusFilter = req.getParameter("status");
		String startDateStr = req.getParameter("startDate");
		String endDateStr = req.getParameter("endDate");

		List<Claim> claims = (policyId == null || policyId.isEmpty()) ? claimRepository.getAllClaims()
				: claimRepository.getClaimsByPolicyId(policyId);

		if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
			Date startDate = Date.valueOf(startDateStr);
			Date endDate = Date.valueOf(endDateStr);
			claims = claimRepository.filterClaimsByDate(claims, startDate, endDate);
		}

		if (statusFilter != null && !statusFilter.isEmpty()) {
			claims = claimRepository.filterClaimsByStatus(claims, statusFilter);
		}

		req.setAttribute("claims", claims);
		req.getRequestDispatcher("/claimsHistory.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
