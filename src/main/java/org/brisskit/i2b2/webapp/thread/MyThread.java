package org.brisskit.i2b2.webapp.thread;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.I2B2Project;
import org.brisskit.i2b2.UploaderException;
import org.brisskit.i2b2.webapp.model.Projects;
import org.brisskit.i2b2.webapp.service.ProjectsService;
import org.brisskit.i2b2.webapp.utils.SendMailTLS;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/** MyThread
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

@Component
@Scope("prototype")
public class MyThread implements Runnable{
	 
	final static Logger logger = LogManager.getLogger(MyThread.class);	
	
	String name;
	File spreadsheetFile;
	String project_name;
	String user;
	private ProjectsService projectsService;
	private boolean isNewProject;
	String referer;
	
 
	public MyThread(File spreadsheetFile, String project_name, String user, ProjectsService projectsService, boolean isNewProject, String referer){
		//this.name = name;
		this.spreadsheetFile = spreadsheetFile;
		this.project_name = project_name;
		this.user = user;
		this.projectsService = projectsService;
		this.isNewProject = isNewProject;
		this.referer = referer;
		
	}
	
	@Override
	public void run() {
 
		logger.info("THREAD start" + project_name + " is running");
 
		I2B2Project project = null;
		
		try {				
				logger.info("THREAD " + project_name + " login uploadFile 6 ");
			
				project = I2B2Project.Factory.newInstance( project_name ) ;
				
				logger.info("THREAD " + project_name + " login uploadFile 7 ");
				project.processSpreadsheet( spreadsheetFile ) ;
				project.dispose() ;
				project = null ;
							
				String url_webapp = "";
				String url_i2b2 = "";
				
				URL url;
				try {
					url = new URL(referer);
					String protocol = url.getProtocol();
		            String host = url.getHost();
		            int port = url.getPort();
		            String path = url.getPath();
		             
		            logger.info("URL created: " + url);
		            logger.info("protocol: " + protocol);
		            logger.info("host: " + host);
		            logger.info("port: " + port);
		            logger.info("path: " + path);
		            
		            url_webapp = protocol + "://" + host;
		            if (port != -1) { url_webapp += ":" + port; }
		            url_webapp += "/i2b2UploaderWebapp";
		            
		            url_i2b2 = protocol + "://" + host;
		            //if (port != -1) { url_i2b2 += ":" + port; }
		            url_i2b2 += "/i2b2/webclient_brisskit/";
		            

				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}

				
				logger.info("THREAD " + project_name + " login uploadFile 8 ");
				
				if (isNewProject)
				{
				Projects p = new Projects(project_name, user);
				logger.info("THREAD " + project_name + " login uploadFile 12 project_name = " + project_name + " user =  " + user + " p = " + p);
				projectsService.createProject(p);
				//projectsService = null;
				logger.info("THREAD " + project_name + " login uploadFile 13 ");
				
					SendMailTLS.sendMail("Brisskit i2b2 Portal [" + project_name + "]", user, 
							             "Successfully created project " + project_name + " and uploaded the file " + spreadsheetFile.getName() + " into it. " + "\n\n" +
					                     "Brisskit portal - " + url_webapp + "\n\n" +
					                     "i2b2 - " + url_i2b2 + "\n\n" + 
					                     "Thank you. BRISSKit Team.");
			    
				}
				else
				{
					SendMailTLS.sendMail("Brisskit i2b2 Portal [" + project_name + "]", user, 
				             "Successfully updated project " + project_name + " and uploaded the file " + spreadsheetFile.getName() + " into it. " + "\n\n" +
		                     "Brisskit portal - " + url_webapp + "\n\n" +
		                     "i2b2 - " + url_i2b2 + "\n\n" + 
		                     "Thank you. BRISSKit Team.");
				    
				}
				
				logger.info("THREAD " + project_name + " login uploadFile 14 ");
						
		} 
		catch( UploaderException cex ) 
		{ 
			logger.info(project_name + " login uploadFile UploaderException *" + cex.getMessage() + "*" + cex.getLocalizedMessage() + "*" + cex.toString());
			SendMailTLS.sendMail("Brisskit i2b2 Portal [" + project_name + "]", user, 
		             "Failed to upload the file " + spreadsheetFile.getName() + " into the project " + project_name + ". " + "\n\n" + 
		             cex.getMessage() + "\n\n" +
				 	 cex.getLocalizedMessage() + "\n\n" +
					 cex.toString() + "\n\n" +
                     "Thank you. BRISSKit Team.");	
			
			logger.info("XXX1");
			
			/*
			if (project !=null)
			{
				try {
					project.dispose() ;
					project = null ;
					//projectsService = null;
				} catch (UploaderException e) {
					logger.info("XXX4");
				}		
			}
			
			try {
				I2B2Project.Factory.delete(project_name);
				//projectsService = null;
			} catch (UploaderException e) {
				logger.info("XXX6");
			}		
			*/
			
		}	
		logger.info(project_name + " end is running");
	}

	public void setName(String string) {
		// TODO Auto-generated method stub
		this.name = string;
	}
 
}
