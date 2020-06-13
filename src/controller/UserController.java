package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import business.UserBusinessInterface;
import models.Credentials;
import models.User;

/**
 * This is the controller class for the User where it handles the front end page
 * distribution, the methods in this class accept (or don't accept) data and
 * based on the outcome the methods will return a string of the page it is
 * directed.
 * 
 * 
 *
 */
@ManagedBean
@ViewScoped
public class UserController {

	// inject beans
	@Inject
	UserBusinessInterface userService;

	/**
	 * This method will grab the information from the register form and store it in
	 * a user model so it can be displayed in the RegistrationSuccessful form.
	 * 
	 * @param user - User class (The user model that is being registered to the
	 *             web-site which will be added to the database)
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onRegister(User user) {
		try {
			// call business service to register and store outcome in a boolean object
			boolean outcome = userService.onRegister(user);
			// if outcome true go to success/ false unsuccessful
			if (outcome) {
				return "registrationSuccessful.xhtml";
			} else {
				return "unsuccsessfulLogin.xhtml";
			}
			// if there is a database error catch it with custom exception
		} catch (Exception e) {
			return "error.xhtml";
		}
	}

	/**
	 * This method will grab the information from the login form, it will create a
	 * user model with the inputed username and password will compare to username
	 * and password in the database and if the inputed one equals it it will display
	 * a welcome message on LoginSuccessful form.
	 * 
	 * @param user - User Class (User model that is going to go through the
	 *             authentication logic.)
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onLogin(User user) {

		// Create session variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// put user on the page
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);

		try {
			// login user using the businessService
			if (userService.onLogin(user) == true) {
				// set user credentials to the user
				session.setAttribute("userCred", user.getCredentials());
				Credentials c = (Credentials) session.getAttribute("userCred");
				System.out.println("in User Controller : onLogin() user: " + c.getUsername());
				if (c.getID() == 2) {
					return "homeAdmin.xhtml";
				} else {
					return "successfulLogin.xhtml";
				}
			} else {
				// display unsuccessful page
				return "unsuccsessfulLogin.xhtml";
			}
			// if there is a database error catch it with custom exception
		} catch (Exception e) {
			return "error.xhtml";
		}

	}

	/**
	 * This method is called to end the session variable (this will be important
	 * later when I implement the logic so a user cannot access a page by simply
	 * putting in a URL)
	 * 
	 * @return String - Path to login page.
	 */
	public String onLogout() {
		/*
		 * // Create session variable HttpSession session = (HttpSession)
		 * FacesContext.getCurrentInstance().getExternalContext().getSession(false); //
		 * set the credentials in session to null session.setAttribute("userCred",
		 * null);
		 */
		return "loginForm.xhtml";
	}

	/**
	 * This is a method to go to register page
	 * 
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onSubmitRegister() {
		return "registrationForm.xhtml";
	}

	/**
	 * This is a method to go to the login page
	 * 
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onSubmitLogin() {
		System.out.println("In onSubmitLogin()");
		return "loginForm.xhtml";
	}

	/**
	 * This method will return the user business interface once called.
	 * 
	 * @return UserBusinessInterface Class - (Business Service)
	 */
	public UserBusinessInterface getUserService() {
		return userService;
	}
}
