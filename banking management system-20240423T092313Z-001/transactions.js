$(document).ready(function() {
    // Function to fetch all transactions and render them in the table
    function fetchAndRenderTransactions() {
        $.ajax({
            url: 'http://localhost:8080/api/transactions/allTransactions',
            method: 'GET',
            success: function(response) {
                if (response.length > 0) {
                    renderTransactions(response);
                } else {
                    $('#transactionTableBody').html('<tr><td colspan="4">No transactions found</td></tr>');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error fetching transactions:', error);
                $('#transactionTableBody').html('<tr><td colspan="4">Error fetching transactions</td></tr>');
            }
        });
    }

    // Function to render transactions in the table
    function renderTransactions(transactions) {
        var tableBody = $('#transactionTableBody');
        tableBody.empty(); // Clear existing rows

        transactions.forEach(function(transaction) {
            var row = '<tr>' +
                        '<td>' + transaction.transactionId + '</td>' +
                        '<td>' + transaction.accountNo + '</td>' +
                        '<td>' + transaction.transactionType + '</td>' +
                        '<td>' + transaction.transactionAmount + '</td>' +
                      '</tr>';
            tableBody.append(row);
        });
    }

    // Call fetchAndRenderTransactions() when the page loads
    fetchAndRenderTransactions();
});
