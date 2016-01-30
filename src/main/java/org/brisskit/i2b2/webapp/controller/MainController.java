package org.brisskit.i2b2.webapp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.I2B2Project;
import org.brisskit.i2b2.webapp.dao.UploadedFile;
import org.brisskit.i2b2.webapp.model.ConceptDimension;
import org.brisskit.i2b2.webapp.model.ObservationFact;
import org.brisskit.i2b2.webapp.model.OntMappings;
import org.brisskit.i2b2.webapp.model.Ontology;
import org.brisskit.i2b2.webapp.model.PatientMapping;
import org.brisskit.i2b2.webapp.model.Projects;
import org.brisskit.i2b2.webapp.model.User;
import org.brisskit.i2b2.webapp.service.ProjectsService;
import org.brisskit.i2b2.webapp.service.UserService;
import org.brisskit.i2b2.webapp.thread.ThreadExecutor;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/** MainContoller for the uploader app
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

@Controller
public class MainController {

	/**  
	* Service for all user related functions
	*/
	@Autowired
	private UserService userService;

	/**  
	* Service for all project related functions
	*/
	@Autowired
	private ProjectsService projectsService;

	/**  
	* Thread queue for file uploads
	*/
	@Autowired
	private ThreadExecutor threadExecutor;

	/**  
	* logger
	*/
	final static Logger logger = LogManager.getLogger(MainController.class);

	/**  
	* treemap for snomed terms
	*/
	static ConcurrentNavigableMap<String, String> treeMap;

	/**  
	* This creates the snomed database in memory
	*/
	static {
		DB db = DBMaker.newMemoryDB().make();
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(MainController.class.getResourceAsStream("/SNOMED_1_IN_4.txt")));

			String line;
			treeMap = db.getTreeMap("map");
			while ((line = r.readLine()) != null) {
				String[] split = line.split("\\s", 2);
				treeMap.put(split[0], split[1]);
			}
		} catch (IOException e) {
			logger.info("error generating snomed");
		}

		db.commit();
		// db.close();
	}

	/**  
	* Initial forward
	*
	*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView forward() {
		logger.info("login forward");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;

	}

	/**  
	* Welcome forward
	*
	*/
	@RequestMapping(value = "/welcome**", method = RequestMethod.GET)
	public ModelAndView defaultPage() {
		logger.info("welcome");
		ModelAndView model = new ModelAndView();
		model.setViewName("dashboard");
		return model;

	}

	/**  
	* Called when user logs out
	*
	*/
	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "logout", required = false) String logout,
							  HttpServletRequest request) {

		logger.info("login error = " + error + " logout " + logout);

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}
	
	/**  
	* Upload XLS - New Project
	*
	*/
	@RequestMapping(value = "/np_uploadxls**", method = RequestMethod.GET)
	public ModelAndView np_uploadxls(Principal principal) {
		final String currentUser = principal.getName();
		logger.info("np_uploadxls user " + currentUser);
		ModelAndView model = new ModelAndView();
		model.addObject("user", currentUser);
		model.addObject("uploadtype", "np_uploadxls");
		model.setViewName("np_uploadxls");
		return model;
	}

	/**  
	*  Upload XLS - Existing Project
	*
	*/
	@RequestMapping(value = "/ex_uploadxls**", method = RequestMethod.GET)
	public ModelAndView ex_uploadxls(Principal principal) {

		List<Projects> projects = null;
		final String currentUser = principal.getName();
		
		logger.info("ex_uploadxls user " + currentUser);
		
		// Test if projectService is valid
		if (projectsService == null) {
			logger.info("ex_uploadxls projectsService is null ");
		} else {
			logger.info("ex_uploadxls projectsService is NOT null ");
		}
		
		projects = projectsService.viewAllProjects();

		ModelAndView model = new ModelAndView();
		model.addObject("projects", projects);
		model.addObject("user", currentUser);
		model.addObject("uploadtype", "ex_uploadxls");
		model.setViewName("ex_uploadxls");
		return model;
	}

	/**
	 * Upload single file and process within thread
	 * 
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping(value = "/uploadFile**", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@RequestParam(value = "project_name", required = false) String project_name,
										  @RequestParam(value = "user", required = false) String user,
										  @RequestParam(value = "uploadtype", required = false) String uploadtype,
										  @RequestHeader(value = "referer", required = false) final String referer,
										  @RequestParam("file") MultipartFile file) {
		

		logger.info("uploadFile - project_name = " + project_name + " user = " + user + " uploadtype = " + uploadtype + " referer =" + referer);
		
		ModelAndView model = new ModelAndView();
		model.addObject("user", user);
		model.addObject("uploadtype", uploadtype);
		model.setViewName(uploadtype);

		// are we uploading a spreadsheet from new project option or an existing project option
		
		if (uploadtype.equals("ex_uploadxls")) {
			List<Projects> projects = null;
			projects = projectsService.viewAllProjects();
			model.addObject("projects", projects);
		}

		// check is project name already exists
		Projects existing_p = projectsService.findByProjectID(project_name);

		logger.info("uploadFile existing_p " + existing_p);

		if (existing_p != null) {
			logger.info("uploadFile existing_p Id = " + existing_p.getId() + " ProjectId = " + existing_p.getProjectId() + " Username = " + existing_p.getUsername());			
			model.addObject("success", false);
			model.addObject("message", "project with same name already exists");

			if (uploadtype.equals("ex_uploadxls")) {
				try {
					File spreadsheetFile = multipartToFile(file);
					// spin up a thread
					threadExecutor.fireThread(spreadsheetFile, project_name, user, projectsService, false, referer);
					model.addObject("added_project", project_name);
					model.addObject("success", true);
					model.addObject("message","uploaded file " + file.getOriginalFilename()	+ ".<br />A notification email will be sent when complete.");
				} catch (IllegalStateException | IOException e) {
					logger.info("uploadFile exception "  + e.getStackTrace());			
				}
			}
		}

		if (existing_p == null) {
			try {
				File spreadsheetFile = multipartToFile(file);
				// spin up a thread
				threadExecutor.fireThread(spreadsheetFile, project_name, user,projectsService, true, referer);
				model.addObject("added_project", project_name);
				model.addObject("success", true);
				model.addObject("message","uploaded file "+ file.getOriginalFilename()	+ ".<br />A notification email will be sent when complete.");
			} catch (IllegalStateException | IOException e) {
				logger.info("uploadFile exception "  + e.getStackTrace());
				//e.printStackTrace();
			}
			
			model.addObject("filename", file.getOriginalFilename());
			model.addObject("filesize", Long.valueOf(file.getSize()).intValue());
		}

		return model;
	}

	/**  
	* Delete project
	*
	*/
	@RequestMapping(value = "/deleteproject**", method = RequestMethod.GET)
	public ModelAndView deleteproject(Principal principal,
									  @RequestParam(value = "project_name", required = false) String project_name) {
		
		final String currentUser = principal.getName();
		
		logger.info("deleteproject project_name = " + project_name + " user = " + currentUser); 
		ModelAndView model = new ModelAndView();

		try {

			if (project_name != null) {
				I2B2Project.Factory.delete(project_name);
				boolean flag = projectsService.deleteProject(project_name);

				if (flag) {
					model.addObject("message", "deleted project " + project_name);
				} else {
					model.addObject("message", "unable to delete project "+ project_name);
				}
			}

			List<Projects> projects = null;
			projects = projectsService.viewAllProjects();

			model.addObject("projects", projects);
			model.addObject("user", currentUser);
			model.addObject("uploadtype", "exportdata");
			model.setViewName("deleteproject");
		} catch (Exception e) {
			model.setViewName("logout");
		}
		return model;
	}

	/**  
	* Export Data
	*
	*/
	@RequestMapping(value = "/exportdata**", method = RequestMethod.GET)
	public ModelAndView exportdata(@RequestParam(value = "project_name", required = false) String project_name,
								   @RequestParam(value = "data_choice", required = false) String data_choice,
								   @RequestParam(value = "views_per_page", required = false) Integer views_per_page,
								   @RequestParam(value = "next_view", required = false) Integer next_view,
								   @RequestParam(value = "totalcount", required = false) Integer totalcount,
								   @RequestParam(value = "user", required = false) String user,
								   @RequestParam(value = "uploadtype", required = false) String uploadtype,
								   Principal principal) {

		List<Projects> projects = null;
		final String currentUser = principal.getName();
		projects = projectsService.viewAllProjects();

		ModelAndView model = new ModelAndView();

		logger.info("exportdata - project_name = " + project_name +
								" data_choice " + data_choice +
								" views_per_page " + views_per_page +
								" next_view " + next_view +
								" totalcount " + totalcount +
								" user " + user +
								" uploadtype " + uploadtype);

		try {
			
			// observations
			if (project_name != null && data_choice != null	&& data_choice.equals("observations")) {
				
				if (next_view == null) {
					next_view = 1;
				}

				List<ObservationFact> obs = null;
				obs = projectsService.getObs(project_name, views_per_page, next_view - 1);
				int count = projectsService.getObsCount(project_name);

				model.addObject("totalcount", count);

				int limit = (next_view + views_per_page);

				if (limit > count) {
					limit = count;
				}

				model.addObject("message", "Showing " + next_view + " to " + limit + " of " + count + " entries");
				model.addObject("next_view", next_view);
				model.addObject("data_choice", "observations");
				model.addObject("object", obs);
			}

			// patients
			if (project_name != null && data_choice != null	&& data_choice.equals("patients")) {

				if (next_view == null) {
					next_view = 1;
				}

				List<PatientMapping> obs = null;
				obs = projectsService.getPatients(project_name, views_per_page,	next_view - 1);
				int count = projectsService.getPatientsCount(project_name);

				model.addObject("totalcount", count);

				int limit = (next_view + views_per_page);

				if (limit > count) {
					limit = count;
				}

				model.addObject("message", "Showing " + next_view + " to " + limit + " of " + count + " entries");
				model.addObject("next_view", next_view);
				model.addObject("data_choice", "patients");
				model.addObject("object", obs);
			}

			// concepts
			if (project_name != null && data_choice != null	&& data_choice.equals("concepts")) {
				
				if (next_view == null) {
					next_view = 1;
				}

				List<ConceptDimension> obs = null;
				obs = projectsService.getConcepts(project_name, views_per_page,	next_view - 1);
				int count = projectsService.getConceptsCount(project_name);

				model.addObject("totalcount", count);

				int limit = (next_view + views_per_page);

				if (limit > count) {
					limit = count;
				}

				model.addObject("message", "Showing " + next_view + " to " + limit + " of " + count + " entries");
				model.addObject("next_view", next_view);
				model.addObject("data_choice", "concepts");
				model.addObject("object", obs);
			}

			// ontology
			if (project_name != null && data_choice != null	&& data_choice.equals("ontology")) {
				
				if (next_view == null) {
					next_view = 1;
				}

				List<Ontology> obs = null;
				obs = projectsService.getOntology(project_name, views_per_page,	next_view - 1);
				int count = projectsService.getOntologyCount(project_name);

				model.addObject("totalcount", count);

				int limit = (next_view + views_per_page);

				if (limit > count) {
					limit = count;
				}

				model.addObject("message", "Showing " + next_view + " to " + limit + " of " + count + " entries");
				model.addObject("next_view", next_view);
				model.addObject("data_choice", "ontology");
				model.addObject("object", obs);
			}

			// mappings
			if (project_name != null && data_choice != null	&& data_choice.equals("mappings")) {
				
				if (next_view == null) {
					next_view = 1;
				}

				List<OntMappings> mappings = null;
				mappings = projectsService.getMappings(project_name, views_per_page, next_view - 1);
				int count = projectsService.getMappingsCount(project_name);

				model.addObject("totalcount", count);

				int limit = (next_view + views_per_page);

				if (limit > count) {
					limit = count;
				}

				model.addObject("message", "Showing " + next_view + " to " + limit + " of " + count + " entries");
				model.addObject("next_view", next_view);
				model.addObject("data_choice", "mappings");
				model.addObject("object", mappings);
			}

			model.addObject("projects", projects);
			model.addObject("user", currentUser);
			model.addObject("uploadtype", "exportdata");
			model.setViewName("exportdata");
		} catch (Exception e) {
			logger.info("exportdata exception " + e.getStackTrace());
			model.setViewName("logout");
		}
		return model;
	}


	/**  
	* Mapping existing term to snomed term
	*
	*/
	@RequestMapping(value = "/join**", method = RequestMethod.GET)
	public @ResponseBody String join(@RequestParam(value = "existing_code", required = false) String existing_code,
									 @RequestParam(value = "new_code", required = false) String new_code,
									 @RequestParam(value = "project_name", required = false) String project_name,
									 Principal principal) {

		final String currentUser = principal.getName();
		Entry<String, String> keypair = treeMap.floorEntry(new_code);
		logger.info("join key " + keypair.getKey() + " value " + keypair.getValue());

		String message = projectsService.mapNewCode(project_name, existing_code, new_code, keypair.getValue());

		ModelAndView model = new ModelAndView();
		
		return message;
	}

	/**  
	* Delete a mapped term
	*
	*/
	@RequestMapping(value = "/deleteconcept**", method = RequestMethod.GET)
	public @ResponseBody String deleteconcept(@RequestParam(value = "new_code", required = false) String new_code,
										      @RequestParam(value = "project_name", required = false) String project_name,
										      Principal principal) {

		final String currentUser = principal.getName();
		String flag = null;
		flag = projectsService.deleteConceptCode(project_name, new_code);

		ModelAndView model = new ModelAndView();
		String json = "";

		// TODO a suitable return status	
		return "[{}]";

	}

	/**  
	* Find an existing concept in the ontology
	*
	*/
	@RequestMapping(value = "/findexistingconcept**", method = RequestMethod.GET)
	public @ResponseBody String findexistingconcept(@RequestParam(value = "term", required = false) String term,
												    @RequestParam(value = "project_name", required = false) String project_name,
												    Principal principal) {

		List<ConceptDimension> concepts = null;
		concepts = projectsService.findCodes(project_name, term);

		ModelAndView model = new ModelAndView();
		String json = "";

		for (int i = 0; i < concepts.size(); i++) {
			ConceptDimension o = (ConceptDimension) concepts.get(i);
			logger.info("findexistingconcept " + o.getConcept_cd());

			json = json + "{ \"code\":\"" + o.getConcept_cd() + "\" } ,";

		}

		if (!json.equals("")) {
			return "[ " + json.substring(0, json.length() - 1) + " ]";
		} else {
			return "[{}]";
		}
	}

	/**  
	* 
	*
	*/
	@RequestMapping(value = "/findconcept**", method = RequestMethod.GET)
	public @ResponseBody String findconcept(@RequestParam(value = "term", required = false) String term,
										    Principal principal) {

		logger.info("findconcept");
		List<Projects> projects = null;
		projects = projectsService.viewAllProjects();

		ModelAndView model = new ModelAndView();
		String json = "";
		int count = 0;

		for (ConcurrentNavigableMap.Entry<String, String> entry : treeMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (key.contains(term) || value.contains(term)) {
				json = json + "{ \"code\":\"" + key + "\"" + ","
						+ " \"term\":\"" + value + "\" } ,";
				count++;
			}

			if (count > 100) {
				break;
			}
		}

		if (json.equals("")) {
			json = "{} ";
		}

		if (!json.equals("")) {
			return "[ " + json.substring(0, json.length() - 1) + " ]";
		} else {
			return "";
		}
	}

	/**  
	* Ontology Mapper forward
	*
	*/
	@RequestMapping(value = "/ontmapper**", method = RequestMethod.GET)
	public ModelAndView ontmapper(Principal principal) {

		logger.info("ontmapper");
		final String currentUser = principal.getName();
		List<Projects> projects = null;
		projects = projectsService.viewAllProjects();

		ModelAndView model = new ModelAndView();

		try {
			model.addObject("projects", projects);
			model.addObject("user", currentUser);
			model.setViewName("ontmapper");
		} catch (Exception e) {
			model.setViewName("logout");
		}
		return model;

	}

	/**  
	* Change Password
	*
	*/
	@RequestMapping(value = "/changepassword**", method = RequestMethod.GET)
	public ModelAndView changepassword(Principal principal) {
		logger.info("login changepassword");

		final String currentUser = principal.getName();

		ModelAndView model = new ModelAndView();
		model.addObject("user", currentUser);
		model.setViewName("changepassword");
		return model;

	}

	/**  
	* Show Settings Page
	*
	*/
	@RequestMapping(value = "/settings**", method = RequestMethod.GET)
	public ModelAndView settings() {
		logger.info("login settings");
		ModelAndView model = new ModelAndView();
		model.setViewName("settings");
		return model;

	}

	/**  
	* View Users
	*
	*/
	@RequestMapping(value = "/viewusers**", method = RequestMethod.GET)
	public ModelAndView viewusers() {

		logger.info("login viewusers");

		List<User> users = userService.viewAllUsers();

		ModelAndView model = new ModelAndView();
		model.addObject("users", users);
		model.setViewName("viewusers");
		return model;

	}

	/**  
	* Your i2b2 instance page
	*
	*/
	@RequestMapping(value = "/i2b2instance**", method = RequestMethod.GET)
	public ModelAndView i2b2instance(HttpServletRequest request) {
		logger.info("login i2b2instance");
		
		String url = request.getServerName();
		url = "i2b2up";

		ModelAndView model = new ModelAndView();
		model.addObject("i2b2url", "http://" + url	+ "/i2b2/webclient_brisskit/");
		return model;

	}

	/**  
	* Change password
	*
	*/
	@RequestMapping(value = "/passwordchanged", method = RequestMethod.POST)
	public ModelAndView passwordchanged(@RequestParam(value = "curPassword", required = false) String curPassword,
										@RequestParam(value = "newPassword1", required = false) String newPassword1,
										@RequestParam(value = "newPassword2", required = false) String newPassword2,
										Principal principal) {
		
		final String currentUser = principal.getName();

		logger.info("passwordchanged currentUser" + currentUser + " curPassword = " + curPassword + " newPassword1 = " + newPassword1 + " newPassword2 " + newPassword2);

		
		boolean flag = userService.changeUserPassword(currentUser,newPassword1, newPassword2);

		logger.info("passwordchanged flag " + flag);

		ModelAndView model = new ModelAndView();
		model.setViewName("passwordchanged");
		return model;
	}

	/**  
	* customize the error message
	*
	*/
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	/**  
	* for 403 access denied page
	*
	*/
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			logger.info(userDetail);
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}

	/**  
	* forgotpassword
	*
	*/
	@RequestMapping(value = "/fp", method = RequestMethod.POST)
	public ModelAndView fp(@RequestParam(value = "username", required = false) String username) {
		
		logger.info("fp username " + username);
		boolean flag = userService.sendNewPassword(username);
		logger.info("fp flag " + flag);
		ModelAndView model = new ModelAndView();
		model.setViewName("newpass");
		return model;
	}

	/**  
	* multipartToFile
	*
	*/
	public File multipartToFile(MultipartFile multipart)
			throws IllegalStateException, IOException {
		File tmpFile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator")
				+ multipart.getOriginalFilename());

		logger.info(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator")
				+ multipart.getOriginalFilename());

		multipart.transferTo(tmpFile);
		return tmpFile;
	}

	/**  
	* createnewuser
	*
	*/
	@RequestMapping(value = "/createnewuser**", method = RequestMethod.GET)
	public ModelAndView createnewuser() {
		logger.info("login createnewuser");
		ModelAndView model = new ModelAndView();
		model.setViewName("createnewuser");
		return model;

	}

	/**  
	* createuser
	*
	*/
	@RequestMapping(value = "/createuser**", method = RequestMethod.POST)
	public ModelAndView createuser(@RequestParam(value = "email", required = false) String email,
								   @RequestParam(value = "password1", required = false) String password1,
								   @RequestParam(value = "password2", required = false) String password2) {
		
		logger.info("createuser x " + email + " password1 "+  password1 + " password2 "+  password2);
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password1);

		User u = new User(email, hashedPassword, true);
		boolean flag = userService.addUser(u);

		if (flag) {
			ModelAndView model = new ModelAndView();
			model.addObject("username", email);
			model.addObject("success", "true");
			model.setViewName("createnewuser");
			return model;
		} else {
			ModelAndView model = new ModelAndView();
			model.addObject("username", email);
			model.setViewName("createnewuser");
			return model;
		}

	}

}