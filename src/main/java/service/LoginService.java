package service;

import exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jdbc.ExpoDAO;
import jdbc.LoginDAO;
import model.ExpoManager;
import model.Visitor;
import model.account.AccountType;

public class LoginService {
	public static HttpSession visitorLogin(String email, String password, HttpServletRequest request) throws UserNotFoundException {
		//System.out.println("LoginService: email " + email + " password " + password);
		Visitor visitor = new LoginDAO().login(email, password); // Throws UserNotFoundException
		ExpoManager expo = new ExpoDAO().getExpoById(1);
		HttpSession session = request.getSession();
		session.setAttribute("visitor", visitor);
		session.setAttribute("expo", expo);
		session.setAttribute("accountType", AccountType.VISITOR_ACCOUNT);
		//session.setAttribute("loggedIn", Boolean.FALSE);
		return session;
	}
}
