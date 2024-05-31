 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
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
<title>Admin Panel</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navBrand" href="#">Admin: SonicPulse</a>
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
					<li class="nav-item"><a class="nav-link "
						href="${pageContext.request.contextPath}/OrderDisplayServlet">Order
							List</a></li>
					<li class="nav-item"><a class="nav-link "
						href="${pageContext.request.contextPath}/FeedbackDisplayServlet">FeedBack Details</a></li>
				</ul>
				<form class="d-flex" role="search"
					action="${pageContext.request.contextPath}/LogoutServlet"
					method="get">
					<button class="btn" type="submit">Logout</button>
				</form>
			</div>
		</div>
	</nav>

	<div class=" main-container mx-auto bg-dark mt-4 rounded-2 text-white">
		<div
			class="table-header d-flex align-items-center justify-content-between">
			<h2>Product List</h2>
			<div class="admin-action">
				<a href="${pageContext.request.contextPath}/pages/AddProduct.jsp"
					class="btn addBtn darkbg">Add</a>
				<!-- <div class="saveBtn"></div> -->
			</div>
		</div>
		<hr>
		<div class="table-container">
			<table class="table-hover w-100">
				<thead>
					<tr>
						<th>Product ID</th>
						<th>Product Name</th>
						<th>Product Description</th>
						<th>Image</th>
						<th>Stock Quantity</th>
						<th>Price</th>
						<th>Brand</th>
						<th>Color</th>
						<th>Connectivity</th>
						<th>Noise Cancellation</th>
						<th>Cable Length</th>
						<th>Driver</th>
						<th>Weight</th>
						<th>Action</th>
					</tr>
				</thead>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.productId}</td>
						<td>${product.productName}</td>
						<td>${product.productDescription}</td>
						<td><img class="small-image"
							src="${pageContext.request.contextPath}/resources/images/${product.imageUrlFromPart}"
							alt=""></td>
						<td>${product.stockQuantity}</td>
						<td>Rs.${product.price}</td>
						<td>${product.brand}</td>
						<td>${product.color}</td>
						<td>${product.connectivity}</td>
						<td>${product.noiseCancellation}</td>
						<td>${product.cableLength}m</td>
						<td>${product.driver}</td>
						<td>${product.weight}</td>
						<td>
							<button class="btn editBtn darkbg"
								onclick="window.location.href='${pageContext.request.contextPath}/UpdateProductServlet?productId=${product.productId}'">Edit</button>
							<form id="deleteForm-${product.productName}"
								action="${pageContext.request.contextPath}/DeleteServlet?adminProductDelete=yes"
								method="post" class="d-flex" accept-charset="UTF-8">
								<input type="hidden" name="deleteProductId"
									value="${product.productId}" />
								<button class="btn deleteBtn darkbg"
									onclick="return confirmDelete('${product.productName}')">Delete</button>
							</form>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
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
	<script>
		function confirmDelete(productName) {
			if (confirm("Are you sure you want to delete this Product: "
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