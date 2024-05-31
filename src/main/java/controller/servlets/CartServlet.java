package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.CartModel;
import util.StringUtils;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.CART_SERVLET)
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController = new DatabaseController();

	public CartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId != null) {
				System.out.println("\n Going To Cart of User: " + userId);
				// Fetch all Cart details of Particular User from the database
				ArrayList<CartModel> cartProduct = dbController.fetchCartRecordById(userId);

				// Set the list of cart product as an attribute in the request object
				request.setAttribute("cartProducts", cartProduct);

				// Forward the request to the cart.jsp page
				request.getRequestDispatcher(StringUtils.CART_PAGE).forward(request, response);
			} else {
				// userId is null in the session
				System.out.println("User is not logged in.");
				// Set a message to be displayed on the login page
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.CART_ERROR_MESSAGE);
				// Forward the request to the login.jsp page
				request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
			}
		} else {
			System.out.println("Session is null add to cart");
		}
	}

}
