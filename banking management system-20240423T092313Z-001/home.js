// // Get the links for Customer Login and Admin Login
// var customerLoginLink = document.getElementById("customerLogin");
// var adminLoginLink = document.getElementById("adminLogin");

// // Add event listeners to handle click events
// customerLoginLink.addEventListener("click", function(event) {
//     event.preventDefault(); // Prevent the default action of the link
//     // Your code to handle Customer Login click event
//     alert("Customer Login clicked!"); // Example action (replace with your logic)
// });

// adminLoginLink.addEventListener("click", function(event) {
//     event.preventDefault(); // Prevent the default action of the link
//     // Your code to handle Admin Login click event
//     alert("Admin Login clicked!"); // Example action (replace with your logic)
// });


let slideIndex = 0;
showSlides();

function showSlides() {
    let i;
    const slides = document.getElementsByClassName("mySlides");
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1
    }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(showSlides, 2000); // Change image every 2 seconds
}
