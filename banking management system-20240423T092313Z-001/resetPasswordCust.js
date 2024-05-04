$(document).ready(function() {
    $('#resetPasswordForm').submit(function(e) {
        e.preventDefault();

        var token = $('#token').val();
        var newPassword = $('#newPassword').val();

        $.ajax({
            url: 'http://localhost:8080/api/customers/resetPassword',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                token: token,
                newPassword: newPassword
            }),
            success: function(response) {
                alert('Password reset successfully.');
                window.location.href = '/login_customer.html'; // Redirect to login page
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('Failed to reset password. Please try again later.');
            }
        });
    });
});
