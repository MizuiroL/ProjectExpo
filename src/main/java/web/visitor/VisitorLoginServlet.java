package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.LoginService;

import java.io.IOException;

import exceptions.UserNotFoundException;

/**
 * Servlet implementation class VisitorLoginServlet
 */
public class VisitorLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println("VisitorLoginServlet: email " + email + " password " + password);

		/*if (request.getSession(false) != null && request.getSession(false).getAttribute("loggedIn").equals(Boolean.FALSE)) {
			request.getRequestDispatcher("visitor_already_logged.jsp").forward(request, response);
		}*/
		try {
			HttpSession session = LoginService.visitorLogin(email, password, request);
		} catch (UserNotFoundException e) {
			request.getRequestDispatcher("failed_login.jsp").forward(request, response);
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("visitor/visitor_home.jsp").forward(request, response);
	}

}
