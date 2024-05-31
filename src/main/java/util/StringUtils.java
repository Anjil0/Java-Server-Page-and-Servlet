package util;

import java.io.File;

public class StringUtils {

	// Start User SQL Queries
	public static final String INSERT_USER = "INSERT INTO users "
			+ "( firstName, lastName, userName, phoneNumber, address, email, password, image) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String GET_ALL_USERS = "SELECT * FROM users";
	public static final String GET_USER_LOGIN_INFO = "SELECT * FROM users WHERE userName = ?";
	public static final String GET_USER_DETAILS_BY_ID = "SELECT * FROM users WHERE userId = ?";
	public static final String GET_USERNAME = "SELECT COUNT(*) FROM users WHERE userName = ?";
	public static final String GET_PHONE = "SELECT COUNT(*) FROM users WHERE phoneNumber = ?";
	public static final String GET_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
	public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE userId = ?";
	public static final String UPDATE_USER = "UPDATE users SET firstName = ?, lastName = ?, userName =?, phoneNumber = ?, address = ?, image=? WHERE userId = ?";
	public static final String INSERT_USER_FEEDBACK = "INSERT INTO feedback (fullName, message, email) VALUES (?, ?, ?);";
	public static final String INSERT_MEMBER_FEEDBACK = "INSERT INTO feedback (userId, fullName, message, email) SELECT  u.userId, CONCAT(u.firstName, ' ', u.lastName) , ?, u.email FROM users u WHERE u.userId = ?";
	// End User SQL Queries

	// Start Product SQL Queries
	public static final String INSERT_PRODUCT = "INSERT INTO products "
			+ "(productName, productDescription, image, stockQuantity, price, brand, color, connectivity, noiseCancellation, cableLength, driver, weight) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_PRODUCTS = "SELECT * FROM products WHERE stockQuantity > 0;";
	public static final String GET_PRODUCT_NAME_INFO = "SELECT * FROM products WHERE productName = ?";
	public static final String GET_PRODUCT_NAME = "SELECT COUNT(*) FROM products WHERE productName = ?";
	public static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE productId = ?";
	public static final String SEARCH_PRODUCT = "SELECT * FROM products WHERE (stockQuantity > 0 AND productName LIKE ?) OR (stockQuantity > 0 AND price >= ?)";
	public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE productId = ?";
	public static final String UPDATE_PRODUCT = "UPDATE products SET productName = ?, productDescription = ?, image = ?, stockQuantity = ?, price = ?, brand = ?, color = ?, connectivity = ?, noiseCancellation = ?, cableLength = ?, driver = ?, weight = ? WHERE productId = ?";
	public static final String ADD_TO_CART_PRODUCT = "INSERT INTO headphonecarts (userID, productID, quantity, isOrder) VALUES (?, ?, ?, ?)";
	public static final String CHECK_PRODUCT = "SELECT COUNT(*) FROM headphonecarts WHERE userId = ? AND productId = ? AND isOrder = false";
	public static final String UPDATE_QUANTITY_PRODUCT = "UPDATE headphonecarts SET quantity = quantity + ? WHERE userId = ? AND isOrder = false AND productId = ?";
	
