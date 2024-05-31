<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/admin.css">
<title>Add Product</title>

<style>
label {
	color: white !important;
}
</style>

</head>

<body>

	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navBrand" href="#">SonicPulse</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mx-auto mb-2 mb-lg-0 gap-5">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="${pageContext.request.contextPath}/AdminProductServlet">Products</a>
					</li>
					<li class="nav-item"><a class="nav-link " href="#">Order
							List</a></li>
				</ul>
				<form class="d-flex" role="search">
					<button class="btn" type="submit">Logout</button>
				</form>
			</div>
		</div>
	</nav>

	<div class="container mt-4 mb-5 shadow p-4 rounded-2">
		<h3>Please fill the form to add a product</h3>
		<!-- Card Based form -->
		<div class="card w-75 mx-auto bg-dark w-75-md w-100 border-0">
			<div class="card-body">
				<c:if test="${not empty errorMessage}">
					<div class="error text-danger text-center fs-5">${errorMessage}</div>
				</c:if>
				<form action="${pageContext.request.contextPath}/AddProductServlet"
					method="POST" enctype="multipart/form-data">
					<div class="form-group">
						<label for="product-name">Product Name</label> <input type="text"
							class="form-control" id="product-name" name="productName"
							placeholder="Enter product name">
					</div>
					<div class="form-group">
						<label for="product-description">Product Description</label>
						<textarea class="form-control" id="product-description"
							name="productDescription" placeholder="Enter product description"></textarea>
					</div>
					<div class="form-group">
						<label for="stock-quantity">Stock Quantity</label> <input
							type="number" class="form-control" id="stock-quantity"
							name="stockQuantity" placeholder="Enter stock quantity">
					</div>
					<div class="form-group">
						<label for="price">Price</label> <input type="number"
							class="form-control" id="price" name="price"
							placeholder="Enter price">
					</div>
					<div class="form-group">
						<label for="brand">Brand</label> <input type="text"
							class="form-control" id="brand" name="brand"
							placeholder="Enter brand">
					</div>
					<div class="form-group">
						<label for="color">Color</label> <select class="form-control"
							id="color" name="color">
							<option value="Select Color">Select Color</option>
							<option value="white">White</option>
							<option value="red">Red</option>
							<option value="yellow">Yellow</option>
							<option value="blue">Blue</option>
						</select>
					</div>

					<div class="form-group">
						<label for="connectivity">Connectivity</label> <select
							class="form-control" id="connectivity" name="connectivity">
							<option value="Select Connectivity">Select Connectivity</option>
							<option value="Wired">Wired</option>
							<option value="Wireless">Wireless</option>
						</select>
					</div>

					<div class="form-group">
						<label for="noise-cancellation">Noise Cancellation</label> <select
							class="form-control" id="noise-cancellation"
							name="noiseCancellation">
							<option value="Select Noise">Select Noise</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
						</select>
					</div>

					<div class="form-group">
						<label for="cable-length">Cable Length</label> <input type="text"
							class="form-control" id="cable-length" name="cableLength"
							placeholder="Enter cable length: 20/30/40">
					</div>
					<div class="form-group">
						<label for="driver">Driver</label> <select class="form-control"
							id="driver" name="driver">
							<option value="Select Driver">Select Driver</option>
							<option value="20MM">20MM</option>
							<option value="40MM">40MM</option>
							<option value="50MM">50MM</option>
						</select>
					</div>

					<div class="form-group">
						<label for="weight">Weight</label> <input type="text"
							class="form-control" id="weight" name="weight"
							placeholder="Enter weight: 100/150/200">
					</div>
					<div class="form-group">
						<label for="image">Image</label> <input type="file"
							class="form-control" id="image" name="productImage"
							placeholder="Choose product image">
					</div>
					<div class="btn-container d-flex gap-3">
						<button type="submit" class="btn darkbg">Submit</button>
						<!-- cancel Btn -->
						<a href="${pageContext.request.contextPath}/AdminProductServlet"
							class="btn darkbg">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>


	<footer class="footer bg-dark">
		<div class="footer-header">
			<a href="#" class="navBrand">SonicPulse</a>
		</div>
		<div class="footer-contacts d-flex gap-2 ms-auto">
			<!-- Email -->
			<div
				class="footer-item footer-email d-flex align-items-center text-white gap-2 border rounded-2 px-2 ">
				<i class="bi bi-envelope-fill"></i>
				<p class="mb-0">contact@sonicpulse.com</p>
			</div>
			<!-- Phone -->
			<div
				class="footer-item footer-phone d-flex align-items-center text-white gap-2 border rounded-2 px-2">
				<i class="bi bi-telephone-fill"></i>
				<p class="mb-0">+91 1234567890</p>
			</div>
		</div>
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>

</html>