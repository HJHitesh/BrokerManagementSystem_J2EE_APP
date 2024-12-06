<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Get Policy Quote</title>
  
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Policy Quote Form</h1>
        <div class="card shadow-lg p-4">
            <form action="getPolicyQuote" method="post">
                <div class="mb-3">
                    <label for="customerName" class="form-label">Customer Name</label>
                    <input type="text" class="form-control" id="customerName" name="customerName" placeholder="Enter your name" required>
                </div>

                <div class="mb-3">
                    <label for="age" class="form-label">Age</label>
                    <input type="number" class="form-control" id="age" name="age" min="18" placeholder="Enter your age" required>
                </div>

                <div class="mb-3">
                    <label for="policyType" class="form-label">Policy Type</label>
                    <select class="form-select" id="policyType" name="policyType" required>
                        <option value="" selected disabled>Select policy type</option>
                        <option value="Health">Health</option>
                        <option value="Life">Life</option>
                        <option value="Auto">Auto</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="coverageAmount" class="form-label">Coverage Amount</label>
                    <input type="number" class="form-control" id="coverageAmount" name="coverageAmount" min="1000" placeholder="Enter coverage amount" required>
                </div>

                <div class="mb-4">
                    <label for="termLength" class="form-label">Term Length (years)</label>
                    <select class="form-select" id="termLength" name="termLength" required>
                        <option value="" selected disabled>Select term length</option>
                        <option value="1">1 Year</option>
                        <option value="5">5 Years</option>
                        <option value="10">10 Years</option>
                    </select>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-lg">Get Quote</button>
                </div>
            </form>
        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
