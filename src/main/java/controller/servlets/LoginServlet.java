package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.UserModel;
import util.StringUtils;

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.LOGIN_SERVLET)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int SESSION_TIMEOUT_SECONDS = 60 * 60;
	private DatabaseController dbController = new DatabaseController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect to login page
		resp.sendRedirect(req.getContextPath() + StringUtils.LOGIN_PAGE);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter(StringUtils.USER_NAME);
		String password = request.getParameter(StringUtils.PASSWORD);

		try {
			int loginResult = dbController.getUserLoginInfo(userName, password);

			if (loginResult == 1) {
				// User login successful
				UserModel user = dbController.verifyUser(userName);

				HttpSession session = request.getSession(true);
				session.setAttribute("user", userName);
				session.setAttribute("userId", user.getUserId());
				session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

				Cookie userCookie = new Cookie("user", userName);
				userCookie.setMaxAge(SESSION_TIMEOUT_SECONDS);
				userCookie.setHttpOnly(true);
				userCookie.setSecure(true);
				response.addCookie(userCookie);

				// Redirect to user's page
				response.sendRedirect(request.getContextPath() + StringUtils.PRODUCT_SERVLET);
			} else if (loginResult == 0) {
				// Admin login successful
				HttpSession session = request.getSession(true);
				session.setAttribute("Admin", userName); // admin Login
				session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

				Cookie adminCookie = new Cookie("Admin", userName);
				adminCookie.setMaxAge(SESSION_TIMEOUT_SECONDS);
				adminCookie.setHttpOnly(true);
				adminCookie.setSecure(true);
				response.addCookie(adminCookie);

				// Redirect to admin page
				response.sendRedirect(request.getContextPath() + StringUtils.ADMIN_PRODUCT_SERVLET);
			} else if (loginResult == 2 || loginResult == 3) {
				// Incorrect credentials or no user found errors
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_LOGIN_MESSAGE);
				request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
			} else {
				// Other server errors
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
			}
		} catch (Exception e) {
			// Handle exceptions
			e.printStackTrace(); // Better to log the exception
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
		}

	}
}
