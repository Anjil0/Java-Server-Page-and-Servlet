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
import model.UserModel;
import util.StringUtils;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.UPDATE_PROFILE_SERVLET)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB)
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		// TODO Auto-generated method stub
		// Retrieve form data
		String updateId = request.getParameter(StringUtils.USER_ID);
		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		String userName = request.getParameter(StringUtils.USER_NAME);
		String phoneNumber = request.getParameter(StringUtils.PHONE_NUMBER);
		String address = request.getParameter(StringUtils.ADDRESS);
		Part imagePart = request.getPart(StringUtils.IMAGE);

		UserModel userUpdate = new UserModel(firstName, lastName, userName, phoneNumber, address, null, null,
				imagePart);

		String savePath = StringUtils.IMAGE_DIR_PATH;
		String fileName = userUpdate.getImageUrlFromPart();

		if (!fileName.isEmpty() && fileName != null) {
			imagePart.write(savePath + fileName);
		}
		userUpdate.setUserId(Integer.parseInt(updateId));

		int result = dbController.updateUserDetails(userUpdate);
		if (result == 1) {
			request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.USER_UPDATE_MESSAGE);
			request.getRequestDispatcher(StringUtils.USER_SERVLET).forward(request, response);
		} else if (result == -4) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_USER_UPDATE_PHONE_SAME_MESSAGE);
			request.getRequestDispatcher(StringUtils.USER_SERVLET).forward(request, response);
		}

	}

}
