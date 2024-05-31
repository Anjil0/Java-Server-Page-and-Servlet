// Import necessary packages
package controller.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Define this class as a filter and specify the URL pattern to intercept
@WebFilter({ "/*" })
public class AuthenticationFilter implements Filter {

	// Default constructor
	public AuthenticationFilter() {
	}

	// Method invoked by the servlet container when the filter is being destroyed
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast ServletRequest and ServletResponse to HttpServletRequest and
		// HttpServletResponse respectively
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// Get the requested URI
		String uri = req.getRequestURI();
		// Check if the requested resource is a CSS file, if so, allow it to pass
		// through the filter
		if (uri.endsWith(".css")) {
			chain.doFilter(request, response);
			return; // Return immediately after allowing CSS files to pass
		}

		if (uri.endsWith("/")) {
			res.sendRedirect(req.getContextPath() + "/ProductServlet");
			return;
		}

		// Check if the user is trying to access AdminProductServlet without being
		// logged in as admin
		if (uri.endsWith("/AdminProductServlet")) {
			HttpSession session1 = req.getSession(false);
			if (session1 == null || session1.getAttribute("Admin") == null) {
				res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
				return;
			}
		}
		
		if (!uri.endsWith("login.jsp") && !uri.endsWith("index.jsp") && !uri.endsWith("aboutUs.jsp")
				&& !uri.endsWith("signUp.jsp")) {
			// Check if the requested resource is not a login, index, aboutUs, or signUp
			// page
			if (!uri.endsWith("SignUpServlet") && !uri.endsWith("CartServlet") && !uri.endsWith("FeedbackServlet")
					&& !uri.endsWith("OrderServlet") && !uri.endsWith("searchPage.jsp")
					&& !uri.endsWith("AddProductServlet") && !uri.endsWith("ProductServlet")
					&& !uri.contains("SingleProduct") && !uri.endsWith("index.jsp") && !uri.endsWith("/")) {
				// Check if the requested resource is not related to certain servlets or pages
				if (!uri.endsWith(".png") && !uri.endsWith(".jpeg") && !uri.endsWith(".webp")
						&& !uri.endsWith(".jpg")) {
					// Check if the requested resource is not an image file
					boolean isLogin = uri.endsWith("login.jsp");
					boolean isLoginServlet = uri.endsWith("LoginServlet");
					boolean isLogoutServlet = uri.endsWith("LogoutServlet");
					// Get the current session
					HttpSession session = req.getSession(false);
					// Check if a user is logged in or if the user is an admin
					boolean isLoggedIn = session != null && session.getAttribute("user") != null;
					boolean isAdmin = session != null && session.getAttribute("Admin") != null;
					// Check if the user is an admin, if so, allow access to all resources

					if (isAdmin) {
						chain.doFilter(request, response);
					} else if ((isLoggedIn || !isLogin) && !isLoginServlet) {
						// Check if the user is logged in or if the requested resource is not a login
						// page or servlet
						if (!isLoggedIn) {
							// Redirect to the login page if the user is not logged in
							res.sendRedirect(req.getContextPath() + "/pages/login.jsp");
						} else if (isLoggedIn && isLogin && !isLogoutServlet) {
							// Redirect to the home page if the user is already logged in and tries to
							// access the login page
							res.sendRedirect(req.getContextPath() + "/pages/index.jsp");
						} else {
							// Allow access to the requested resource
							chain.doFilter(request, response);
						}
					} else {
						// Allow access to the requested resource
						chain.doFilter(req, res);
					}
				} else {
					// Allow access to the requested resource
					chain.doFilter(request, response);
				}
			} else {
				// Allow access to the requested resource
				chain.doFilter(request, response);
			}
		} else {
			// Allow access to the requested resource
			chain.doFilter(request, response);
		}
	}

	// Method invoked by the servlet container when the filter is being initialized
	public void init(FilterConfig arg0) throws ServletException {
	}
}
