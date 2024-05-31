document.addEventListener("DOMContentLoaded", function() {
	// Selecting the required elements
	const cartItems = document.querySelectorAll('.cart-item');
	// Function to update subtotal, shipping fee, and order total
	let subtotal = 0;
	function updateTotals() {
		subtotal = 0; // Reset subtotal before recalculating
		cartItems.forEach(cartItem => {
			const quantity = parseInt(cartItem.querySelector('.quantity-value').value); // Selecting the quantity using .quantity-value class   
			const priceElement = cartItem.querySelector('.price'); // Selecting the price using .price class   
			const price = priceElement.textContent;
			const priceOnly = parseFloat(price); // Extracting the numerical value from the price
			const subItemTotalElement = cartItem.querySelector('.Sub-Item-Total'); // Selecting the Sub-Item-Total element
			const subItemTotal = quantity * priceOnly; // Calculating the subtotal for the item
			subtotal += subItemTotal; // Adding the subtotal for this item to the overall subtotal
			subItemTotalElement.textContent = 'Rs. ' + subItemTotal.toFixed(2); // Updating the Sub-Item-Total element for this item
		});
	}

	document.querySelectorAll('.quantity-control').forEach(function(button) {
		button.addEventListener('click', function() {
			var input = this.parentElement.querySelector('.quantity-value');
			var action = this.getAttribute('data-action');
			var value = parseInt(input.value);
			var stockQuantity = parseInt(input.getAttribute('max'));
			if (value > stockQuantity) {
				input.value = stockQuantity;
			}
			
			else if (action === 'decrease' && value > 1) {
				input.value = value - 1;
				updateTotals(); // Update totals after quantity change
			} else if (action === 'increase' && value < stockQuantity) {
				input.value = value + 1;
				updateTotals(); // Update totals after quantity change
			}
		});
	});
	// Initial update of totals
	updateTotals();
});

function addToOrder(link) {
	// Find the parent form element of the clicked button
	var sel = link.closest('.cart-item');
	// Find the quantity input within the parent form element
	var quantityInput = sel.querySelector('.quantity-value');
	// Get the value of the quantity input
	var quantityValue = quantityInput.value;
	// Append the quantity value to the action attribute of the form
	return quantityValue;

}

