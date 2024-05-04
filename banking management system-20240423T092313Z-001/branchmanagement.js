$(document).ready(function() {
  // Fetch all branches and render them in the table
  function fetchBranches() {
      $.get('http://localhost:8080/api/branches/all', function(data) {
          $('#branchTable tbody').empty(); // Clear existing table rows
          data.forEach(function(branch) {
            var isActive = branch.active; // Get the 'active' status from the server

            var checkedStatus = isActive ? 'checked' : ''; // Determine if checkbox should be checked
              var row = `<tr>
                  <td>${branch.branchId}</td>
                  <td>${branch.branchName}</td>
                  <td>${branch.branchAddress}</td>
                  <td><button class="editBtn" data-branch-id="${branch.branchId}">Edit</button></td>
                  <td><button class="lockBtn" data-branch-id="${branch.branchId}">Lock</button></td>
              </tr>`;
              $('#branchTable tbody').append(row);
          });
      });
  }

  fetchBranches(); // Load branches on page load

  // Handle Edit button click to open modal with branch details
  $(document).on('click', '.editBtn', function() {
      var branchId = $(this).data('branch-id');
          // Set the branch ID in the hidden input field of the modal form
      $('#editBranchId').val(branchId);
      $.get(`http://localhost:8080/api/branches/${branchId}`, function(branch) {
          $('#editBranchName').val(branch.branchName);
          $('#editBranchAddress').val(branch.branchAddress);

         // Check if 'branch' object exists and 'active' property is not null
         if (branch && branch.active !== null) {
          // Set dropdown value based on 'active' status
          $('#editActive').val(branch.active.toString()); // Convert to string
      } else {
          // Handle case where 'active' property is null or undefined
          console.error('Active status is null or undefined.');
          // Optionally set a default value or handle the error scenario
      }
          $('#editBranchModal').modal('show');
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error('Error fetching branch details:', errorThrown);
            // Handle error if branch details cannot be fetched
        });
  });

  // Handle Lock button click to lock/unlock branch
  $(document).on('click', '.lockBtn', function() {
      var branchId = $(this).data('branch-id');
      if (confirm('Are you sure you want to lock this branch?')) {
      $.ajax({
          url: `http://localhost:8080/api/branches/lock/${branchId}`,
          type: 'PUT',
          success: function(response) {
              alert(response); // Show success message
              fetchBranches(); // Refresh branches after locking
          },
          error: function(error) {
              alert('Error: ' + error.responseJSON.message); // Show error message
          }
      });
     }
  });

  // Handle form submission for updating branch
  $('#editBranchForm').submit(function(e) {
      e.preventDefault();
      var branchId = $('#editBranchId').val();
          // Prepare branch data from form inputs
      var branchData = {
          branchName: $('#editBranchName').val(),
          branchAddress: $('#editBranchAddress').val(),
          active: $('#editActive').val() === 'true' // Convert string to boolean
        };
      $.ajax({
          url: `http://localhost:8080/api/branches/update/${branchId}`,
          type: 'PUT',
          contentType: 'application/json',
          data: JSON.stringify(branchData),
          success: function(response) {
              alert('Branch updated successfully!');
              $('#editBranchModal').modal('hide');
              fetchBranches(); // Refresh branches after update
          },
          error: function(error) {
              alert('Error updating branch: ' + error.responseJSON.message);
          }
      });
  });

});
