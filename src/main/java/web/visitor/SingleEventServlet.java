package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Event;
import service.EventService;

import java.io.IOException;

/**
 * Servlet implementation class SingleEventServlet
 */
@WebServlet(urlPatterns = "/visitor/SingleEventsServlet")
public class SingleEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer eventId = Integer.valueOf(request.getParameter("eventId")); // TODO surround with try/catch?
		Event event = new EventService().findEvent(eventId);
		
		HttpSession session = request.getSession();
		session.setAttribute("event", event);
		
		request.getRequestDispatcher("/visitor/purchasable_event.jsp").forward(request, response);
	}

}
