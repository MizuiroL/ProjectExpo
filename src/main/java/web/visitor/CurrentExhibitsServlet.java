package web.visitor;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    
	

	@Override
	public void init(ServletConfig config) throws ServletException {
        expoService = new ExpoManagerService();
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ExpoManager expo = (ExpoManager) session.getAttribute("expo");

		List<Exhibit> exhibitList = expoService.getPresentExhibits(expo);
		System.out.println(exhibitList.toString());
		request.setAttribute("exhibitList", exhibitList);

		request.getRequestDispatcher("/current_exhibits.jsp").forward(request, response);
	}

}
