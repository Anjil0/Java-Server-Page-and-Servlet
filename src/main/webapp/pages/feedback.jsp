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
<title>Order</title>
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
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="${pageContext.request.contextPath}/AdminProductServlet">Products</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/OrderDisplayServlet">Order
							List</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="${pageContext.request.contextPath}/FeedbackDisplayServlet">FeedBack
							Details</a></li>
				</ul>
				<form class="d-flex" role="search"
					action="${pageContext.request.contextPath}/LogoutServlet"
					method="get">
					<button class="btn" type="submit">Logout</button>
				</form>
			</div>
		</div>
	</nav>

	<section id="feedbacks"
		style="text-align: center; padding: 10px; margin-top: 10px; background-color: #ebe7e7; display: flex; flex-direction: column; align-items: center;">
		<h2 style="text-align: center; margin: 10px;">Feedbacks</h2>
		<table class="feedbackTable text-white">
			<thead>
				<tr>
					<th>Feedback ID</th>
					<th>Full Name</th>
					<th>Email</th>
					<th>Message</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="feedback" items="${feedbacks}">
					<tr>
						<td>${feedback.feedbackId}</td>
						<td>${feedback.fullName}</td>
						<td>${feedback.email}</td>
						<td>${feedback.message}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section>
	<!-- footerr -->
	
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