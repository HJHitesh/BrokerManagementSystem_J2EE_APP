<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Policy Cancellation</title>
    <style>
        .error {
            color: red;
            font-weight: bold;
        }
        .success {
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Policy Cancellation</h1>

    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p class="success">${successMessage}</p>
    </c:if>

    <h2>Policies (Active & Expired)</h2>
    <form action="PolicyCancelServlet" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer Name</th>
                    <th>Policy Type</th>
                    <th>Coverage</th>
                    <th>Term Length</th>
                    <th>Premium</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="policy" items="${allPolicies}">
                    <tr class="${policy.expired ? 'expired' : 'renewable'}">
                        <td>${policy.id}</td>
                        <td>${policy.customerName}</td>
                        <td>${policy.policyType}</td>
                        <td>${policy.coverageAmount}</td>
                        <td>${policy.termLength}</td>
                        <td>${policy.premium}</td>
                        <td>${policy.expired ? 'Expired' : 'Active'}</td>
                        <td>
                            <c:if test="${!policy.expired && !policy.canceled}">
                                <input type="radio" name="policyId" value="${policy.id}">
                                <input type="text" name="cancellationReason" placeholder="Reason for cancellation">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <button type="submit">Cancel Policy</button>
    </form>
</body>
</html>
