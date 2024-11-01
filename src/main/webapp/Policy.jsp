<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Policy Management - IBMS</title>
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
        .policy-actions {
            display: flex;
            gap: 8px;
        }
        #createPolicyForm {
            margin-bottom: 20px;
        }
        .form-section {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <nord-layout>
        <!-- Header -->
        <nord-header slot="header">
            <h1 class="n-typescale-l">Policy Management</h1>
            <nord-button variant="primary" slot="end" id="toggleFormButton">
                <nord-icon slot="start" name="interface-add-small"></nord-icon>
                Create New Policy
            </nord-button>
        </nord-header>

        <nord-stack gap="l">
            <!-- Policy Form Card -->
            <nord-card id="policyFormCard" style="display: none;">
                <h2 slot="header" id="formTitle">Create New Policy</h2>
                <form id="policyForm">
                    <nord-stack gap="m">
                        <nord-input 
                            label="Policy Name" 
                            name="policyName" 
                            id="policyName" 
                            required>
                        </nord-input>
                        
                        <nord-select 
                            label="Policy Type" 
                            name="policyType" 
                            id="policyType" 
                            required>
                            <option value="">Select Type</option>
                            <option value="Health">Health Insurance</option>
                            <option value="Life">Life Insurance</option>
                            <option value="Vehicle">Vehicle Insurance</option>
                            <option value="Property">Property Insurance</option>
                        </nord-select>
                        
                        <nord-input 
                            type="number" 
                            label="Premium Amount" 
                            name="premium" 
                            id="premium" 
                            min="0" 
                            step="0.01" 
                            required>
                        </nord-input>
                        
                        <input type="hidden" id="formAction" value="create">
                        
                        <nord-stack direction="horizontal" gap="s">
                            <nord-button type="submit" variant="primary" id="submitButton">
                                Create Policy
                            </nord-button>
                            <nord-button type="button" variant="secondary" onclick="resetForm()">
                                Cancel
                            </nord-button>
                        </nord-stack>
                    </nord-stack>
                </form>
            </nord-card>

            <!-- Policies Table Card -->
            <nord-card>
                <h2 slot="header">All Policies</h2>
                <table id="policyTable" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Policy Name</th>
                            <th>Policy Type</th>
                            <th>Premium</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="policyList">
                    </tbody>
                </table>
            </nord-card>
        </nord-stack>
    </nord-layout>

    <script>
        $(document).ready(function() {
            loadPolicies();
            
            // Toggle form visibility
            $('#toggleFormButton').click(function() {
                const formCard = $('#policyFormCard');
                formCard.slideToggle();
                resetForm();
            });
            
            // Form submission
            $('#policyForm').submit(function(e) {
                e.preventDefault();
                const formData = {
                    policyName: $('#policyName').val(),
                    policyType: $('#policyType').val(),
                    premium: $('#premium').val(),
                    action: $('#formAction').val()
                };
                
                $.ajax({
                    url: 'PolicyController',
                    method: 'POST',
                    data: formData,
                    success: function(response) {
                        showNotification(response.message, 'success');
                        loadPolicies();
                        resetForm();
                        $('#policyFormCard').hide();
                    },
                    error: function(xhr) {
                        showNotification(xhr.responseJSON?.message || 'Error processing request', 'error');
                    }
                });
            });
        });

        function loadPolicies() {
            $.ajax({
                url: 'PolicyController?action=view',
                method: 'GET',
                success: function(data) {
                    if ($('#policyTable').DataTable()) {
                        $('#policyTable').DataTable().destroy();
                    }

                    const policyList = $('#policyList');
                    policyList.empty();

                    data.forEach(policy => {
                        policyList.append(`
                            <tr>
                                <td>${escapeHtml(policy.policyName)}</td>
                                <td>${escapeHtml(policy.policyType)}</td>
                                <td>$${parseFloat(policy.premium).toFixed(2)}</td>
                                <td class="policy-actions">
                                    <nord-button variant="secondary" onclick="editPolicy('${escapeHtml(policy.policyName)}', '${escapeHtml(policy.policyType)}', ${policy.premium})">
                                        Edit
                                    </nord-button>
                                    <nord-button variant="danger" onclick="deletePolicy('${escapeHtml(policy.policyName)}')">
                                        Delete
                                    </nord-button>
                                </td>
                            </tr>
                        `);
                    });

                    $('#policyTable').DataTable({
                        responsive: true,
                        pageLength: 10,
                        language: {
                            search: "Search policies:",
                            emptyTable: "No policies available"
                        }
                    });
                },
                error: function(xhr) {
                    showNotification('Error loading policies', 'error');
                }
            });
        }

        function editPolicy(name, type, premium) {
            $('#formTitle').text('Update Policy');
            $('#policyName').val(name);
            $('#policyType').val(type);
            $('#premium').val(premium);
            $('#formAction').val('update');
            $('#submitButton').text('Update Policy');
            $('#policyName').prop('readonly', true);
            $('#policyFormCard').slideDown();
        }

        function deletePolicy(policyName) {
            if (confirm('Are you sure you want to delete this policy?')) {
                $.ajax({
                    url: 'PolicyController',
                    method: 'POST',
                    data: {
                        action: 'delete',
                        policyName: policyName
                    },
                    success: function(response) {
                        showNotification('Policy deleted successfully', 'success');
                        loadPolicies();
                    },
                    error: function(xhr) {
                        showNotification('Error deleting policy', 'error');
                    }
                });
            }
        }

        function resetForm() {
            $('#formTitle').text('Create New Policy');
            $('#policyForm')[0].reset();
            $('#formAction').val('create');
            $('#submitButton').text('Create Policy');
            $('#policyName').prop('readonly', false);
        }

        function showNotification(message, type) {
            const notification = $(`<nord-banner variant="${type === 'success' ? 'success' : 'error'}">${message}</nord-banner>`);
            $('body').append(notification);
            setTimeout(() => notification.remove(), 3000);
        }

        function escapeHtml(unsafe) {
            return unsafe
                .replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#039;");
        }
    </script>
</body>
</html>