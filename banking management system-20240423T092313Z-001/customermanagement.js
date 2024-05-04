$(document).ready(function() {
    // Fetch all customers and render them in the table
    function fetchCustomers() {
        $.get('http://localhost:8080/api/customers/allCustomers', function(data) {
            $('#customerTable tbody').empty(); // Clear existing table rows
            data.forEach(function(customer) {
                var row = `<tr>
                    <td>${customer.customerId}</td>
                    <td>${customer.customerName}</td>
                    <td>${customer.phoneNo}</td>
                    <td>${customer.address}</td>
                    <td>${customer.userName}</td>
                    <td>${customer.email}</td>
                    <td>${customer.password}</td>
                    <td>${customer.accountNo}</td>
                    <td><button class="editBtn" data-customer-id="${customer.customerId}">Edit</button></td>
                    <td><button class="lockBtn" data-customer-id="${customer.customerId}">Lock</button></td>
                </tr>`;
                $('#customerTable tbody').append(row);
            });
        });
    }

    fetchCustomers(); // Load customers on page load

    // Handle Edit button click to open modal with customer details
    $(document).on('click', '.editBtn', function() {
        var customerId = $(this).data('customer-id');
        // Set the customer ID in the hidden input field of the modal form
        $('#editCustomerId').val(customerId);
        $.get(`http://localhost:8080/api/customers/${customerId}`, function(customer) {
            $('#editCustomerName').val(customer.customerName);
            $('#editPhoneNo').val(customer.phoneNo);
            $('#editAddress').val(customer.address);
            $('#editUserName').val(customer.userName);
            $('#editEmail').val(customer.email);
            $('#editPassword').val(customer.password);
            $('#editAccountNo').val(customer.accountNo);
            $('#editCustomerModal').modal('toggle'); // Toggle modal visibility
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error('Error fetching customer details:', errorThrown);
            // Handle error if customer details cannot be fetched
        });
    });

    // Handle Lock button click to lock/unlock customer
    $(document).on('click', '.lockBtn', function() {
        var customerId = $(this).data('customer-id');
        var userName = $(this).data('user-name'); // Retrieve userName from data attribute

        if (confirm('Are you sure you want to lock this customer?')) {
        $.ajax({
            url: `http://localhost:8080/api/customers/lock/${customerId}/${userName}`,
            type: 'PUT',
            success: function(response) {
                alert(response); // Show success message
                fetchCustomers(); // Refresh customers after locking
            },
            error: function(error) {
                alert('Error: ' + error.responseJSON.message); // Show error message
            }
        });
     }
    });

    // Handle form submission for updating customer
    $('#editCustomerForm').submit(function(e) {
        e.preventDefault();
        var customerId = $('#editCustomerId').val();
        // Prepare customer data from form inputs
        var customerData = {
            customerName: $('#editCustomerName').val(),
            phoneNo: $('#editPhoneNo').val(),
            address: $('#editAddress').val(),
            userName: $('#editUserName').val(),
            email: $('#editEmail').val(),
            password: $('#editPassword').val(),
            accountNo: $('editAccountNo').val()
        };
        $.ajax({
            url: `http://localhost:8080/api/customers/update`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(customerData),
            success: function(response) {
                alert('Customer updated successfully!');
                $('#editCustomerModal').modal('hide');
                fetchCustomers(); // Refresh customers after update
            },
            error: function(error) {
                alert('Error updating customer: ' + error.responseJSON.message);
            }
        });
    });
    // Handle close button click to close the modal
    $('.close').on('click', function() {
        $('#editCustomerModal').modal('hide'); // Hide modal
    });
});
