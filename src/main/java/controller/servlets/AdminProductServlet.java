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

@WebServlet(asyncSupported = true, urlPatterns = StringUtils.ADMIN_PRODUCT_SERVLET)
public class AdminProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController dbController = new DatabaseController();

    public AdminProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all user details from the database
        List<ProductModel> products = dbController.getAllProducts();

        // Set the list of students as an attribute in the request object
        request.setAttribute("products", products);

        // Forward the request to the students.jsp page
        request.getRequestDispatcher(StringUtils.ADMIN_PAGE).forward(request, response);
    }
}
