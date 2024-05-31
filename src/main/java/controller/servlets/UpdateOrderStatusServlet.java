package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import util.StringUtils;

/**
 * Servlet implementation class UpdateOrderStatusServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.UPDATE_ORDER_STATUS_SERVLET)
public class UpdateOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateOrderStatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter(StringUtils.ORDER_ID));
		String orderStatus = request.getParameter(StringUtils.ORDER_STATUS);

		int result = dbController.updateOrderStatus(orderId, orderStatus);
		if (result == 1) {
			request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.USER_UPDATE_MESSAGE);
			request.getRequestDispatcher(StringUtils.ORDER_DISPLAY_SERVLET).forward(request, response);
		} else if (result == -1 || result == 0) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_USER_UPDATE_PHONE_SAME_MESSAGE);
			request.getRequestDispatcher(StringUtils.ORDER_DISPLAY_SERVLET).forward(request, response);
		}

	}

}
