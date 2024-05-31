<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<%
HttpSession userSession = request.getSession();
%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/cart.css">
<!-- external link of box-icons -->
<link rel="stylesheet"
	href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

<!-- external link of remix-icons -->
<link
	href="https://cdn.jsdelivr.net/npm/remixicon@4.2.0/fonts/remixicon.css"
	rel="stylesheet" />

<!-- External Links for google fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">
<title>Cart</title>
</head>
<script>
	// Function to display error message
	function showError(message) {
		alert("Error: " + message);
	}

	// Function to display success message
	function showSuccess(message) {
		alert("Success: " + message);
	}
</script>
<body>
	<!-- header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- End header -->
	<h1 class="Top-text">MY CART</h1>
	<div class="wrapper">

		<div class="container">
			<div class="cart_heading">
				<p>Product Image</p>
				<p>Product Name</p>
				<p>Quantity</p>
				<p class="cart-hide">Price</p>
				<p class="cart-hide">Subtotal</p>
				<p>Action</p>
			</div>
			<hr>
			<c:if test="${empty cartProducts}">
				<h1 style="text-align: center; color: red; margin: 25px">Nothing
					is added to the cart</h1>
			</c:if>
			<c:forEach var="cartProduct" items="${cartProducts}">
				<div class="cart-item">
					<div class="cart-image">
						<img class="small-image"
							src="${pageContext.request.contextPath}/resources/images/${cartProduct.productImage}"
							alt="">
					</div>
					<div>
						<h4>${cartProduct.productName}</h4>
					</div>
					<div class="quantity">
						<input type="text" class="quantity-value"
							value="${cartProduct.productQuantity}" max="${cartProduct.stockQuantity}" name="quantity"
							readonly />
						<button class="quantity-control" data-action="decrease">-</button>
						<button class="quantity-control" data-action="increase">+</button>
					</div>
					<p class="price">${cartProduct.productPrice}</p>
					<p class="Sub-Item-Total">Rs.</p>
					<div class="actions">
						<a class="remove_btn"
							href="${pageContext.request.contextPath}/OrderServlet?eachOrder=yes&cartId=${cartProduct.cartId}&quantity="
							onclick="var quantity = addToOrder(this); this.href += quantity; return true;">
							Place Order </a>
						<form id="deleteForm-${cartProduct.productName}"
							action="${pageContext.request.contextPath}/DeleteServlet?cartProductDelete=yes"
							method="post" class="d-flex" accept-charset="UTF-8">
							<input type="hidden" name="deleteCartId"
								value="${cartProduct.cartId}" />
							<button class="remove_btn"
								onclick="return confirmDelete('${cartProduct.productName}')">Delete</button>
						</form>
					</div>
				</div>
			</c:forEach>
			<hr>
			<div class="cart-two-button">
				<a class="btn btn-clear"
					href="${pageContext.request.contextPath}/ProductServlet">
					Continue Shopping </a>
				<button class="btn btn-clear"
					onclick="window.location.href='${pageContext.request.contextPath}/DeleteServlet?isClearCart=yes'">Clear
					Cart</button>
			</div>
		</div>
	</div>

	<script>
		// Check if error message exists and display it
		<c:if test="${not empty errorMessage}">
		showError("${errorMessage}");
		</c:if>

		// Check if success message exists and display it
		<c:if test="${not empty successMessage}">
		showSuccess("${successMessage}");
		</c:if>
	</script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/javascript/cart.js"></script>
	<script>
		function confirmDelete(productName) {
			if (confirm("Are you sure you want to delete this Product From Cart: "
					+ productName + "?")) {
				// If user clicks "Yes", submit the form
				document.getElementById("deleteForm-" + productName).submit();
			} else {
				// If user clicks "No", prevent form submission
				return false;
			}
		}
	</script>
</body>

</html>