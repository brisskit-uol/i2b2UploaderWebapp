package org.brisskit.i2b2.webapp.service;

// default package
// Generated 22-Jan-2015 15:23:27 by Hibernate Tools 3.2.0.beta8

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.webapp.dao.ProjectsDao;
import org.brisskit.i2b2.webapp.model.ConceptDimension;
import org.brisskit.i2b2.webapp.model.ObservationFact;
import org.brisskit.i2b2.webapp.model.OntMappings;
import org.brisskit.i2b2.webapp.model.Ontology;
import org.brisskit.i2b2.webapp.model.PatientMapping;
import org.brisskit.i2b2.webapp.model.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** ProjectsServiceImp
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

@Service
public class ProjectsServiceImp implements ProjectsService {
	
	private ProjectsDao projectsDao;

	final static Logger log = LogManager.getLogger(ProjectsServiceImp.class);	
	
	public ProjectsDao getProjectsDao() {
		log.info("UserServiceImp - getUserDao");
		return projectsDao;
	}

	public void setProjectsDao(ProjectsDao projectsDao) {
		log.info("UserServiceImp - setUserDao");
		this.projectsDao = projectsDao;
	}
	
	//@Transactional
	public boolean createProject(Projects p) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp createProject");
		return projectsDao.attachDirty(p);
		
	}
	
	//@Transactional
	public boolean deleteProject(String project_id) {
		// TODO Auto-generated method stub
		return projectsDao.deleteProject(project_id);
		
	}
		
	//@Transactional
	public List<Projects> viewAllProjects() {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp viewAllProjects 1");
		return projectsDao.viewAllProjects();
	}

	//@Transactional
	public Projects findByProjectID(String project_id) {
		// TODO Auto-generated method stub
		return projectsDao.findByProjectId(project_id);
	}

	//@Transactional
	public int getObsCount(String project_name) {
		return projectsDao.getObsCount(project_name);
	}
	
	//@Transactional
	public List<ObservationFact> getObs(String project_name, Integer views_per_page, Integer next_view) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - getObs 1 ");
		List<ObservationFact> obs = projectsDao.getObs(project_name, views_per_page, next_view);
		log.info("ProjectsServiceImp - getObs 2 " + obs.size());
		
		/*
		for (int i = 0; i < obs.size(); i++) {
			log.info("ProjectsServiceImp - getObs 3 ");
			ObservationFact o = (ObservationFact) obs.get(i);
			//System.out.println(o.getId());
			log.info("ProjectsServiceImp - getObs 4 " + o.getEncounter_num());
		}
        */
		return projectsDao.getObs(project_name, views_per_page, next_view);
	}
	
	//@Transactional
	public int getPatientsCount(String project_name) {
		return projectsDao.getPatientsCount(project_name);
	}
	
	//@Transactional
	public List<PatientMapping> getPatients(String project_name, Integer views_per_page, Integer next_view) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - getPatients 1 ");
		List<PatientMapping> patients = projectsDao.getPatients(project_name, views_per_page, next_view);
		log.info("ProjectsServiceImp - getPatients 2 " + patients.size());
		
		return patients;
	}
	
	//@Transactional
	public int getConceptsCount(String project_name) {
		return projectsDao.getConceptsCount(project_name);
	}
	
	//@Transactional
	public List<ConceptDimension> getConcepts(String project_name, Integer views_per_page, Integer next_view) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - getConcepts 1 ");
		List<ConceptDimension> concepts = projectsDao.getConcepts(project_name, views_per_page, next_view);
		log.info("ProjectsServiceImp - getConcepts 2 " + concepts.size());
		
		return concepts;
	}
	
	//@Transactional
	public int getOntologyCount(String project_name) {
		return projectsDao.getPatientsCount(project_name);
	}
	
	//@Transactional
	public List<Ontology> getOntology(String project_name, Integer views_per_page, Integer next_view) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - getOntology 1 ");
		List<Ontology> ontology = projectsDao.getOntology(project_name, views_per_page, next_view);
		log.info("ProjectsServiceImp - getOntology 2 " + ontology.size());
		
		return ontology;
	}
	
	//@Transactional
	public int getMappingsCount(String project_name) {
		return projectsDao.getMappingsCount(project_name);
	}
	
	//@Transactional
	public List<OntMappings> getMappings(String project_name, Integer views_per_page, Integer next_view) {
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - getMappings 1 ");
		List<OntMappings> mappings = projectsDao.getMappings(project_name, views_per_page, next_view);
		log.info("ProjectsServiceImp - getMappings 2 " + mappings.size());
		
		return mappings;
	}
	
	//@Transactional
	public List<ConceptDimension> findCodes(String project_name, String term){
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - findCodes 1 ");
		List<ConceptDimension> concepts = projectsDao.findCodes(project_name, term);
		log.info("ProjectsServiceImp - findCodes 2 " + concepts.size());
				
		return concepts;
	}
	
	//@Transactional
	public String deleteConceptCode(String project_name, String new_code){
		// TODO Auto-generated method stub
		log.info("ProjectsServiceImp - findCodes 1 ");
		String flag = projectsDao.deleteConceptCode(project_name, new_code);
		log.info("ProjectsServiceImp - findCodes 2 " + flag);
				
		return flag;
	}
	
	//@Transactional
	public String mapNewCode(String project_name, String existing_code, String new_code, String new_code_desc) {
	
		return  projectsDao.mapNewCode(project_name, existing_code, new_code, new_code_desc);
	}

	}
