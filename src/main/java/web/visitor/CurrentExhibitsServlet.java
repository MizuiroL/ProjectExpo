package web.visitor;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Exhibit;
import model.ExpoManager;
import service.ExpoManagerService;

/**
 * Servlet implementation class CurrentExhibitsServlet
 */
@WebServlet(urlPatterns = "/visitor/CurrentExhibitsServlet")
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

		request.getRequestDispatcher("/visitor/current_exhibits.jsp").forward(request, response);
	}

}
