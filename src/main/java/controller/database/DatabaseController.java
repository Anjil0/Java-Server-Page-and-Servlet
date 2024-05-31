package controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.CartModel;
import model.FeedbackModel;
import model.OrderModel;
import model.PasswordEncryptionWIthAes;
import model.ProductModel;
import model.UserModel;
import util.StringUtils;

public class DatabaseController {

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3307/sonicpulse";
		String user = "root";
		String pass = "";
		return DriverManager.getConnection(url, user, pass);
	}

	// FOR Adding user in the database
	public int addUser(UserModel userModel) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_USER);

			PreparedStatement checkUsernameSt = con.prepareStatement(StringUtils.GET_USERNAME);
			checkUsernameSt.setString(1, userModel.getuserName());
			ResultSet checkUsernameRs = checkUsernameSt.executeQuery();

			checkUsernameRs.next();

			if (checkUsernameRs.getInt(1) > 0) {
				return -2; // Username already exists
			}

			PreparedStatement checkPhoneSt = con.prepareStatement(StringUtils.GET_PHONE);
			checkPhoneSt.setString(1, userModel.getPhoneNumber());
			ResultSet checkPhoneRs = checkPhoneSt.executeQuery();

			checkPhoneRs.next();

			if (checkPhoneRs.getInt(1) > 0) {
				return -4; // Phone Number already exists
			}

			PreparedStatement checkEmailSt = con.prepareStatement(StringUtils.GET_EMAIL);
			checkEmailSt.setString(1, userModel.getEmail());
			ResultSet checkEmailRs = checkEmailSt.executeQuery();

			checkEmailRs.next();

			if (checkEmailRs.getInt(1) > 0) {
				return -3; // Email already exists
			}

			// Encrypt password before storing it in the database
			String encryptedPassword = PasswordEncryptionWIthAes.encryptPassword(userModel.getPassword(),
					"U3CdwubLD5yQbUOG92ZnHw==");

			st.setString(1, userModel.getFirstName());
			st.setString(2, userModel.getLastName());
			st.setString(3, userModel.getuserName());
			st.setString(4, userModel.getPhoneNumber());
			st.setString(5, userModel.getAddress());
			st.setString(6, userModel.getEmail());
			st.setString(7, encryptedPassword);
			st.setString(8, userModel.getImageUrlFromPart());

			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// for getting users login cardentials
	public int getUserLoginInfo(String username, String password) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.GET_USER_LOGIN_INFO);
			st.setString(1, username);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				// getting data from database
				String userDb = rs.getString("userName");
				String encryptedPassword = rs.getString("password");
				int isAdmin = rs.getInt("isAdmin");

				// Decrypting password from database and comparing it with entered password
				String decryptedPassword = PasswordEncryptionWIthAes.decryptPassword(encryptedPassword,
						"U3CdwubLD5yQbUOG92ZnHw==");

				if (decryptedPassword != null && userDb.equals(username) && decryptedPassword.equals(password)) {
					if (isAdmin == 1) {
						return 0; // Admin login successful
					} else {
						return 1; // Regular user login successful
					}
				} else {
					return 2; // Password Wrong
				}
			} else {
				// User record Not found
				return 3;
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// to get the user details
	public UserModel verifyUser(String userName) throws ClassNotFoundException {
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(StringUtils.GET_USER_LOGIN_INFO)) {
			statement.setString(1, userName);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					// Fetch user details from the result set and create a User object
					UserModel user = new UserModel();
					user.setUserId(resultSet.getInt("userId"));
					user.setFirstName(resultSet.getString("firstName"));
					user.setLastName(resultSet.getString("lastName"));
					user.setEmail(resultSet.getString("email"));
					user.setPhoneNumber(resultSet.getString("phoneNumber"));
					user.setAddress(resultSet.getString("address"));
					user.setuserName(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					user.setImageUrlFromPart(resultSet.getString("image"));
					return user;
				} else {
					// No User found with the given username
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle or log the exception as needed
			return null;
		}
	}

	// to get the Users details from the database for profile
	public UserModel fetchUserDetail(int SessionUserID) {
		Connection connection = null;
		UserModel userUpdate = new UserModel(); // Initialize user as null
		try {
			connection = getConnection();

			PreparedStatement st = connection.prepareStatement(StringUtils.GET_USER_DETAILS_BY_ID);
			st.setInt(1, SessionUserID);
			ResultSet dbUserData = st.executeQuery();
			if (dbUserData.next()) {
				// Create the UserModel object
				userUpdate.setUserId(dbUserData.getInt(1));
				userUpdate.setFirstName(dbUserData.getString(2));
				userUpdate.setLastName(dbUserData.getString(3));
				userUpdate.setuserName(dbUserData.getString(4));
				userUpdate.setPhoneNumber(dbUserData.getString(5));
				userUpdate.setAddress(dbUserData.getString(6));
				userUpdate.setEmail(dbUserData.getString(7));
				userUpdate.setHasRole(dbUserData.getInt(9));
				userUpdate.setImageUrlFromPart(dbUserData.getString(10));
			} else {
				userUpdate = null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userUpdate; // Return the UserModel object (may be null if user not found)
	}

	// update user profile
	public int updateUserDetails(UserModel userModel) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_USER);

			st.setString(1, userModel.getFirstName());
			st.setString(2, userModel.getLastName());
			st.setString(3, userModel.getuserName());
			st.setString(4, userModel.getPhoneNumber());
			st.setString(5, userModel.getAddress());
			st.setString(6, userModel.getImageUrlFromPart());
			st.setInt(7, userModel.getUserId()); // User id to update the user details

			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// FOR Adding Feedback Details in the database
	public int postMemberFeedback(int userId, String message) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_MEMBER_FEEDBACK);
			st.setString(1, message);
			st.setInt(2, userId);
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int postUsersFeedback(String message, String fullName, String email) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_USER_FEEDBACK);
			st.setString(1, fullName);
			st.setString(2, message);
			st.setString(3, email);
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	// To get all feedbacks in the database
	public List<FeedbackModel> getAllFeedbacks() {
	    List<FeedbackModel> feedbacks = new ArrayList<>();
	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement("SELECT * FROM feedback");
	         ResultSet rs = st.executeQuery()) {
	        while (rs.next()) {
	        	FeedbackModel feedback = new FeedbackModel();
	            feedback.setFeedbackId(rs.getInt("feedbackId"));
	            feedback.setFullName(rs.getString("fullName"));
	            feedback.setEmail(rs.getString("email"));
	            feedback.setMessage(rs.getString("message"));
	            feedbacks.add(feedback);
	        }
	    } catch (SQLException | ClassNotFoundException ex) {
	        ex.printStackTrace(); // Log the exception for debugging
	    }
	    return feedbacks;
	}

	// FOR Adding Product in the database
	public int addProduct(ProductModel productModel) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.INSERT_PRODUCT);

			PreparedStatement checkUsernameSt = con.prepareStatement(StringUtils.GET_PRODUCT_NAME);
			checkUsernameSt.setString(1, productModel.getProductName());
			ResultSet checkUsernameRs = checkUsernameSt.executeQuery();

			checkUsernameRs.next();

			if (checkUsernameRs.getInt(1) > 0) {
				return 0; // ProductName already exists
			}
			st.setString(1, productModel.getProductName());
			st.setString(2, productModel.getProductDescription());
			st.setString(3, productModel.getImageUrlFromPart());
			st.setInt(4, productModel.getStockQuantity());
			st.setInt(5, productModel.getPrice());
			st.setString(6, productModel.getBrand());
			st.setString(7, productModel.getColor());
			st.setString(8, productModel.getConnectivity());
			st.setString(9, productModel.getNoiseCancellation());
			st.setInt(10, productModel.getCableLength());
			st.setString(11, productModel.getDriver());
			st.setString(12, productModel.getWeight());

			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// to get all the products from the database to display them
	public List<ProductModel> getAllProducts() {
		List<ProductModel> products = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(StringUtils.GET_ALL_PRODUCTS);
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				ProductModel product = new ProductModel();
				product.setProductId(rs.getInt("productId"));
				product.setProductName(rs.getString("ProductName"));
				product.setProductDescription(rs.getString("productDescription"));
				product.setImageUrlFromPart(rs.getString("image"));
				product.setStockQuantity(rs.getInt("StockQuantity"));
				product.setPrice(rs.getInt("Price"));
				product.setBrand(rs.getString("Brand"));
				product.setColor(rs.getString("color"));
				product.setConnectivity(rs.getString("connectivity"));
				product.setNoiseCancellation(rs.getString("noiseCancellation"));
				product.setCableLength(rs.getInt("cableLength"));
				product.setDriver(rs.getString("driver"));
				product.setWeight(rs.getString("weight"));
				products.add(product);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
		}
		return products;
	}

	// to get singleproduct details for single product description page
	public List<ProductModel> getProductDetails(int productId) {
		List<ProductModel> products = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(StringUtils.GET_PRODUCT_BY_ID)) {
			st.setInt(1, productId);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt("productId"));
					product.setProductName(rs.getString("ProductName"));
					product.setProductDescription(rs.getString("productDescription"));
					product.setImageUrlFromPart(rs.getString("image"));
					product.setStockQuantity(rs.getInt("StockQuantity"));
					product.setPrice(rs.getInt("Price"));
					product.setBrand(rs.getString("Brand"));
					product.setColor(rs.getString("color"));
					product.setConnectivity(rs.getString("connectivity"));
					product.setNoiseCancellation(rs.getString("noiseCancellation"));
					product.setCableLength(rs.getInt("cableLength"));
					product.setDriver(rs.getString("driver"));
					product.setWeight(rs.getString("weight"));
					products.add(product);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return products;
	}

	// to get the searched products details
	public List<ProductModel> fetchSearchProducts(String searchData) {
		List<ProductModel> products = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(StringUtils.SEARCH_PRODUCT)) {
			// Check if the search data is a number (price)
			if (searchData.matches("\\d+")) {
				double price = Double.parseDouble(searchData);
				// Search for products with price greater than or equal to the specified value
				st.setString(1, "");
				st.setDouble(2, price);
			} else {
				// Treat the search data as a product name
				st.setString(1, "%" + searchData + "%");
				st.setNull(2, Types.INTEGER);
			} 
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					ProductModel product = new ProductModel();
					product.setProductId(rs.getInt("productId"));
					product.setProductName(rs.getString("productName"));
					product.setProductDescription(rs.getString("productDescription"));
					product.setImageUrlFromPart(rs.getString("image"));
					product.setStockQuantity(rs.getInt("stockQuantity"));
					product.setPrice(rs.getInt("price"));
					product.setBrand(rs.getString("brand"));
					product.setColor(rs.getString("color"));
					product.setConnectivity(rs.getString("connectivity"));
					product.setNoiseCancellation(rs.getString("noiseCancellation"));
					product.setCableLength(rs.getInt("cableLength"));
					product.setDriver(rs.getString("driver"));
					product.setWeight(rs.getString("weight"));
					products.add(product);
				}
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
		}
		return products;
	}

	public int updateProductDetails(ProductModel productModel) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_PRODUCT);

			st.setString(1, productModel.getProductName());
			st.setString(2, productModel.getProductDescription());
			st.setString(3, productModel.getImageUrlFromPart());
			st.setInt(4, productModel.getStockQuantity());
			st.setInt(5, productModel.getPrice());
			st.setString(6, productModel.getBrand());
			st.setString(7, productModel.getColor());
			st.setString(8, productModel.getConnectivity());
			st.setString(9, productModel.getNoiseCancellation());
			st.setInt(10, productModel.getCableLength());
			st.setString(11, productModel.getDriver());
			st.setString(12, productModel.getWeight());
			st.setInt(13, productModel.getProductId()); // Product id to update the Product details

			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// to delete the product from the database
	public int deleteProduct(int id) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.DELETE_PRODUCT_BY_ID);
			st.setInt(1, id);
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0; // Return 1 if Product is delete successfully, otherwise 0
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Return -1 for any exceptions
		}
	}

