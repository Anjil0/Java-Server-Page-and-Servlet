package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import model.OrderModel;
import util.StringUtils;

/**
 * Servlet implementation class AdminOrderDisplay
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.ORDER_DISPLAY_SERVLET })
public class OrderDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderDisplayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Fetch all orders from the database
		List<OrderModel> orders = dbController.getAllOrders();

		// Set the list of Allorders as an attribute in the request object
		request.setAttribute("Allorders", orders);

		// Forward the request to the Order List page
		request.getRequestDispatcher(StringUtils.ORDER_LIST_PAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
