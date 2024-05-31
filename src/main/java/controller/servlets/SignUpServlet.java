package controller.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * @author anjil
 */
@WebServlet(asyncSupported = true, urlPatterns = StringUtils.SIGNUP_SERVLET)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB)
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseController dbController = new DatabaseController();

	public SignUpServlet() {
		super();
	}

	private boolean isValidEmail(String email) {
		// Regex pattern for Gmail email validation
		String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	private boolean isValidPhoneNumber(String phoneNumber) {
		// Regex pattern for 10-digit phone number validation
		String phoneRegex = "^\\d{10}$";
		Pattern pattern = Pattern.compile(phoneRegex);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	private boolean isValidPassword(String password) {
		// Regex pattern for password validation
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=]).{8,}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	private boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z]+");
	}

	private boolean isValidUsername(String username) {
		return username.matches("[a-zA-Z0-9]+");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter(StringUtils.FIRST_NAME);
		String lastName = request.getParameter(StringUtils.LAST_NAME);
		String userName = request.getParameter(StringUtils.USER_NAME);
		String phoneNumber = request.getParameter(StringUtils.PHONE_NUMBER);
		String address = request.getParameter(StringUtils.ADDRESS);
		String email = request.getParameter(StringUtils.EMAIL);
		String password = request.getParameter(StringUtils.PASSWORD);
		Part imagePart = request.getPart(StringUtils.IMAGE);

		UserModel userModel = new UserModel(firstName, lastName, userName, phoneNumber, address, email, password,
				imagePart);

		if (!isValidName(firstName) || !isValidName(lastName)) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.NAME_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			return; // Prevent further execution
		}
		if (!isValidUsername(userName)) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_USERNAME_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			return; // Prevent further execution
		}
		if (!isValidEmail(email)) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.EMAIL_VERIFY);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			return; // Prevent further execution
		}

		if (!isValidPhoneNumber(phoneNumber)) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PHONE_NUMBER_VERIFY);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			return; // Prevent further execution
		}

		if (!isValidPassword(password)) {
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PASSWORD_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			return; // Prevent further execution
		}

		String savePath = StringUtils.IMAGE_DIR_PATH;
		String fileName = userModel.getImageUrlFromPart();

		if (!fileName.isEmpty() && fileName != null) {
			imagePart.write(savePath + fileName);
		}

		int result = dbController.addUser(userModel);

		switch (result) {
		case 1:
			request.setAttribute(StringUtils.SUCCESS_MESSAGE, StringUtils.SUCCESS_REGISTER_MESSAGE);
			request.getRequestDispatcher(StringUtils.LOGIN_PAGE).forward(request, response);
			break;
		case 0:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.ERROR_REGISTER_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		case -1:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		case -2:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.USERNAME_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		case -3:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.EMAIL_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		case -4:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.PHONE_NUMBER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		default:
			request.setAttribute(StringUtils.ERROR_MESSAGE, StringUtils.SERVER_ERROR_MESSAGE);
			request.getRequestDispatcher(StringUtils.SIGNUP_PAGE).forward(request, response);
			break;
		}
	}
}
