package controller.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import util.StringUtils;

/**
 * Servlet implementation class FeedbackServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.FEEDBACK_SERVLET })
public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private boolean isValidEmail(String email) {
		// Regex pattern for Gmail email validation
		String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String message = request.getParameter("message");
		Integer userIdInteger = (Integer) session.getAttribute("userId");
		if (userIdInteger != null) {
			int userId = userIdInteger.intValue();
			if (dbController.postMemberFeedback(userId, message) == 0) {
				request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_FEEDBACK_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ABOUTUS_PAGE);
			} else {

				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ABOUTUS_PAGE);
			}
		} else {
			String email = request.getParameter("email");
			String fullName = request.getParameter("fullName");
			if (!isValidEmail(email)) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.EMAIL_VERIFY);
				request.getRequestDispatcher(StringUtils.ABOUTUS_PAGE).forward(request, response);
				return; // Prevent further execution
			}
			if (dbController.postUsersFeedback(message, fullName, email) == 0) {
				request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_FEEDBACK_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ABOUTUS_PAGE);
			} else {

				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ABOUTUS_PAGE);

			}
		}

	}

}
