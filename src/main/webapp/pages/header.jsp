<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/header.css">
<%
String cookieUsername = null;
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie cookie : cookies) {
		if (cookie.getName().equals("user"))
	cookieUsername = cookie.getValue();
	}
}
HttpSession userSession = request.getSession();
Integer notificationCount = (Integer) userSession.getAttribute("notificationCount"); // Retrieve notification count from session
if (notificationCount == null) {
	notificationCount = 0; // Initialize notification count if not found in session
}
%>
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
<header>
	<a href="${pageContext.request.contextPath}/ProductServlet"
		class="logo">SonicPulse</a>
	<ul class="menu">
		<li><a href="${pageContext.request.contextPath}/ProductServlet"
			class="homeactive">Home</a></li>
		<li><a
			href="${pageContext.request.contextPath}/pages/aboutUs.jsp"
			class="aboutUsactive">About Us</a></li>
		<%
		if (cookieUsername != null) {
		%>
		<li><a href="${pageContext.request.contextPath}/UserServlet"
			class=profileActive>Profile</a></li>
		<%
		}
		%>
	</ul>
	<form action="${pageContext.request.contextPath}/SearchServlet"
		method="POST" class="search-form">
		<input type="text" name="searchItem" placeholder="Search Product.."
			class="search-input">
		<button type="submit" class="search-btn">Search</button>
	</form>

	<div class="links">
		<%
		if (cookieUsername != null) {
			int userId = (int) userSession.getAttribute("userId");
		%>
		<a
			href="${pageContext.request.contextPath}/CartServlet?userId=${userId}"
			class=cartActive><i class="ri-shopping-cart-2-line"></i></a> <a
			href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
		<%
		} else {
		%>

		<a class="nav-link"
			href="${pageContext.request.contextPath}/pages/login.jsp">Login</a>
		<%
		}
		%>
	</div>

</header>
