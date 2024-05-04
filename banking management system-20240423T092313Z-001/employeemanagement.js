$(document).ready(function() {
    // Function to fetch all employees and render them in the table
    function fetchEmployees() {
        $.get('http://localhost:8080/api/employee/all', function(employees) {
            $('#employeeTable tbody').empty(); // Clear existing table rows
            employees.forEach(function(employee) {
                var row = `<tr>
                    <td>${employee.employeeId}</td>
                    <td>${employee.employeeName}</td>
                    <td>${employee.employeeAddress}</td>
                    <td>${employee.employeePhoneNumber}</td>
                    <td>${employee.employeeEmail}</td>
                    <td><button class="editBtn" data-employee-id="${employee.employeeId}">Edit</button></td>
                    <td><button class="lockBtn" data-employee-id="${employee.employeeId}">Lock</button></td>
                </tr>`;
                $('#employeeTable tbody').append(row);
            });
        });
    }

    fetchEmployees(); // Load employees on page load

    // Handle Edit button click to open modal with employee details for editing
    $(document).on('click', '.editBtn', function() {
        var employeeId = $(this).data('employee-id');
        $.get(`http://localhost:8080/api/employee/${employeeId}`, function(employee) {
            $('#editEmployeeId').val(employee.employeeId);
            $('#editEmployeeName').val(employee.employeeName);
            $('#editEmployeeAddress').val(employee.employeeAddress);
            $('#editEmployeePhoneNumber').val(employee.employeePhoneNumber);
            $('#editEmployeeEmail').val(employee.employeeEmail);
            $('#editEmployeeModal').modal('toggle'); // Toggle modal visibility
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error('Error fetching employee details:', errorThrown);
            // Handle error if employee details cannot be fetched
        });
    });

    // Handle Lock button click to lock/unlock employee
    $(document).on('click', '.lockBtn', function() {
        var employeeId = $(this).data('employee-id');
        if (confirm('Are you sure you want to lock this employee?')) {
            $.ajax({
                url: `http://localhost:8080/api/employee/lock/${employeeId}`,
                type: 'PUT',
                success: function(response) {
                    alert(response); // Show success message
                    fetchEmployees(); // Refresh employees after locking
                },
                error: function(error) {
                    alert('Error: ' + error.responseJSON.message); // Show error message
                }
            });
        }
    });

    // Handle form submission for updating employee
    $('#editEmployeeForm').submit(function(e) {
        e.preventDefault();
        var employeeId = $('#editEmployeeId').val();
        var employeeData = {
            employeeName: $('#editEmployeeName').val(),
            employeeAddress: $('#editEmployeeAddress').val(),
            employeePhoneNumber: $('#editEmployeePhoneNumber').val(),
            employeeEmail: $('#editEmployeeEmail').val()

        };
        $.ajax({
            url: `http://localhost:8080/api/employee/update/${employeeId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(employeeData),
            success: function(response) {
                alert('Employee updated successfully!');
                $('#editEmployeeModal').modal('hide');
                fetchEmployees(); // Refresh employees after update
            },
            error: function(error) {
                alert('Error updating employee: ' + error.responseJSON.message);
            }
        });
    });

    // Additional functions for handling modal interactions, form validations, etc.
    // Example: Handle close button click to close the modal
    $('.close').on('click', function() {
        $('#editEmployeeModal').modal('hide'); // Hide modal
    });
});
