package org.brisskit.i2b2.webapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.webapp.dao.UserDao;
import org.brisskit.i2b2.webapp.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** MyUserDetailsService
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class MyUserDetailsService implements UserDetailsService {

	private UserDao userDao;
	
	final static Logger logger = LogManager.getLogger(MyUserDetailsService.class);	
	

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		logger.info("MyUserDetailsService - loadUserByUsername username = " + username );
		
		// Programmatic transaction management
		/*
		return transactionTemplate.execute(new TransactionCallback<UserDetails>() {

			public UserDetails doInTransaction(TransactionStatus status) {
				com.mkyong.users.model.User user = userDao.findByUserName(username);
				List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

				return buildUserForAuthentication(user, authorities);
			}

		});*/
		
		org.brisskit.i2b2.webapp.model.User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		

	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(org.brisskit.i2b2.webapp.model.User user, List<GrantedAuthority> authorities) {
		logger.info("MyUserDetailsService - buildUserForAuthentication user = " + user + " authorities " + authorities);
		
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		logger.info("MyUserDetailsService - buildUserAuthority userRoles = " + userRoles );
		
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserDao getUserDao() {
		logger.info("MyUserDetailsService - getUserDao");
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		logger.info("MyUserDetailsService - setUserDao");
		this.userDao = userDao;
	}

}