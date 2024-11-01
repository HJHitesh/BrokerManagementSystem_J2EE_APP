package com.ibms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibms.dto.ApiResponse;
import com.ibms.exception.PolicyException;
import com.ibms.model.Policy;
import com.ibms.model.PolicyType;
import com.ibms.repository.PolicyRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet(urlPatterns = {"/api/policies/*", "/policy/*"})
public class PolicyController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PolicyController.class.getName());

    private PolicyRepository policyRepository;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
    	super.init();
        System.out.println("PolicyController initialized");
        policyRepository = new PolicyRepository();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String servletPath = request.getServletPath();

            if (servletPath.equals("/policy")) {
                // Handle page requests
                if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/manage")) {
                    request.getRequestDispatcher("/WEB-INF/views/Policy.jsp").forward(request, response);
                    return;
                }
            } else {
                // Handle API requests
                if (pathInfo == null || pathInfo.equals("/")) {
                    handleGetAllPolicies(response);
                } else {
                    String policyNumber = pathInfo.substring(1);
                    handleGetPolicy(policyNumber, response);
                }
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action != null) {
                switch (action.toLowerCase()) {
                    case "create":
                        handleCreatePolicy(request, response);
                        break;
                    case "update":
                        handleUpdatePolicy(request, response);
                        break;
                    case "delete":
                        handleDeletePolicy(request, response);
                        break;
                    default:
                        throw new PolicyException("Invalid action specified");
                }
            } else {
                // Handle JSON API requests
                Policy policy = parseRequestBody(request);
                validatePolicy(policy);
                policyRepository.savePolicy(policy);
                sendJsonResponse(response, HttpServletResponse.SC_CREATED,
                    new ApiResponse<>(true, "Policy created successfully", policy));
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void handleCreatePolicy(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Policy newPolicy = new Policy();
            newPolicy.setPolicyNumber(request.getParameter("policyNumber"));
            newPolicy.setType(PolicyType.valueOf(request.getParameter("policyType")));

            String coverageAmount = request.getParameter("coverage");
            if (!isNullOrEmpty(coverageAmount)) {
                try {
                    double coverage = Double.parseDouble(coverageAmount);
                    newPolicy.setCoverage(coverage);
                } catch (NumberFormatException e) {
                    throw new PolicyException("Invalid coverage amount: must be a valid number");
                }
            }

            String premiumAmount = request.getParameter("premium");
            if (!isNullOrEmpty(premiumAmount)) {
                try {
                    double premium = Double.parseDouble(premiumAmount);
                    newPolicy.setPremium(premium);
                } catch (NumberFormatException e) {
                    throw new PolicyException("Invalid premium amount: must be a valid number");
                }
            }

            // Set dates
            newPolicy.setStartDate(new Date()); // You might want to parse this from request
            newPolicy.setEndDate(new Date()); // You might want to parse this from request
            newPolicy.setIsActive(true);

            validatePolicy(newPolicy);
            policyRepository.savePolicy(newPolicy);

            sendJsonResponse(response, HttpServletResponse.SC_CREATED,
                new ApiResponse<>(true, "Policy created successfully", newPolicy));

        } catch (Exception e) {
            throw new PolicyException("Error creating policy: " + e.getMessage());
        }
    }

    private void handleUpdatePolicy(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String policyNumber = request.getParameter("policyNumber");
            if (isNullOrEmpty(policyNumber)) {
                throw new PolicyException("Policy number is required for update");
            }

            Policy existingPolicy = policyRepository.getPolicyByNumber(policyNumber);
            if (existingPolicy == null) {
                throw new PolicyException("Policy not found: " + policyNumber);
            }

            // Update fields if provided
            String policyType = request.getParameter("policyType");
            if (!isNullOrEmpty(policyType)) {
                existingPolicy.setType(PolicyType.valueOf(policyType));
            }

            String premiumAmount = request.getParameter("premium");
            if (!isNullOrEmpty(premiumAmount)) {
                try {
                    double premium = Double.parseDouble(premiumAmount);
                    existingPolicy.setPremium(premium);
                } catch (NumberFormatException e) {
                    throw new PolicyException("Invalid premium amount: must be a valid number");
                }
            }

            validatePolicy(existingPolicy);
            policyRepository.updatePolicy(existingPolicy);

            sendJsonResponse(response, HttpServletResponse.SC_OK,
                new ApiResponse<>(true, "Policy updated successfully", existingPolicy));

        } catch (Exception e) {
            throw new PolicyException("Error updating policy: " + e.getMessage());
        }
    }

    private void handleDeletePolicy(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String policyNumber = request.getParameter("policyNumber");
        if (isNullOrEmpty(policyNumber)) {
            throw new PolicyException("Policy number is required for deletion");
        }

        Policy policy = policyRepository.getPolicyByNumber(policyNumber);
        if (policy == null) {
            throw new PolicyException("Policy not found: " + policyNumber);
        }

        policy.setIsActive(false); // Soft delete
        policyRepository.updatePolicy(policy);

        sendJsonResponse(response, HttpServletResponse.SC_OK,
            new ApiResponse<>(true, "Policy deleted successfully", null));
    }

    private void handleGetAllPolicies(HttpServletResponse response) throws IOException {
        List<Policy> policies = policyRepository.getAllPolicies();
        sendJsonResponse(response, HttpServletResponse.SC_OK,
            new ApiResponse<>(true, "Policies retrieved successfully", policies));
    }

    private void handleGetPolicy(String policyNumber, HttpServletResponse response) throws IOException {
        Policy policy = policyRepository.getPolicyByNumber(policyNumber);
        if (policy == null) {
            throw new PolicyException("Policy not found: " + policyNumber);
        }
        sendJsonResponse(response, HttpServletResponse.SC_OK,
            new ApiResponse<>(true, "Policy retrieved successfully", policy));
    }

    private Policy parseRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return objectMapper.readValue(buffer.toString(), Policy.class);
    }

    private void validatePolicy(Policy policy) {
        if (policy == null) {
            throw new PolicyException("Policy cannot be null");
        }
        if (isNullOrEmpty(policy.getPolicyNumber())) {
            throw new PolicyException("Policy number is required");
        }
        if (policy.getType() == null) {
            throw new PolicyException("Policy type is required");
        }
        if (policy.getPremium() != null && policy.getPremium() <= 0) {
            throw new PolicyException("Premium must be greater than 0");
        }
        if (policy.getCoverage() != null && policy.getCoverage() <= 0) {
            throw new PolicyException("Coverage must be greater than 0");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void handleError(HttpServletResponse response, Exception e) throws IOException {
        LOGGER.log(Level.SEVERE, "Error processing request", e);

        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        if (e instanceof PolicyException) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        sendJsonResponse(response, status,
            new ApiResponse<>(false, e.getMessage(), null));
    }

    private void sendJsonResponse(HttpServletResponse response, int status, ApiResponse<?> body)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print(objectMapper.writeValueAsString(body));
            out.flush();
        }
    }
}