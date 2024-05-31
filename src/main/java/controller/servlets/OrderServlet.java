package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DatabaseController;
import model.UserModel;
import util.StringUtils;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.ORDER_SERVLET)
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isOrderCart = request.getParameter(StringUtils.CHECK_EACH_CART_ORDER);
		HttpSession session = request.getSession(false);
		if (session != null) {
			int userId = (Integer) session.getAttribute(StringUtils.USER_ID);
			int quantity = Integer.parseInt(request.getParameter(StringUtils.CART_QUANTITY));
			if (isOrderCart != null && isOrderCart.equals("yes")) {
				int cartId = Integer.parseInt(request.getParameter(StringUtils.CART_ID));
				int result = dbController.orderEachProducts(userId, cartId, quantity);
				if (result > 0) {
					response.sendRedirect(request.getContextPath() + StringUtils.PRODUCT_SERVLET);
				} else {
					// Product addition to cart failed, redirect to an error page
					response.sendRedirect(request.getContextPath() + StringUtils.PRODUCT_SERVLET);
				}
			} else {
				doPost(request, response);
			}
		} else {
			System.out.println("Session is null add to cart");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session != null) {
			int userId = (Integer) session.getAttribute(StringUtils.USER_ID);
			int result = dbController.orderAllProducts(userId);
			if (result > 0) {
				response.sendRedirect(request.getContextPath() + StringUtils.PRODUCT_SERVLET);
			} else {
				// Product addition to cart failed, redirect to an error page
				response.sendRedirect(request.getContextPath() + StringUtils.PRODUCT_SERVLET);
			}
		} else {
			System.out.println("Session is null add to cart");
		}
	}
}
