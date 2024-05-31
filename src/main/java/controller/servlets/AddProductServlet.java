package controller.servlets;

import java.io.IOException;
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
 * @author anjil
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.ADD_PRODUCT_SERVLET })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB)
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseController dbController = new DatabaseController();

	public AddProductServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
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

			boolean anyFieldEmpty = productName.isEmpty() || productDescription.isEmpty() || stockQuantity < 0
					|| String.valueOf(stockQuantity).isEmpty() || String.valueOf(price).isEmpty() || brand.isEmpty()
					|| String.valueOf(cableLength).isEmpty() || weight.isEmpty() || imagePart == null;

			if (anyFieldEmpty) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.FILL_ALL_THE_FIELDS);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (price < 10) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_PRICE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (color.equals("Select Color")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_COLOR_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (connectivity.equals("Select Connectivity")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_CONNECTIVITY_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (noiseCancellation.equals("Select Noise")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_NOISE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (driver.equals("Select Driver")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_DRIVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			} else if (cableLength < 10) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_LENGTH_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			}

			String savePath = StringUtils.IMAGE_DIR_PATH;
			String fileName = productModel.getImageUrlFromPart();
			
			if (fileName.equals("a")) {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SELECT_IMAGE_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
				return;
			}else if (!fileName.isEmpty() && fileName != null) {
				imagePart.write(savePath + fileName);
			}
			
			int result = dbController.addProduct(productModel);

			switch (result) {
			case 1 -> {
				request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_ADD_PRODUCT_MESSAGE);
				response.sendRedirect(request.getContextPath() + StringUtils.ADMIN_PRODUCT_SERVLET);
			}
			case 0 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PRODUCT_NAME_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
			}
			case -1 -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
			}
			default -> {
				request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
				request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
			}
			}
		} catch (NumberFormatException e) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.FILL_ALL_THE_FIELDS);
			request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
		} catch (Exception e) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, "An error occurred");
			request.getRequestDispatcher(StringUtils.ADD_PRODUCT_PAGE).forward(request, response);
		}
	}
}