//	Add To cart 
	public int addToCart(int userId, int productId, int quantity) {
		try (Connection con = getConnection()) {
			// Check if the product is already in the cart
			PreparedStatement checkStmt = con.prepareStatement(StringUtils.CHECK_PRODUCT);
			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, productId);
			ResultSet rs = checkStmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			if (count > 0) {
				// Product already exists, update the quantity
				PreparedStatement updateStmt = con.prepareStatement(StringUtils.UPDATE_QUANTITY_PRODUCT);
				updateStmt.setInt(1, quantity);
				updateStmt.setInt(2, userId);
				updateStmt.setInt(3, productId);
				int result = updateStmt.executeUpdate();
				if (result > 0) {
					System.out.println("\nQuantity updated Successfully for product " + productId);
					return 0; // Success
				} else {
					System.out.println("\nFailed to update quantity for product " + productId);
					return 1; // Failure
				}
			} else {
				// Product doesn't exist, insert a new row
				PreparedStatement insertStmt = con.prepareStatement(StringUtils.ADD_TO_CART_PRODUCT);
				insertStmt.setInt(1, userId);
				insertStmt.setInt(2, productId);
				insertStmt.setInt(3, quantity);
				insertStmt.setBoolean(4, false);
				int result = insertStmt.executeUpdate();
				if (result > 0) {
					System.out.println("\nProduct " + productId + " added Successfully to cart");
					return 0; // Success
				} else {
					System.out.println("\nFailed to add the product " + productId + " in cart");
					return 1; // Failure
				}
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Error
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Error
		}
	}

	public ArrayList<CartModel> fetchCartRecordById(int userId) {
		ArrayList<CartModel> cartlist = new ArrayList<>();
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.CART_PRODUCT);
			st.setInt(1, userId);
			ResultSet table = st.executeQuery();
			while (table.next()) {

				String productImage = table.getString(1);
				String productName = table.getString(2);
				int productPrice = table.getInt(3);
				int productQuantity = table.getInt(4);
				int productId = table.getInt(5);
				boolean isOrder = table.getBoolean(6);
				int cartId = table.getInt(7);
				int stockQuantity = table.getInt(8);

				CartModel cartProduct = new CartModel(cartId, productId, userId, productImage, productName,
						productQuantity, productPrice, isOrder, stockQuantity);
				cartlist.add(cartProduct);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartlist;
	}

	// to delete the product from the database
	public int deleteEachCartProduct(int cartId) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.DELETE_CART_PRODUCT_BY_ID);
			st.setInt(1, cartId);
			int result = st.executeUpdate();
			if (result > 0) {
				System.out.println("\nCart " + cartId + " Deleted Successfully!");
				return 0; // Success
			} else {
				System.out.println("\nFailed to delete the Cart No " + cartId + " in cart");
				return 1; // Failure
			} // Return 1 if Product is delete successfully, otherwise 0
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Return -1 for any exceptions
		}
	}

	public int clearCartsProduct(int userId) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.DELETE_ALL_CART_BY_USER_ID);
			st.setInt(1, userId);
			int result = st.executeUpdate();
			if (result > 0) {
				System.out.println("\nAll Carts of " + userId + " Deleted Successfully!");
				return 0; // Success
			} else {
				System.out.println("\nFailed to delete the Cart details of " + userId + " in cart");
				return 1; // Failure
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Return -1 for any exceptions
		}
	}

	public int orderEachProducts(int userId, int cartId, int quantity) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.ORDER_SPECIFIC_BY_USER_ID_CART_ID);
			st.setInt(1, quantity);
			st.setInt(2, userId);
			st.setInt(3, cartId);
			int result = st.executeUpdate();
			if (result > 0) {
				PreparedStatement st2 = con.prepareStatement(StringUtils.CART_VIEW_OFF_BY_USER_ID);
				st2.setInt(1, userId);
				st2.setInt(2, cartId);
				st2.executeUpdate();
				PreparedStatement st3 = con.prepareStatement(StringUtils.UPDATE_CART_QUANTITY_USER_ID);
				st3.setInt(1, quantity);
				st3.setInt(2, userId);
				st3.setInt(3, cartId);
				st3.executeUpdate();
				PreparedStatement st4 = con.prepareStatement(StringUtils.UPDATE_PRODUCT_QUANTITY_ON_ORDER);
				st4.setInt(1, cartId);
				st4.setInt(2, userId);
				st4.executeUpdate();
				System.out.println("\nThe Carts " + cartId + " of " + userId + " Ordered Successfully!");
				return 0; // Success
			} else {
				System.out.println("\nFailed to Order the Cart " + cartId + " of " + userId + "!");
				return 1; // Failure
			} // Return 1 if Product is delete successfully, otherwise 0
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Return -1 for any exceptions
		}
	}

	public int orderAllProducts(int userId) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.ORDER_ALL_BY_USER_ID);
			st.setInt(1, userId);
			int result = st.executeUpdate();
			if (result > 0) {
				PreparedStatement st2 = con.prepareStatement(StringUtils.CART_VIEW_OFF_BY_USER_ID);
				st2.setInt(1, userId);
				st2.executeUpdate();
				System.out.println("\nAll Carts of " + userId + " Ordered Successfully!");
				return 0; // Success
			} else {
				System.out.println("\nFailed to Order the Product details of " + userId + "!");
				return 1; // Failure
			} // Return 1 if Product is delete successfully, otherwise 0
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1; // Return -1 for any exceptions
		}
	}

	// to get all the products from the database to display them
	public List<OrderModel> getAllOrders() {
		List<OrderModel> orders = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(StringUtils.GET_ALL_ORDERS);
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				OrderModel order = new OrderModel();
				order.setOrderId(rs.getInt("orderId"));
				order.setCustomerName(rs.getString("Username"));
				order.setProductName(rs.getString("productName"));
				order.setQuantity(rs.getInt("quantity"));
				order.setTotalPrice(rs.getInt("totalPrice"));
				order.setProductPrice(rs.getInt("price"));
				order.setOrderDate(rs.getString("orderDate"));
				order.setOrderStatus(rs.getString("orderStatus"));

				orders.add(order);
			}
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
		}
		return orders;
	}

	public List<OrderModel> getSpecifiUserOrders(int userId) {
		List<OrderModel> orders = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(StringUtils.GET_SPECIFIC_USER_ORDERS)) {
			// Set the user ID parameter in the prepared statement
			st.setInt(1, userId);

			// Execute the query
			try (ResultSet rs = st.executeQuery()) {
				// Iterate through the result set
				while (rs.next()) {
					// Create a new OrderModel object for each row
					OrderModel order = new OrderModel();
					// Set the attributes of the OrderModel object using data from the result set
					order.setProductName(rs.getString("productName"));
					order.setOrderDate(rs.getString("orderDate"));
					order.setQuantity(rs.getInt("quantity"));
					order.setProductPrice(rs.getInt("price"));
					order.setTotalPrice(rs.getInt("totalPrice"));
					order.setOrderStatus(rs.getString("orderStatus"));

					// Add the OrderModel object to the list
					orders.add(order);
				}
			}
		} catch (SQLException | ClassNotFoundException ex) {
			// Handle any SQL or class loading exceptions
			ex.printStackTrace(); // Log the exception for debugging
		}
		// Return the list of OrderModel objects
		return orders;
	}

	public int updateOrderStatus(int orderId, String orderStatus) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_ORDER_STATUS);
			st.setString(1, orderStatus); // Order id to update the Order Status
			st.setInt(2, orderId);
			int result = st.executeUpdate();
			return result > 0 ? 1 : 0;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
