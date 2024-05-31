package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.OrderModel;
import model.UserModel;
import util.StringUtils;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.USER_SERVLET)
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Integer userIdInteger = (Integer) session.getAttribute("userId");
			if (userIdInteger != null) {
				int userId = userIdInteger.intValue();
				// Fetch all orders from the database
				List<OrderModel> orders = dbController.getSpecifiUserOrders(userId);
				// Fetch user details using the userId
				UserModel userDetails = dbController.fetchUserDetail(userId);

				// Set the order details as an attribute in the request object
				request.setAttribute("orderDetails", orders);
				// Set the user details as an attribute in the request object
				request.setAttribute("userDetails", userDetails);

				// Forward the request to the profile.jsp page
				request.getRequestDispatcher(StringUtils.PROFILE_PAGE).forward(request, response);
			} else {
				// userId is null, handle the situation (e.g., redirect to login page)
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_PROFILE_MESSAGE);
				request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
			}
		} else {
			//No Session
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_PROFILE_MESSAGE);
			request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
