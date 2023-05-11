package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Event;
import model.ExpoManager;
import model.Ticket;
import model.Visitor;
import service.VisitorService;

import java.io.IOException;

import exceptions.EventAlreadyBookedException;

/**
 * Servlet implementation class BookEventServlet
 */
@WebServlet(urlPatterns = "/visitor/BookEventsServlet")
public class BookEventServlet extends HttpServlet {
	VisitorService visitorService;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookEventServlet() {
		super();
		visitorService = new VisitorService();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		Visitor visitor = (Visitor) session.getAttribute("visitor");
		Event event = (Event) session.getAttribute("event");
		ExpoManager expo = (ExpoManager) session.getAttribute("expo");

		Ticket ticket = null;
		try {
			ticket = visitorService.bookEvent(expo, event, visitor);

			if (ticket != null) {
				session.setAttribute("purchasedTicket", ticket);
				request.getRequestDispatcher("/visitor/ticket_purchased.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/visitor/failed_purchase.jsp").forward(request, response);
			}
			
		} catch (EventAlreadyBookedException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/visitor/failed_purchase.jsp").forward(request, response);
		}

	}

}
