package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import exception.DataServiceException;
import models.User;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class UserDataService implements DataAccessInterface<User> {

	// Instantiate connection code
	Connection conn = null;
	String url = "jdbc:mysql://localhost:8889/WeatherApp";
	String username = "root";
	String password = "root";

	/**
	 * This method will return a list of all the users in the database. A SQL
	 * statement will join the Credential and User table in order to grab user name
	 * and Password as well.
	 *
	 * @return list - List(Type User) Class (List of all users from the database.)
	 */
	@Override
	public List<User> findAll() {
		// create SQL statement to grab all the users from the database
		String sql = "SELECT users.ID, users.FIRSTNAME, users.LASTNAME, users.EMAIL, credentials.USERNAME, credentials.PASSWORD"
				+ " FROM credentials" + " INNER JOIN users ON users.credentials_ID = credentials.ID;";
		// create a list of type users
		List<User> users = new ArrayList<User>();

		try {
			// connect to the database
			conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// run the SQL statement and store the result set
			ResultSet rs = stmt.executeQuery(sql);
			// loop through the result set, create a new User based on the information in
			// the result set and add it to the list
			while (rs.next()) {
				users.add(new User(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
						rs.getString("EMAIL"), rs.getString("USERNAME"),
						rs.getString("PASSWORD")));
			}
		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return users;
	}

	/**
	 * This method will return a specific user from the database. It will select a
	 * user from the users table where ID is equal to the ID sent to the method.
	 * 
	 * @param id - Integer Class (ID of the user that will be returned.)
	 * @return User Class - (User that corresponds to the ID sent.)
	 */
	@Override
	public User findById(int id) {
		String sql = String.format("SELECT * FROM `users` WHERE `users`.`ID` = %d", id);
		User returnUser = null;

		try {
			// connect to the database
			conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// run the SQL statement and store the result set
			ResultSet rs = stmt.executeQuery(sql);
			// loop through the result set, create a new User based on the information in
			// the result set and add it to the list
			while (rs.next()) {
				returnUser = new User(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
						rs.getString("EMAIL"), rs.getString("USERNAME"),
						rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return returnUser;
	}

	/**
	 * This method will write a User to the database. First it will write into the
	 * credentials table, grab the generated credentials ID in order to write a
	 * user.
	 * 
	 * @param user     - User Class (User model that will be added to the database)
	 * @param uniqueId - Integer Class (This feature was forced to be here since it
	 *                 is only used in RecipeDataService)
	 * @return Integer Class (The value that results from executeUpdate() to see if
	 *         there was actual change in the database.)
	 */

	@Override
	public int create(User user, int uniqueId) {
		// create SQL statement to insert into the credentials table
		String sqlCredentials = String.format("INSERT INTO credentials(USERNAME, PASSWORD) VALUES('%s', '%s')",
				user.getCredentials().getUsername(), user.getCredentials().getPassword());
		// create SQL statement to get the credential ID
		String sqlGetCredentialID = String.format(
				"SELECT `credentials`.`ID` FROM `credentials` WHERE `credentials`.`USERNAME` = '%s';",
				user.getCredentials().getUsername());
		// create rowsAffected and credentialId integers
		int rowsAffected = -1;
		int credentialId = -1;

		try {
			// connect to database
			conn = DriverManager.getConnection(url, username, password);
			// create statement
			Statement stmt = conn.createStatement();
			// Run the SQL code
			rowsAffected = stmt.executeUpdate(sqlCredentials);
			ResultSet rs = stmt.executeQuery(sqlGetCredentialID);
			while (rs.next()) {
				credentialId = rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataServiceException(e);
		}

		// create SQL statement to add to users
		String sqlUsers = String.format(
				"INSERT INTO users(FIRSTNAME, LASTNAME, EMAIL, credentials_ID) VALUES('%s', '%s', '%s', %d)",
				user.getFirstName(), user.getLastName(), user.getEmail(), credentialId);

		try {
			// connect to database
			conn = DriverManager.getConnection(url, username, password);
			// create statement
			Statement stmt = conn.createStatement();
			rowsAffected = stmt.executeUpdate(sqlUsers);

		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return rowsAffected;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int update(User t, int id) {
		// Use logic from update() in recipedataservice
		return -1;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int delete(User t) {
		// Use logic from delete() in recipedataservice
		return -1;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int getUniqueId(User t) {
		// TODO Auto-generated method stub
		return -1;
	}

}
