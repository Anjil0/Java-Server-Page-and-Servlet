document.querySelectorAll('.quantity-control').forEach(function(button) {
	button.addEventListener('click', function() {
		var input = this.parentElement.querySelector('.quantity-value');
		var action = this.getAttribute('data-action');
		var value = parseInt(input.value);
		var stockQuantity = parseInt(input.getAttribute('max'));

		if (action === 'decrease' && value > 1) {
			input.value = value - 1;
		} else if (action === 'increase' && value < stockQuantity) {
			input.value = value + 1;
		}
	});
});

function addToCart(link) {
	// Find the parent row element of the clicked link
	var row = link.closest('.row');
	// Find the quantity input within the parent row element
	var quantityInput = row.querySelector('.quantity-value');
	var quantityValue = quantityInput.value;
	return quantityValue;
}

var button = document.getElementById("op");
// Get a reference to the target section
var targetSection = document.getElementById("productScroll");

// Add a click event listener to the button
button.addEventListener("click", function() {
	// Scroll to the target section
	targetSection.scrollIntoView({ behavior: 'auto' });
});