	public static final String CART_PRODUCT = "SELECT p.image, p.productName, p.price, c.quantity, c.productId, c.isOrder, c.cartId, p.stockQuantity FROM users u INNER JOIN headphonecarts c ON u.userId = c.userId INNER JOIN products p ON c.productId = p.productId WHERE u.userId = ? AND c.isOrder = 0";
	public static final String DELETE_CART_PRODUCT_BY_ID = "DELETE FROM headphonecarts WHERE cartId = ?";
	public static final String DELETE_ALL_CART_BY_USER_ID = "DELETE FROM headphonecarts WHERE userID = ? AND isOrder = false";
	public static final String ORDER_SPECIFIC_BY_USER_ID_CART_ID = "INSERT INTO orders (cartId, totalPrice, orderDate, orderStatus) SELECT hc.cartId, (SELECT SUM(p.price * ?) FROM headphonecarts c INNER JOIN products p ON c.productId = p.productId WHERE c.cartId = hc.cartId) AS totalPrice, NOW() AS orderDate, 'Pending' AS orderStatus FROM headphonecarts hc WHERE hc.userID = ? AND hc.cartId = ?;\n";
	public static final String CART_VIEW_OFF_BY_USER_ID = "UPDATE headphonecarts SET isOrder = true WHERE userID = ? AND cartId =?;";
	public static final String UPDATE_CART_QUANTITY_USER_ID = "UPDATE headphonecarts SET quantity=? WHERE userID = ? AND cartId =?;";
	public static final String UPDATE_PRODUCT_QUANTITY_ON_ORDER ="UPDATE products p JOIN headphonecarts hc ON p.productId = hc.productId SET p.stockQuantity = p.stockQuantity - hc.quantity WHERE hc.cartId = ? AND userID = ?";
	public static final String ORDER_ALL_BY_USER_ID = "INSERT INTO orders (cartId, totalPrice, orderDate, orderStatus) SELECT hc.cartId, (SELECT SUM(p.price * ?) FROM headphonecarts c INNER JOIN products p ON c.productId = p.productId WHERE c.cartId = hc.cartId) AS totalPrice, NOW() AS orderDate, 'Pending' AS orderStatus FROM headphonecarts hc WHERE hc.userID = ?;";
	public static final String GET_ALL_ORDERS = "SELECT o.orderId, u.userName, p.productName, hc.quantity, p.price, o.totalPrice, o.orderDate, o.orderStatus FROM orders o JOIN headphonecarts hc ON o.cartId = hc.cartId JOIN products p ON hc.productId = p.productId JOIN users u ON hc.userId = u.userId";
	public static final String GET_SPECIFIC_USER_ORDERS = "SELECT p.productName, o.orderDate, hc.quantity, p.price, o.totalPrice, o.orderStatus FROM orders o INNER JOIN headphonecarts hc ON o.cartId = hc.cartId INNER JOIN products p ON hc.productId = p.productId WHERE hc.userId = ?; ";
	public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET orderStatus = ? WHERE orderId = ?";
	// End Product SQL Queries

	// image Directories
	public static final String SAVE_PATH = "/home/arch/eclipse-workspace/MyFirstProject/src/main/webapp/resources/images";
	public static final String IMAGE_DIR = "Users\\angil\\eclipse-workspace\\SonicPulse\\src\\main\\webapp\\resources\\images\\";
	public static final String IMAGE_DIR_PATH = "C:" + File.separator + IMAGE_DIR;

	// Get User Parameter names
	public static final String USER_ID = "userId";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USER_NAME = "userName";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ADDRESS = "address";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String IMAGE = "img";
	public static final String IS_ADMIN = "isAdmin";
	// End User Parameter names

	// Get Product Parameter names
	public static final String PRODUCT_ID = "productId";
	public static final String CART_QUANTITY = "quantity";
	public static final String PRODUCT_NAME = "productName";
	public static final String PRODUCT_DESCRIPTION = "productDescription";
	public static final String PRODUCT_IMAGE = "productImage";
	public static final String STOCK_QUANTITY = "stockQuantity";
	public static final String PRICE = "price";
	public static final String BRAND = "brand";
	public static final String COLOR = "color";
	public static final String CONNECTIVITY = "connectivity";
	public static final String NOISE_CANCELLATION = "noiseCancellation";
	public static final String CABLE_LENGTH = "cableLength";
	public static final String DRIVER = "driver";
	public static final String WEIGHT = "weight";
	public static final String SEARCH_BAR_INPUT = "searchItem";
	// End Product Parameter names

