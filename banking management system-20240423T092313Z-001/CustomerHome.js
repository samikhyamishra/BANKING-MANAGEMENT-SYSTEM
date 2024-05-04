$(document).ready(function() {
  $('#logoutBtn').click(function(e) {
      e.preventDefault(); // Prevent the default behavior of the link

// Retrieve username from localStorage
var userName = localStorage.getItem('loggedInUserName');
      var settings = {
          "url": "http://localhost:8080/api/customers/logout", 
          "method": "DELETE",
          "timeout": 0,
          "headers": {
            "Content-Type": "application/json"
          },
          "data": JSON.stringify({
            "userName":userName
          }),
      };

      $.ajax(settings)
          .done(function(response) {
              // Handle the response if needed
              console.log(response);
              alert("Logout successful!"); // Display a success message
              window.location.href = '/Welcome.html'; 
          })
          .fail(function(xhr, status, error) {
              // Display an error message if the request fails
              console.error('Error:', error);
              alert('An error occurred while processing your request. Please try again later.');
          });
  });
});

$(document).ready(function() {
  $('#checkBal').click(function(e) {
    e.preventDefault(); // Prevent the default behavior of the link

    // Retrieve the account number from localStorage or prompt the user for input
    var accountNo = prompt('Please enter your account number:');
    if (!accountNo) return; // If no account number is provided, exit

    var settings = {
      "url": "http://localhost:8080/api/accounts/checkBalance/" + accountNo,
      "method": "GET",
      "timeout": 0,
      "headers": {
        "Content-Type": "application/json"
      },
    };

    $.ajax(settings)
      .done(function(response) {
        // Handle the response
        console.log(response);
        if (response.status === 1) {
          var balance = response.post[0];
          alert("Your account balance is: " + balance);
        } else {
          alert(response.error);
        }
      })
      .fail(function(xhr, status, error) {
        console.error('Error:', error);
        alert('An error occurred while processing your request. Please try again later.');
      });
  });

  // Deposit Button Click Event
  $('#depositBtn').click(function() {
    $('#depositModal').css('display', 'block');
  });

  // Withdraw Button Click Event
  $('#withdrawBtn').click(function() {
    $('#withdrawModal').css('display', 'block');
  });

  // Close Modal Button Click Event
  $('.modal .close').click(function() {
    $(this).closest('.modal').css('display', 'none');
  });

  // Confirm Deposit Button Click Event
  $('#confirmDepositBtn').click(function() {
    var accountNo = $('#depositAccountNo').val();
    var amount = $('#depositAmount').val();

    $.ajax({
      url: `http://localhost:8080/api/accounts/deposit/${accountNo}`,
      method: 'POST',
      data: { amount: amount },
      success: function(response) {
        alert(response);
        $('#depositModal').css('display', 'none');
      },
      error: function(xhr, status, error) {
        alert('Failed to deposit amount. Please try again.');
        console.error(error);
      }
    });
  });

  // Confirm Withdraw Button Click Event
  $('#confirmWithdrawBtn').click(function() {
    var accountNo = $('#withdrawAccountNo').val();
    var amount = $('#withdrawAmount').val();

    $.ajax({
      url: `http://localhost:8080/api/accounts/withdraw/${accountNo}`,
      method: 'POST',
      data: { amount: amount },
      success: function(response) {
        alert(response);
        $('#withdrawModal').css('display', 'none');
      },
      error: function(xhr, status, error) {
        alert('Failed to withdraw amount. Please try again.');
        console.error(error);
      }
    });
  });

  // Function to render transaction history table
  function renderTransactionTable(transactions) {
    // Clear existing table content
    $('#transactionTableBody').empty();

    // Iterate through transactions and append rows to the table
    transactions.forEach(function(transaction) {
      var row = `<tr>
                  <td>${transaction.transactionId}</td>
                  <td>${transaction.transactionType}</td>
                  <td>${transaction.transactionAmount}</td>
                </tr>`;
      $('#transactionTableBody').append(row);
    });

    // Display the transaction history modal
    $('#transactionHistoryModal').css('display', 'block');
  }

  // Event listener for Transaction History button
  $('button#transactionHistoryBtn').click(function() {
    var accountNo = prompt('Please enter your account number:');
    if (!accountNo) return; // If no account number is provided, exit

    // AJAX request to fetch transactions by account number
    $.ajax({
      url: `http://localhost:8080/api/transactions/byAccountNo/${accountNo}`,
      method: 'GET',
      success: function(response) {
        if (response.length > 0) {
          renderTransactionTable(response);
        } else {
          alert('No transactions found for the provided account number.');
        }
      },
      error: function(xhr, status, error) {
        console.error('Error:', error);
        alert('Failed to fetch transaction history. Please try again.');
      }
    });
  });

  // Close Transaction History modal on close button click
  $('#closeTransactionHistoryModal').click(function() {
    $('#transactionHistoryModal').css('display', 'none');
  });
});

