package service;

import jakarta.servlet.http.HttpSession;
import model.Visitor;

public class LoginService {
	public static Boolean visitorLogin(String email, String password, HttpSession session) {
		// TODO connect to database and check data
		Visitor visitor = new Visitor();
		session.setAttribute("visitor", visitor);
		return true;
	}
}
