<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claims History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Claims History</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <form action="PolicyClaimsHistoryServlet" method="get" class="mb-3">
            <div class="row">
                <div class="col-md-3">
                    <label for="policyId">Policy ID:</label>
                    <input type="text" id="policyId" name="policyId" class="form-control" placeholder="Enter Policy ID">
                </div>
                <div class="col-md-2">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate" class="form-control">
                </div>
                <div class="col-md-2">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="status">Status:</label>
                    <select name="status" id="status" class="form-control">
                        <option value="">-- All --</option>
                        <option value="Approved">Approved</option>
                        <option value="Pending">Pending</option>
                        <option value="Rejected">Rejected</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary mt-4">Filter</button>
                </div>
            </div>
        </form>
        <c:if test="${not empty claims}">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>Claim ID</th>
                        <th>Policy ID</th>
                        <th>Claim Date</th>
                        <th>Claim Amount</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="claim" items="${claims}">
                        <tr>
                            <td>${claim.claimId}</td>
                            <td>${claim.policyId}</td>
                            <td>${claim.claimDate}</td>
                            <td>${claim.claimAmount}</td>
                            <td>${claim.status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty claims}">
            <div class="alert alert-warning text-center">No claims found for the selected filters.</div>
        </c:if>
    </div>
</body>
</html>
