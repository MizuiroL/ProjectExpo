package service;

import exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdbc.LoginDAO;
import model.Visitor;

public class LoginService {
	public static HttpSession visitorLogin(String email, String password, HttpServletRequest request) throws UserNotFoundException {
		System.out.println("LoginService: email " + email + " password " + password);
		Visitor visitor = new LoginDAO().login(email, password);
		HttpSession session = request.getSession();
		session.setAttribute("visitor", visitor);
		session.setAttribute("loggedIn", Boolean.FALSE);
		return session;
	}
}
