package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import model.ProductModel;
import util.StringUtils;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.SEARCH_PRODUCT_SERVLET)
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get productid from url
		String searchBar = request.getParameter(StringUtils.SEARCH_BAR_INPUT);

		List<ProductModel> products = dbController.fetchSearchProducts(searchBar);

		// Set the list of product as an attribute in the request object
		request.setAttribute("products", products);

		request.getRequestDispatcher(StringUtils.SEARCH_HOMEPAGE).forward(request, response);
	}

}
