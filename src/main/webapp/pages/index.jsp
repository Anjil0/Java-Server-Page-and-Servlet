<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="model.ProductModel"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Index Page</title>
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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/index.css">
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
	<jsp:include page="header.jsp"></jsp:include>
	<!-- Hero Section-->
	<section class="hero">
		<div class="hero-text">
			<h6>New Arrivals</h6>
			<h1>
				New Arrivals <br> ready to rock
			</h1>
			<p>Lorem ipsum dolor, sit amet consectetur adipisicing elit.
				Deleniti praesentium rerum, molestias labore nesciunt facilis.</p>
			<button id="op" class="btn">
				Shop Now <i class="ri-arrow-right-line"></i>
			</button>
		</div>
		<div class="hero-img">
			<!-- Image goes here -->
			<img
				src="${pageContext.request.contextPath}/resources/HomeImages.jpeg"
				alt="Top Image">
		</div>
	</section>
	<!-- feature -->
	<section class="feature">
		<p
			style="color: red; text-align: center; margin-bottom: 10px; letter-spacing: 1px;">OUR
			FEATURES</p>
		<h1>What We Provide</h1>
		<div class="cont-feature">

			<div class="item">
				<div class="icon">
					<i class="ri-e-bike-2-line"></i>
				</div>
				<div class="desc">
					<h3>Free Delivery</h3>
					<p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
						Quae totam recusandae voluptatibus quas non nu</p>
				</div>
			</div>

			<div class="item">
				<div class="icon">
					<i class="ri-customer-service-2-fill"></i>
				</div>
				<div class="desc">
					<h3>60 Days Return Policy</h3>
					<p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
						Quae totam recusandae voluptatibus quas non nu</p>
				</div>
			</div>

			<div class="item">
				<div class="icon">
					<i class="ri-bank-card-2-line"></i>
				</div>
				<div class="desc">
					<h3>Cash On Delivery</h3>
					<p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
						Quae totam recusandae voluptatibus quas non nu</p>
				</div>
			</div>

		</div>
	</section>

	<!-- ALL pRODUCTS -->
	<section id="productScroll" class="product-sec">
		<div class="center-text">
			<p
				style="color: red; text-align: center; margin-bottom: 10px; letter-spacing: 1px;">ALL
				PRODUCTS</p>
			<h1>Available Products</h1>
		</div>
		<c:if test="${empty products}">
			<p class="errorNoProduct">No products found</p>
		</c:if>
		<div class="product-content">
			<!--  Getting the data from attributes and displaying it-->
			<c:forEach var="product" items="${products}">
				<div class="row">
					<div class="row-img">
						<img
							src="${pageContext.request.contextPath}/resources/images/${product.imageUrlFromPart}"
							alt="">
					</div>
					<div class="row-content">
						<a
							href="${pageContext.request.contextPath}/SingleProduct?productId=${product.productId}"
							class="singleProduct"> ${product.productName}</a>
						<div class="price">
							<p>Rs. ${product.price}</p>
						</div>

						<div class="stock">
							<p>Available Stock: ${product.stockQuantity}</p>
						</div>
						<div class="quantity">
							<input type="text" class="quantity-value" value="1"
								max="${product.stockQuantity}" name="quantity" readonly />
							<button class="quantity-control" data-action="decrease">-</button>
							<button class="quantity-control" data-action="increase">+</button>
						</div>
						<div class="row-left">
							<a
								href="${pageContext.request.contextPath}/AddToCartServlet?productId=${product.productId}&quantity="
								onclick="var quantity = addToCart(this); this.href += quantity; return true;">
								Add to cart <i class="ri-shopping-cart-fill"></i>
							</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
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
	<!-- custom js link -->
	<script src="${pageContext.request.contextPath}/javascript/index.js"></script>
</body>

</html>
