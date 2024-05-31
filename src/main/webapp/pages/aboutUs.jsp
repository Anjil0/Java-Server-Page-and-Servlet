<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
	href="${pageContext.request.contextPath}/stylesheet/aboutUs.css">
<title>SONIC</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="wholebody">
		<section class="about">
			<div class="imgh">
				<img
					src="${pageContext.request.contextPath}/resources/aboutUsPic2.webp"
					alt="mission">
			</div>
			<div class="cont">
				<h1 style="text-align: center;">ABOUT US</h1>
				<p style="text-align: justify;">At SONICPULSE, we're all about
					top-notch headphones that sound amazing, look great, and feel
					comfortable to wear for hours on end. Whether you're into music or
					gaming, we've got you covered with headphones that deliver superb
					sound quality, cool designs, and unparalleled comfort. Our mission
					is simple: to bring you the best audio experience possible, without
					any hassle. We're constantly on the lookout for the latest
					technology to ensure our headphones not only sound great but also
					feel comfortable and easy to use. So, if you're ready to take your
					listening experience to the next level, come join us at SONICPULSE.
					Get ready to hear your favorite tunes like never before, all while
					enjoying the comfort and ease of our headphones!</p>

				<h1 style="text-align: left;">CONTACT US</h1>
				<p style="text-align: left;">
					Via phone number: <a href="tel:98313013183">98313013183</a>
				</p>
				<p style="text-align: left;">
					Via email: <a href="mailto:adadada@control.com">adadada@control.com</a>
				</p>
			</div>
		</section>


		<main class="pg2">

			<section class="services">
				<h2>OUR SERVICES</h2>
				<div class="services__container">
					<div class="service">
						<h3>Top-Quality Headphones</h3>
						<p>Discover our premium headphones known for great sound,
							stylish looks, and comfy fit.</p>
					</div>
					<div class="service">
						<h3>Easy Shopping</h3>
						<p>Enjoy hassle-free shopping on our user-friendly website,
							backed by helpful customer support.</p>
					</div>
					<div class="service">
						<h3>Exceptional Service</h3>
						<p>Experience top-notch service with fast shipping, easy
							returns, and dedicated support.</p>
					</div>
				</div>
			</section>

			<section class="aboutus">

				<div class="form-container">
					<form action="${pageContext.request.contextPath}/FeedbackServlet"
						method="POST">
						<h2>Send Us Message</h2>
						<%
						if (cookieUsername == null) {
						%>
						<div class="form-group">
							<label for="firstName">Tell Us Your Name</label> <input
								type="text" id="firstName" name="fullName"
								placeholder="Full name" required>
						</div>
						<div class="form-group">
							<label for="email">Enter Your Email</label> <input type="email"
								id="email" name="email" placeholder="Eg. example@email.com"
								required>
						</div>
						<%
						}
						%>
						<div class="form-group">
							<label for="message">Message</label>
							<textarea id="message" name="message" rows="6"
								placeholder="Write us a message..." required></textarea>
						</div>
						<button class="buttonABoutUs" type="submit">Send Message</button>
					</form>
				</div>
				<div class="image-container">
					<img src="../resources/aboutUsPic.jpg" alt="About Us Image">
				</div>
			</section>
		</main>
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
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
