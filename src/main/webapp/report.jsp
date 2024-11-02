<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Insurance Broker Report</title>
    <link rel="stylesheet" href="https://nordcdn.net/ds/css/3.3.1/nord.min.css">
    <link rel="stylesheet" href="https://nordcdn.net/ds/themes/8.1.1/nord-dark.css">
    <script type="module" src="https://nordcdn.net/ds/components/3.18.2/index.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <style>
        table.dataTable thead th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        table.dataTable tbody tr {
            background-color: #f2f2f2;
        }
        table.dataTable tbody tr td {
            color: #333;
        }
        table.dataTable tbody tr:hover {
            background-color: #ddd;
        }
        .form-section {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
 <nord-layout>
  <nord-header slot="header">
            <h1 class="n-typescale-l">Insurance Broker Management Report</h1>
          
        </nord-header>

        <nord-stack gap="l">

            <nord-card>
                <h2 slot="header">Customers Managed by Each Broker</h2>
                <table id="brokerTable" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Broker</th>
                            <th>Number of Customers</th>                            
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${customersPerBroker}">
           				 <tr>
                			<td>${entry.key.firstName} ${entry.key.lastName}</td>
                			<td>${entry.value}</td>
            			</tr>
        			</c:forEach>
                    </tbody>
                </table>
            </nord-card>
            
                        <nord-card>
                <h2 slot="header">Policy and Claim Summary</h2>
                <p>Total Policies: ${totalPolicies}</p>
    			<p>Approved Claims: ${approvedClaims}</p>
    			<p>Rejected Claims: ${rejectedClaims}</p>
    			<p>Approval Rate: <c:out value="${approvedClaims * 100 / (approvedClaims + rejectedClaims)}"/>%</p>
    			<p>Rejection Rate: <c:out value="${rejectedClaims * 100 / (approvedClaims + rejectedClaims)}"/>%</p>
                </table>
            </nord-card>
        </nord-stack>
 
 		</nord-layout>
    </body>
</html>
