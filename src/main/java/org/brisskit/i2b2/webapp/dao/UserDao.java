package org.brisskit.i2b2.webapp.dao;

import java.util.List;
import org.brisskit.i2b2.webapp.model.User;

/** UserDao
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public interface UserDao {

	User findByUserName(String username);
	boolean createUser(String username, String password);
	boolean changePassword(String username, String password, String newpassword);
	boolean sendNewPassword(String username);
	public List<User> viewAllUsers();
}