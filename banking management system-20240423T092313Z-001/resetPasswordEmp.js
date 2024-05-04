$(document).ready(function() {
    $('#resetPasswordForm').submit(function(e) {
        e.preventDefault();

        var token = $('#token').val();
        var newPassword = $('#newPassword').val();

        $.ajax({
            url: 'http://localhost:8080/api/employee/resetPassword',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                token: token,
                newPassword: newPassword
            }),
            success: function(response) {
                alert('Password reset successfully.');
                window.location.href = '/Employee_login.html'; // Redirect to login page
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('Failed to reset password. Please try again later.');
            }
        });
    });
});