	// Get Cart Parameter names
	public static final String CART_ID = "cartId";
	public static final String CHECK_EACH_CART_ORDER = "eachOrder";
	public static final String CART_ERROR_MESSAGE = "Please Login to access The cart.";
	public static final String SUCCESS_ADD_TO_CART = "User: Product Added TO Cart";
	public static final String ERROR_ADD_CART_MESSAGE = "Please, Login to ADD The Product to The cart.";
	// End Cart Parameter names

	public static final String ERROR_PROFILE_MESSAGE = "Please, Login To View Profile";

	public static final String SUCCESS_FEEDBACK_MESSAGE = "Feedback Submitted Successfully";
	
	public static final String ERROR_FEEDBACK_MESSAGE = "Feedback Not Submiited";
	///
	
	public static final String SUCCESS_CART_DELETE_MESSAGE = "Product Successfully removed from cart";
	public static final String SUCCESS_CLEAR_CART_MESSAGE = "Cart is cleared Successfully";
	public static final String ERROR_CLEAR_CART_MESSAGE = "CART COULD NOT BE CLEARED";
	///
	
	// Get Order Parameter names
	public static final String ORDER_ID = "orderId";
	public static final String ORDER_STATUS = "orderStatus";

	// End Order Parameter names

	// Start User Register Error messagesstring messages
	public static final String SUCCESS_REGISTER_MESSAGE = "User Successfully Registered";
	public static final String ERROR_REGISTER_MESSAGE = "Please correct the form data!";
	public static final String SERVER_ERROR_MESSAGE = "An unexpected server error occurred.!!";
	public static final String NAME_ERROR_MESSAGE = "Please, Enter Valid Names!!";
	public static final String ERROR_USERNAME_MESSAGE = "Please, Enter Valid UserName!!";
	public static final String USERNAME_ERROR_MESSAGE = "Username is already registered!";
	public static final String PASSWORD_ERROR_MESSAGE = "Please, Enter a Strong Password!";
	public static final String EMAIL_ERROR_MESSAGE = "Email is already registered!";
	public static final String EMAIL_VERIFY = "Email is not Valid!";
	public static final String PHONE_NUMBER_VERIFY = "PhoneNumber is not Valid!";
	public static final String PHONE_NUMBER_ERROR_MESSAGE = "Phone Number is already registered!";
	// End User register page messages

	// Start login page message
	public static final String SUCCESS_LOGIN_MESSAGE = "Successfully LoggedIn!";
	public static final String USER_NOT_FOUND = "User Not Found!";
	public static final String ERROR_LOGIN_MESSAGE = "Please, Enter Username or Password Correctly!";
	// End login page message

	// Start Product add Error messages
	public static final String SUCCESS_ADD_PRODUCT_MESSAGE = "- Product Added Sussessfully -";
	public static final String ERROR_FORM_MESSAGE = "Please, correct the form data !";
	public static final String PRODUCT_NAME_ERROR_MESSAGE = "Product Name is already in the List !";
	public static final String SELECT_IMAGE_ERROR_MESSAGE = "Please, Provide An Image!";
	public static final String SELECT_COLOR_ERROR_MESSAGE = "Plese, Select The Color Option!";
	public static final String SELECT_PRICE_ERROR_MESSAGE = "Plese, Enter Correct price!";
	public static final String SELECT_LENGTH_ERROR_MESSAGE = "Plese, Enter Correct Length!";
	public static final String SELECT_WEIGHT_ERROR_MESSAGE = "Plese, Enter Correct weight!";
	public static final String SELECT_DRIVER_ERROR_MESSAGE = "Plese, Select The Driver Option!";
	public static final String SELECT_NOISE_ERROR_MESSAGE = "Plese, Select The Noise Cancellation Option!";
	public static final String SELECT_CONNECTIVITY_ERROR_MESSAGE = "Plese, Select The Connectivity Option!";
	public static final String FILL_ALL_THE_FIELDS = "Please, Fill Correct details in all the Fields!";
	// End Product add messages

