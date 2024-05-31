package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import util.StringUtils;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.ADD_TO_CART_SERVLET })
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCartServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productId = Integer.parseInt(request.getParameter(StringUtils.PRODUCT_ID));
		int quantity = Integer.parseInt(request.getParameter(StringUtils.CART_QUANTITY));
		HttpSession session = request.getSession();
		if (session != null) {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId != null) {
				int result = dbController.addToCart(userId, productId, quantity);
				if (result >= 0) {
					// Product added to cart successfully, redirect to a success page
					System.out.println(StringUtils.SUCCESS_ADD_TO_CART);
					request.getRequestDispatcher(StringUtils.CART_SERVLET).forward(request, response);
				} else {
					request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_ADD_CART_MESSAGE);
					request.getRequestDispatcher(StringUtils.PRODUCT_SERVLET).forward(request, response);
				}
			} else {
				// IF userId is null in the session
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_ADD_CART_MESSAGE);
				request.getRequestDispatcher(StringUtils.PRODUCT_SERVLET).forward(request, response);
			}
		} else {
			System.out.println("Session is null add to cart");
		}
	}
}
