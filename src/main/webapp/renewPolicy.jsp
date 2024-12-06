<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Policy Renewal</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .expired {
            color: red;
        }
        .renewable {
            color: green;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        .success {
            color: green;
            font-weight: bold;
        }
        .table td, .table th {
            vertical-align: middle;
        }
        .form-group {
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>

    <div class="container mt-4">
        <h1 class="text-center">Policy Renewal</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>

        <h2 class="mt-4">Policies </h2>
        <form action="PolicyRenewalServlet" method="post">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
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
                            <td>
                                <c:choose>
                                    <c:when test="${policy.expired}">
                                        <span class="text-danger">Expired</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-success">Active</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${!policy.expired}">
                                    <div class="form-check">
                                        <input type="radio" class="form-check-input" name="policyId" value="${policy.id}">
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="form-group">
                <label for="newTermLength">New Term Length:</label>
                <select class="form-control" id="newTermLength" name="newTermLength">
                    <option value="1">1 Year</option>
                    <option value="5">5 Years</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Renew Policy</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
