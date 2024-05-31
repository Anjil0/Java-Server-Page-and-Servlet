package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DatabaseController;
import util.StringUtils;
import model.FeedbackModel;
/**
 * Servlet implementation class FeedbackDisplayServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.FEEDBACK_DISPLAY_SERVLET })
public class FeedbackDisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseController dbController = new DatabaseController();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackDisplayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Fetch feedback data from the database
	    List<FeedbackModel> feedbacks = dbController.getAllFeedbacks();
	    
	    // Set the feedbacks as an attribute in the request scope
	    request.setAttribute("feedbacks", feedbacks);
	    
	    // Forward the request to the JSP page
	    request.getRequestDispatcher(StringUtils.FEEDABACK_PAGE).forward(request, response);
	}
}
