<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
<title>Profile</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/profile.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="profileBody">
		<div class="hidden-form" id="hiddenForm">
			<form
				action="${pageContext.request.contextPath}/UpdateProfileServlet"
				method="post" enctype="multipart/form-data">
				<div class="profile-form">
					<input type="hidden" id="userId" name="userId"
						value="${userDetails.userId}">
					<div>
						<label for="firstName">First Name:</label> <input type="text"
							id="firstName" name="firstName" value="${userDetails.firstName}"
							required>
					</div>
					<div>
						<label for="lastName">Last Name:</label> <input type="text"
							id="lastName" name="lastName" value="${userDetails.lastName}"
							required>
					</div>
					<div>
						<label for="phoneNumber">Phone Number:</label> <input type="text"
							id="phoneNumber" name="phoneNumber"
							value="${userDetails.phoneNumber}" required>
					</div>
					<div>
						<label for="address">Address:</label> <input type="text"
							id="address" name="address" value="${userDetails.address}"
							required>
					</div>
					<div>
						<label for="userName">Username:</label> <input type="text"
							id="userName" name="userName" value="${userDetails.userName}"
							required>
					</div>
					<div>
						<label for="image">Image:</label> <input type="file" name="img"
							accept="image/*" required>
					</div>
				</div>
				<div class="editbuttons">
					<button type="submit">Update Profile</button>
					<button
						onclick="window.location.href='${pageContext.request.contextPath}/UserServlet'">Back</button>
				</div>
			</form>

		</div>
		<div class="profile-container">
			<div class="profile-details">
				<c:if test="${not empty successMessage || not empty errorMessage}">
					<h1 style="color: red;">${successMessage}${errorMessage}</h1>
				</c:if>
				<c:if test="${not empty userDetails}">
					<h2 class="topText">User Profile</h2>
					<img
						src="${pageContext.request.contextPath}/resources/images/${userDetails.imageUrlFromPart}"
						alt="${userDetails.userName} image not Available"
						class="profile-image" />
					<table>
						<tr>
							<th>Full Name:</th>
							<td>${userDetails.firstName}${userDetails.lastName}</td>
						</tr>
						<tr>
							<th>Email:</th>
							<td>${userDetails.email}</td>
						</tr>
						<tr>
							<th>Phone Number:</th>
							<td>${userDetails.phoneNumber}</td>
						</tr>
						<tr>
							<th>Address:</th>
							<td>${userDetails.address}</td>
						</tr>
						<tr>
							<th>Username:</th>
							<td>${userDetails.userName}</td>
						</tr>
					</table>
					<button id="edit" onclick="toggleHiddenForm()"
						class="edit-profile-btn">Edit Profile</button>
				</c:if>
				<c:if test="${empty userDetails}">
					<p>User not logged in.</p>
				</c:if>
			</div>
		</div>
		<div class="orderContainer">
			<div class="orderList">
				<h2>Order History</h2>
				<table>
					<thead>
						<tr>
							<th>Product Name</th>
							<th>Order Date</th>
							<th>Quantity</th>
							<th>Price</th>
							<th>Total Price</th>
							<th>Order Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orderDetails}">
							<tr>
								<td>${order.productName}</td>
								<td>${order.orderDate}</td>
								<td>${order.quantity}</td>
								<td>$${order.productPrice}</td>
								<td>$${order.totalPrice}</td>
								<td>${order.orderStatus}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>

	<script>
		function toggleHiddenForm() {
			var hiddenForm = document.querySelector(".hidden-form");
			hiddenForm.classList.toggle("show"); // Toggle the "show" class

			// Toggle the modal-open class on the body to prevent scrolling
			document.body.classList.toggle("modal-open");
		}
	</script>

</body>

</html>