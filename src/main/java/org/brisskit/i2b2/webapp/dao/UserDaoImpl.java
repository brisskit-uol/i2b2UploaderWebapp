package org.brisskit.i2b2.webapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.webapp.utils.PasswordGen;
import org.brisskit.i2b2.webapp.utils.SendMailTLS;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.brisskit.i2b2.webapp.model.User;
import org.brisskit.i2b2.webapp.model.UserRole;

/** UserDaoImpl
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	final static Logger logger = LogManager.getLogger(UserDaoImpl.class);	
	
	public SessionFactory getSessionFactory() {
		logger.info("UserDaoImpl - getSessionFactory");
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		logger.info("UserDaoImpl - setSessionFactory");
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession() {
		 Session session = null;
		 try{
		     session = getSessionFactory().getCurrentSession();
		 }catch(HibernateException hex){
		     hex.printStackTrace();
		 }
		 if(session == null /*&& !((SessionFactory) session).isClosed()*/){
		     session = sessionFactory.openSession();
		 }
		return session;
		}

	
	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		logger.info("UserDaoImpl - findByUserName username = " + username );
		
		List<User> users = new ArrayList<User>();

		//getSessionFactory().getCurrentSession().createQuery("set schema i2b2portal");
		
		
		users = getSessionFactory().getCurrentSession().createQuery("from User where username=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			logger.info("UserDaoImpl - findByUserName users.size() = " + users.size() );			
			return users.get(0);
		} else {
			logger.info("UserDaoImpl - findByUserName users.size() = null" );			
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	public boolean sendNewPassword(String username)
	{
		Session session =   getSession(); //getSessionFactory().openSession();
		//User u = new User(username, password, true);
		
		List<User> users = new ArrayList<User>();

		//getSessionFactory().getCurrentSession().createQuery("set schema i2b2portal");
		
		
		users = session.createQuery("from User where username=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			logger.info("UserDaoImpl - sendNewPassword users.size() = " + users.size() );		
			
			
			
			try{
				String newPass = PasswordGen.generateRandomPassword();
				
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(newPass);
				
			    Query query = session.createQuery(" update User set password = '"+ hashedPassword + "' where username='" + username + "'");
			    query.executeUpdate();
			    
			    SendMailTLS.sendMail("Password Reset - Brisskit i2b2 Portal", username, "Your new password is " + newPass + "\n\n" + "Please reset when you log in.");
			    ////session.close();
			    
			    return true;
			    
			}
			catch (HibernateException e) { return false;}
			
			//return users.get(0);
		} else {
			logger.info("UserDaoImpl - sendNewPassword users.size() = null" );		
			////session.close();
			//return null;
			return false;
		}
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean changePassword(String username, String password, String newpassword)
	{
		Session session =   getSession(); //getSessionFactory().openSession();
		//User u = new User(username, password, true);
		
		List<User> users = new ArrayList<User>();

		logger.info("changePassword - username = " + username );	
		logger.info("changePassword - password = " + password );	
		
		//users = session.createQuery("from User where username=? and password=?")
		//		.setParameter(0, username).setParameter(1, password).list();
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(newpassword);
		
		try{
	    Query query = session.createQuery(" update User set password = '"+ hashedPassword + "' where username='" + username + "'");
	    query.executeUpdate();
	    ////session.close();
		}
		catch (HibernateException e) 
		{ 
			////session.close(); 
		return false;
		}
	    
	    
	    
	    //tx.commit();
	    
/*		
		Query query = session.createQuery("update Stock set stockName = 'DIALOG2'" +
				" where stockCode = '7277'");
int result = query.executeUpdate();
*/

/*		if (users.size() > 0) {
			logger.info("changePassword - users.size() = " + users.size() );			
			User u = users.get(0);			
		} else {
			logger.info("changePassword - users.size() = 0");	
			return false;
		}
*/		
		//User u2 = findByUserName(username);
		
		//if (u2.getPassword().equals(password))
		//{
		//	return true;
		//}
					
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean createUser(String username, String password) {
		//getSessionFactory().getCurrentSession().createQuery("from User where username=?").setParameter(0, username).list();
		Session session =   getSession(); //getSessionFactory().openSession();
		//getSessionFactory().getCurrentSession().createQuery("set schema i2b2portal");
		
		// check if email exists
		
		try {
			User u = new User(username, password, true);
			UserRole ur1 = new UserRole(u,"ROLE_USER");
			UserRole ur2 = new UserRole(u,"ROLE_ADMIN");
			//Save the employee in database
	        session.save(u);
	        session.save(ur1);
	        session.save(ur2);
	        ////session.close();
	        
	        return true;
	 
		}
		catch (HibernateException e) { return false;}
    
		
		
		/*
		
        String hql = "select * FROM i2b2pm.pm_project_data";
        Query findQuery = session.createSQLQuery(hql);
        //List result = findQuery.list();
        
        List<Object[]> entities = findQuery.list();
        for (Object[] entity : entities) {
            for (Object entityCol : entity) {
            	logger.info(" " + entityCol);
            }
            logger.info("");
        }
        
        */
        
        //logger.info(result.get(0));
        //Commit the transaction
        //session.getTransaction().commit();
        //HibernateUtil.shutdown();
	}

	@SuppressWarnings("unchecked")
	public List<User> viewAllUsers() {
		// TODO Auto-generated method stub
		Session session =   getSession(); //getSessionFactory().openSession();
		
		List<User> users = new ArrayList<User>();

		//getSessionFactory().getCurrentSession().createQuery("set schema i2b2portal");
		
		users = session.createQuery("from User").list();
		////session.close();
		return users;
	}
	
	
}