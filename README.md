src/main/java/
1. Controller

CustomerServlet.java     # Handles customer-related HTTP requests

2. Service

CustomerService.java     # Business logic related to customers

3. Repository

CustomerRepository.java  # Performs CRUD operations for customers using JSON file

4. model
 
 Customer.java            # Customer entity class

resources
    └── customers.json 


Table structure

<pre> ```

+----------------+      +----------------+     +----------------+     +----------------+
|    Customer    |      |     Policy      |     |     Broker      |     |     Claim       |
+----------------+      +----------------+     +----------------+     +----------------+
| - customerId   |<>---o| - policyId      |<>---| - brokerId      |     | - claimId       |
| - name         |      | - policyType    |     | - name          |     | - claimAmount   |
| - address      |      | - premiumAmount |     | - licenseNumber |     | - claimDate     |
| - email        |      | - startDate     |     | - email         |     | - claimStatus   |
| - phoneNumber  |      | - endDate       |     | - phoneNumber   |     | - policy        |
+----------------+      | - coverageAmount|     +----------------+     +----------------+
| +applyForPolicy()     +----------------+     | +managePolicy()  |     | +submitClaim()  |
| +fileClaim()          | +calculatePremium()  | +assistCustomer()|     | +approveClaim() |
+----------------+      | +renewPolicy()      | +processClaim()   |     | +rejectClaim()  |
                        +----------------+     +----------------+     +----------------+

 ``` </pre>


 Token 

 <pre> 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</pre>