	//ERROR  messages
	public static final String SUCCESS_ADMIN_DELETE_MESSAGE = "Successfully Deleted The Product!";
	public static final String ERROR_DELETE_PRODCUT_MESSAGE = "Could not delete the Product!";
	public static final String ERROR_DELETE_PRODCUT_CART_MESSAGE = "Could not delete the Product From Cart!";
	public static final String SUCCESS_UPDATED_PRODUCT_MESSAGE = "Successfully Updated the Product Details!";
	public static final String ERROR_UPDATED_PRODUCT_MESSAGE = "Could Not  Updated the Product Details!";
	public static final String USER_UPDATE_MESSAGE = "Successfully Updated the User Profile!";
	public static final String ERROR_USER_UPDATE_MESSAGE = "Could not Update user Profile!";
	public static final String ERROR_USER_UPDATE_PHONE_SAME_MESSAGE = "Could not Update user Profile as Phone Number is same!";

	// start string message
	public static final String SUCCESS_MESSAGE = "successMessage";
	public static final String ERROR_MESSAGE = "errorMessage";
	// End string messages

	// Start JSP Route
	public static final String LOGIN_PAGE = "/pages/login.jsp";
	public static final String SIGNUP_PAGE = "/pages/signUp.jsp";
	public static final String HOME_PAGE = "/pages/index.jsp";
	public static final String SEARCH_HOMEPAGE = "/pages/searchPage.jsp";
	public static final String PROFILE_PAGE = "/pages/profile.jsp";
	public static final String ADMIN_PAGE = "/pages/admin.jsp";
	public static final String ADD_PRODUCT_PAGE = "/pages/AddProduct.jsp";
	public static final String ABOUTUS_PAGE = "/pages/aboutUs.jsp";
	public static final String SINGLE_PRODUCT_PAGE = "/pages/singleProduct.jsp";
	public static final String UPDATE_PRODUCT_PAGE = "/pages/updateProduct.jsp";
	public static final String CART_PAGE = "/pages/cart.jsp";
	public static final String ORDER_LIST_PAGE = "/pages/orderList.jsp";
	public static final String FEEDABACK_PAGE = "/pages/feedback.jsp";
	// End JSP Route

	// Start Servlet Route
	public static final String SIGNUP_SERVLET = "/SignUpServlet";
	public static final String LOGIN_SERVLET = "/LoginServlet";
	public static final String FEEDBACK_SERVLET = "/FeedbackServlet";
	public static final String FEEDBACK_DISPLAY_SERVLET = "/FeedbackDisplayServlet";
	public static final String USER_SERVLET = "/UserServlet";
	public static final String UPDATE_PROFILE_SERVLET = "/UpdateProfileServlet";
	public static final String ADD_PRODUCT_SERVLET = "/AddProductServlet";
	public static final String PRODUCT_SERVLET = "/ProductServlet";
	public static final String ADMIN_PRODUCT_SERVLET = "/AdminProductServlet";
	public static final String SINGLE_PRODUCT_SERVLET = "/SingleProduct";
	public static final String SEARCH_PRODUCT_SERVLET = "/SearchServlet";
	public static final String ADD_TO_CART_SERVLET = "/AddToCartServlet";
	public static final String DELETE_SERVLET = "/DeleteServlet";
	public static final String UPDATE_PRODUCT_SERVLET = "/UpdateProductServlet";
	public static final String LOGOUT_SERVLET = "/LogoutServlet";
	public static final String CART_SERVLET = "/CartServlet";
	public static final String ORDER_SERVLET = "/OrderServlet";
	public static final String ORDER_DISPLAY_SERVLET = "/OrderDisplayServlet";
	public static final String UPDATE_ORDER_STATUS_SERVLET = "/UpdateOrderStatusServlet";

	// End Servlet Route
}