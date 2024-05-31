<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>SignUp Page</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/signUp.css">
</head>

<body>
	<div class="main">
		<div class="leftSide">
			<div class="leftText">
				<h1>Sonic Pulse</h1>
				<p>Feel the Rhythm, Hear the Difference</p>
			</div>
		</div>
		<div class="rightSide">
			<h5>Sign Up</h5>
			<form action="${pageContext.request.contextPath}/SignUpServlet"
				method="post" enctype="multipart/form-data">
				<div class="inputs">
					<input type="text" name="firstName" placeholder="First Name"
						required> <input type="text" name="lastName"
						placeholder="Last Name" required> <input type="text"
						name="userName" placeholder="User Name" required> <input
						type="text" name="phoneNumber" placeholder="Phone Number" required>
					<input type="text" name="address" placeholder="Address" required>
					<input type="text" name="email" placeholder="Email" required>
					<input type="password" name="password" placeholder="Password"
						required> <input type="file" name="img" accept="image/*"
						required>
				</div>
				<%-- Display error message if present --%>
				<c:if test="${not empty errorMessage}">
					<div class="error">${errorMessage}</div>
				</c:if>
				<c:if test="${not empty successMessage}">
					<div class="error">${successMessage}</div>
				</c:if>
				<br />
				<button type="submit" class="signButton">Sign Up</button>
				<br />
				<p class="rightText">
					Already have an account? <a
						href="${pageContext.request.contextPath}/pages/login.jsp">
						Sign in</a>
				</p>

			</form>
		</div>

	</div>

</body>
</html>