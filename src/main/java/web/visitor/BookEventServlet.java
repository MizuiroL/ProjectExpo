package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdbc.ExpoDAO;
import model.Event;
import model.Exhibit;
import model.ExpoManager;
import model.Ticket;
import model.Visitor;
import service.ExpoManagerService;
import service.VisitorService;

import java.io.IOException;
import java.util.List;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Visitor visitor = (Visitor) session.getAttribute("visitor");
		Event event = (Event) session.getAttribute("event");
		Ticket ticket = visitorService.bookEvent(event, visitor);
		// TODO manage this with a try/catch
		if (ticket != null) {
			session.setAttribute("purchasedTicket", ticket);
			request.getRequestDispatcher("/ticket_purchased.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/failed_purchase.jsp").forward(request, response);
		}
		
	}

}
