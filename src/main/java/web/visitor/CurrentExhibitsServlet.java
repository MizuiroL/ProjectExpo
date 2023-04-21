package web.visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdbc.ExpoDAO;
import model.Exhibit;
import model.ExpoManager;
import service.ExpoManagerService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class CurrentExhibitsServlet
 */
public class CurrentExhibitsServlet extends HttpServlet {
	ExpoManagerService expoService;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentExhibitsServlet() {
        super();
        expoService = new ExpoManagerService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ExpoManager expo = expoService.findExpo(1);
		//ExpoManager expo = new ExpoDAO().getExpoById(1);
		List<Exhibit> exhibitList = expoService.getPresentExhibits(expo);
		System.out.println(exhibitList.toString());
		request.setAttribute("exhibitList", exhibitList);
		//List<Exhibit> e = (List<Exhibit>) request.getAttribute("exhibitList");
		request.getRequestDispatcher("/current_exhibits.jsp").forward(request, response);
	}

}
