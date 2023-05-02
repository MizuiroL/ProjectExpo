package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SignupService;

import java.io.IOException;

/**
 * Servlet implementation class VisitorSignupServlet
 */
public class VisitorSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fiscalCode = request.getParameter("fiscalCode");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		
		SignupService.signupVisitor(email, password, fiscalCode, name, surname);
		
		request.getRequestDispatcher("visitor_login.html").forward(request, response);
	}

}
