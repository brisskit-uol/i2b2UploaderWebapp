package org.brisskit.i2b2.webapp.service;

import java.util.List;

import org.brisskit.i2b2.webapp.model.User;

/** UserService
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public interface UserService {
	public boolean addUser(User user);
    public boolean changeUserPassword(String currentUser, String hashedPassword, String Password);
	public boolean sendNewPassword(String username);
	public List<User> viewAllUsers();
}