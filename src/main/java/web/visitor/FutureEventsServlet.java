package web.visitor;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import model.Exhibit;
import model.ExpoManager;
import model.Visitor;
import service.ExpoManagerService;
import service.VisitorService;

public class FutureEventsServlet extends HttpServlet {
	ExpoManagerService expoService;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FutureEventsServlet() {
        super();
        expoService = new ExpoManagerService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ExpoManager expo = expoService.findExpo(1);
		Visitor visitor = new VisitorService().findVisitor("ABC"); //TODO get visitor with session
		List<Event> eventList = expoService.getBookedEvents(expo, visitor);
		System.out.println(eventList.toString());
		request.setAttribute("eventList", eventList);
		request.getRequestDispatcher("/booked_events.jsp").forward(request, response);
	}

}