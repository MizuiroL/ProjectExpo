package service;

import jdbc.LoginDAO;
import model.Visitor;

// TODO make this a EJB and remove static keyword
public class SignupService {
	public static void signupVisitor(String email, String password, String fiscalCode, String name,String surname) {
		Visitor visitor = new Visitor();
		visitor.setFiscalCode(fiscalCode);
		visitor.setName(name);
		visitor.setSurname(surname);
		visitor.setEmail(email);
		
		new LoginDAO().createVisitor(fiscalCode, name, surname, email, password);
	}
}
