package org.brisskit.i2b2.webapp.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.brisskit.i2b2.webapp.dao.UserDao;
import org.brisskit.i2b2.webapp.model.User;

/** UserServiceImp
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

@Service
public class UserServiceImp  implements UserService {

	
	private UserDao userDao;
	
	final static Logger logger = LogManager.getLogger(UserService.class);	
	
	public UserDao getUserDao() {
		logger.info("UserServiceImp - getUserDao");
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		logger.info("UserServiceImp - setUserDao");
		this.userDao = userDao;
	}
	
	@Transactional
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return userDao.createUser(user.getUsername(), user.getPassword());
		
	}
	
	@Transactional
	public boolean changeUserPassword(String currentUser, String hashedPassword, String newPassword){
		return userDao.changePassword(currentUser, hashedPassword, newPassword);
		
	}

	@Transactional
	public boolean sendNewPassword(String username) {
		// TODO Auto-generated method stub
		return userDao.sendNewPassword(username);
	}
	
	@Transactional
	public List<User> viewAllUsers() {
		// TODO Auto-generated method stub
		return userDao.viewAllUsers();
	}
	
}