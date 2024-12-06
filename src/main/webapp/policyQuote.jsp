<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Policy Quote</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="card shadow-lg p-4">
            <h1 class="text-center mb-4">Policy Quote</h1>
            <table class="table table-bordered">
                <tbody>
                    <tr>
                        <th scope="row">Customer Name</th>
                        <td>${customerName}</td>
                    </tr>
                    <tr>
                        <th scope="row">Age</th>
                        <td>${age}</td>
                    </tr>
                    <tr>
                        <th scope="row">Policy Type</th>
                        <td>${policyType}</td>
                    </tr>
                    <tr>
                        <th scope="row">Coverage Amount</th>
                        <td>$${coverageAmount}</td>
                    </tr>
                    <tr>
                        <th scope="row">Term Length</th>
                        <td>${termLength} years</td>
                    </tr>
                    <tr class="table-primary">
                        <th scope="row">Premium</th>
                        <td><strong>$${premium}</strong></td>
                    </tr>
                </tbody>
            </table>
            <div class="text-center mt-4">
                <a href="policyForm.jsp" class="btn btn-primary">Get Another Quote</a>
            </div>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
