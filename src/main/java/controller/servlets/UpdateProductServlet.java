package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DatabaseController;
import model.ProductModel;
import util.StringUtils;

/**
 * Servlet implementation class UpdateProductServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.UPDATE_PRODUCT_SERVLET)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB)
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateProductServlet() {
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

		String id = request.getParameter(StringUtils.PRODUCT_ID);
		System.out.println( "\n Updating Details of Product Id: "+id);
		int productId = Integer.parseInt(id);

		List<ProductModel> products = dbController.getProductDetails(productId);
		// Forward the request to the home.jsp page

//		System.out.println("New Products"+ product);
//		// Set the list of products as an attribute in the request object
		request.setAttribute("products", products);

		// Forward the request to the home.jsp page
		request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int pId = Integer.parseInt(request.getParameter(StringUtils.PRODUCT_ID));
			String productName = request.getParameter(StringUtils.PRODUCT_NAME);
			String productDescription = request.getParameter(StringUtils.PRODUCT_DESCRIPTION);
			int stockQuantity = Integer.parseInt(request.getParameter(StringUtils.STOCK_QUANTITY));
			int price = Integer.parseInt(request.getParameter(StringUtils.PRICE));
			String brand = request.getParameter(StringUtils.BRAND);
			String color = request.getParameter(StringUtils.COLOR);
			String connectivity = request.getParameter(StringUtils.CONNECTIVITY);
			String noiseCancellation = request.getParameter(StringUtils.NOISE_CANCELLATION);
			int cableLength = Integer.parseInt(request.getParameter(StringUtils.CABLE_LENGTH));
			String driver = request.getParameter(StringUtils.DRIVER);
			String weight = request.getParameter(StringUtils.WEIGHT);
			Part imagePart = request.getPart(StringUtils.PRODUCT_IMAGE);
			
			ProductModel productModel = new ProductModel(productName, productDescription, imagePart, stockQuantity,
					price, brand, color, connectivity, noiseCancellation, cableLength, driver, weight);
			
			productModel.setProductId(pId);

			boolean anyFieldEmpty = productName.isEmpty() || productDescription.isEmpty() || stockQuantity < 0
					|| String.valueOf(stockQuantity).isEmpty() || String.valueOf(price).isEmpty() || brand.isEmpty()
					|| String.valueOf(cableLength).isEmpty() || weight.isEmpty() || imagePart == null;

			if (anyFieldEmpty) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.FILL_ALL_THE_FIELDS);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (price < 10) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_PRICE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (color.equals("Select Color")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_COLOR_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (connectivity.equals("Select Connectivity")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_CONNECTIVITY_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (noiseCancellation.equals("Select Noise")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_NOISE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (driver.equals("Select Driver")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_DRIVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (cableLength < 10) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_LENGTH_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			}

			String savePath = StringUtils.IMAGE_DIR_PATH;
			String fileName = productModel.getImageUrlFromPart();

			if (fileName.equals("a")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_IMAGE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (!fileName.isEmpty() && fileName != null) {
				imagePart.write(savePath + fileName);
			}

			int result = dbController.updateProductDetails(productModel);

			switch (result) {
			case 1 -> {
				System.out.println( "\n Case 1:Successfully Updated Details of Product Id: "+ pId);
				request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_UPDATED_PRODUCT_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ADMIN_PRODUCT_SERVLET);
			}
			case 0 -> {
				System.out.println( "\n Case 0:Failed to Updated Details of Product Id: "+ pId);
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_UPDATED_PRODUCT_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
			}
			default -> {
				System.out.println( "\n Failed to Updated Details of Product Ids: "+ pId);
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
			}
			}
		} catch (NumberFormatException e) {
			System.out.println( "\n Failed to Updated Details of Product");
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.FILL_ALL_THE_FIELDS);
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
		} catch (Exception e) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, "An error occurred");
			request.getRequestDispatcher(StringUtils.UPDATE_PRODUCT_PAGE).forward(request, response);
		}

	}

}
