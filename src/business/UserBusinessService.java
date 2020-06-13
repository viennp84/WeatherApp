package business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import business.UserBusinessInterface;
import data.DataAccessInterface;
import models.Credentials;
import models.User;

/**
 * This is the business service for the User model, all the logic that connects
 * the controller and the database is used in here.
 * 
 *
 */
@Stateless
@Local(UserBusinessInterface.class)
@Alternative
public class UserBusinessService implements UserBusinessInterface {

	// Initialize the User list
	private List<User> users;

	// Inject the dataSerice
	@Inject
	DataAccessInterface<User> userDataService;

	/**
	 * Default Constructor, creates an instance of a list of type User
	 */
	public UserBusinessService() {
		users = new ArrayList<User>();
	}

	/**
	 * This method will use comparing logic and return true or false if the
	 * credentials that were sent equal to the credentials in the database.
	 * 
	 * @param user - User Class - (User that will be used in the login
	 *             authentication logic.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onLogin(User user) {
		// create session
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// test if User name is passed correctly
		Boolean validate = false;

		// get list of users from database
		users = userDataService.findAll();

		// validate information
		for (int i = 0; i < users.size(); i++) {
			if (user.getCredentials().getUsername().equals(users.get(i).getCredentials().getUsername())
					&& user.getCredentials().getPassword().equals(users.get(i).getCredentials().getPassword())) {
				Credentials c = new Credentials(users.get(i).getFirstName(), users.get(i).getLastName(),
						users.get(i).getID());
				user.setCredentials(c);
				session.setAttribute("userCred", c);
				user.setFirstName(users.get(i).getFirstName());
				user.setLastName(users.get(i).getLastName());
				user.setEmail(users.get(i).getEmail());
				user.setID(users.get(i).getID());
				
				validate = true;
				break;
			}
		}
		return validate;
	}

	/**
	 * This method will register(write) a User to the database.
	 * 
	 * @param user - User Class - (User that will be added to the database.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onRegister(User user) {
		// get the user
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
		// call the dataService to create a user
		if (userDataService.create(user, -1) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will return a list of all the users.
	 * 
	 * @return list - List(Type User) Class (List of all users in the database.)
	 */
	@Override
	public List<User> getUsers() {
		return userDataService.findAll();
	}
}
