package web.visitor;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import model.ExpoManager;
import service.ExpoManagerService;

/**
 * Servlet implementation class FutureEventsServlet
 */
@WebServlet(urlPatterns = "/visitor/FutureEventsServlet")
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
		List<Event> eventList = expoService.getFutureEvents(expo);
		System.out.println(eventList.toString());
		request.setAttribute("eventList", eventList);
		request.getRequestDispatcher("/visitor/future_events.jsp").forward(request, response);
	}

}