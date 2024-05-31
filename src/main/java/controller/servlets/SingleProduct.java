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
 * Servlet implementation class SingleProduct
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.SINGLE_PRODUCT_SERVLET)
public class SingleProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingleProduct() {
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
		//get productid from url
		int productId = Integer.parseInt(request.getParameter(StringUtils.PRODUCT_ID));

		List<ProductModel> products = dbController.getProductDetails(productId);
		// Forward the request to the home.jsp page

//		System.out.println("New Products"+ product);
//		// Set the list of products as an attribute in the request object
		request.setAttribute("products", products);

		// Forward the request to the home.jsp page
		request.getRequestDispatcher(StringUtils.SINGLE_PRODUCT_PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
