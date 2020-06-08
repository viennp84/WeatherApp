package business;

import java.util.List;

import models.User;

public interface UserBusinessInterface {

	// Interface methods

	/**
	 * This method will return a list of all the users.
	 * 
	 * @return list - List(Type User) Class (List of all users in the database.)
	 */
	public List<User> getUsers();

	/**
	 * This method will register(write) a User to the database.
	 * 
	 * @param user - User Class - (User that will be added to the database.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onRegister(User user);

	/**
	 * This method will use comparing logic and return true or false if the
	 * credentials that were sent equal to the credentials in the database.
	 * 
	 * @param user - User Class - (User that will be used in the login
	 *             authentication logic.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onLogin(User user);

}