<%@page import="util.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/signIn.css">
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
	<div class="main">
		<div class="leftSide">
			<div class="leftText">
				<h1>Sonic Pulse</h1>
				<p>Feel the Rhythm, Hear the Difference</p>
			</div>
		</div>

		<div class="rightSide">
			<h5>Sign In</h5>

			<p class="rightText">
				Don't have an account? <a
					href="${pageContext.request.contextPath}/pages/signUp.jsp">Create
					Your Account</a> it takes less than a minute
			</p>
			<form action="${pageContext.request.contextPath}/LoginServlet"
				method="post">
				<div class="inputs">
					<input type="text" name="userName" placeholder="User Name" required>
					<br /> <input type="password" name="password"
						placeholder="Password" required>
				</div>
				<br />
				<button type="submit" class="signButton">Sign In</button>
			
			</form>
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
</body>
</html>