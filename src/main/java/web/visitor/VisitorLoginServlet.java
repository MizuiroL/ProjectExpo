package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.LoginService;

import java.io.IOException;

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

		if (request.getSession(false) != null) {
			// TODO Errore sessione già esistente, ridireziona ad appropriata pagina di
			// errore
		}

		HttpSession session = request.getSession();
		Boolean success = LoginService.visitorLogin(email, password, session);
		if (!success) {
			session.invalidate();
			// TODO Errore accesso fallito, forward pagina apposta
		}

		// TODO forward alla homepage del visitor
	}

}
