
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

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheet/footer.css">
<link rel="stylesheet" href="index.css">
<title>Document</title>

<footer class="footer">
	<div class="footer-container">
		<div class="social-icons">
			<a href="#"><i class="ion-social-instagram"></i></a> <a href="#"><i
				class="ion-social-snapchat"></i></a> <a href="#"><i
				class="ion-social-twitter"></i></a> <a href="#"><i
				class="ion-social-facebook"></i></a>
		</div>
		<nav>
			<ul class="footer-menu">
				<li><a href="${pageContext.request.contextPath}/ProductServlet">Home</a></li>
				<li><a
					href="${pageContext.request.contextPath}/pages/aboutUs.jsp">About
						Us</a></li>
				<%
				if (cookieUsername != null) {
				%>
				<li><a href="${pageContext.request.contextPath}/UserServlet">Profile</a></li>
				<%
				}
				%>
			</ul>
		</nav>
		<p class="copyright">Sonic Pulse &copy; 2024</p>
	</div>
</footer>
