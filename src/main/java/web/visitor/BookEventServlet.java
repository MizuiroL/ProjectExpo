package web.visitor;

import jakarta.servlet.ServletException;
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

/**
 * Servlet implementation class BookEventServlet
 */
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		Visitor visitor = (Visitor) session.getAttribute("visitor");
		Event event = (Event) session.getAttribute("event");
		ExpoManager expo = (ExpoManager) session.getAttribute("expo");
		
		Ticket ticket = visitorService.bookEvent(expo, event, visitor);
		// TODO manage this with a try/catch
		if (ticket != null) {
			session.setAttribute("purchasedTicket", ticket);
			request.getRequestDispatcher("/ticket_purchased.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/failed_purchase.jsp").forward(request, response);
		}
		
	}

}
