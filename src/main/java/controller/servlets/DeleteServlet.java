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
 * Servlet implementation class DeleteServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.DELETE_SERVLET)
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doget(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doDelete(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doDelete(request, response);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isAdminDelete = request.getParameter("adminProductDelete");
		String isCartDelete = request.getParameter("cartProductDelete");
		String isClearCart = request.getParameter("isClearCart");

		// Check if isAdminDelete parameter is not null
		if (isAdminDelete != null && isAdminDelete.equals("yes")) {
			int adminDeleteId = Integer.parseInt(request.getParameter("deleteProductId"));
			if (dbController.deleteProduct(adminDeleteId) == 1) {
				request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_ADMIN_DELETE_MESSAGE);
			} else {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_DELETE_PRODCUT_MESSAGE);
			}
			response.sendRedirect(request.getContextPath() + StringUtils.ADMIN_PRODUCT_SERVLET);
		}
		// Check if isCartDelete parameter is not null
		else if (isCartDelete != null && isCartDelete.equals("yes")) {
			int cartDeleteId = Integer.parseInt(request.getParameter("deleteCartId"));
			HttpSession session = request.getSession(false);
			if (session != null) {
				Integer userId = (Integer) session.getAttribute("userId");
				if (userId != null) {
					if (dbController.deleteEachCartProduct(cartDeleteId) == 0) {
						request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_CART_DELETE_MESSAGE);
					} else {
						request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_DELETE_PRODCUT_CART_MESSAGE);
					}
					response.sendRedirect(request.getContextPath() + StringUtils.CART_SERVLET);
				} else {
					// userId is null in the session
					request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.CART_ERROR_MESSAGE);
					request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
				}
			} else {
				// Session is null
				System.out.println("Session is null add to cart");
				response.sendRedirect(request.getContextPath() + StringUtils.LOGIN_PAGE);
			}
		} else if (isClearCart != null && isClearCart.equals("yes")) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				Integer userId = (Integer) session.getAttribute("userId");
				if (userId != null) {
					if (dbController.clearCartsProduct(userId) == 0) {
						request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_CLEAR_CART_MESSAGE);
					} else {
						request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_DELETE_PRODCUT_CART_MESSAGE);
					}
					response.sendRedirect(request.getContextPath() + StringUtils.CART_SERVLET);
				} else {
					// userId is null in the session
					request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.CART_ERROR_MESSAGE);
					request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
				}
			} else {
				// Session is null
				System.out.println("Session is null add to cart");
				response.sendRedirect(request.getContextPath() + StringUtils.LOGIN_PAGE);
			}
		} else {
			System.out.println("NO Deletes");
		}
	}

}
