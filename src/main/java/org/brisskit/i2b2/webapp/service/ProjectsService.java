package org.brisskit.i2b2.webapp.service;

// default package
// Generated 22-Jan-2015 15:23:27 by Hibernate Tools 3.2.0.beta8

import java.util.List;

import org.brisskit.i2b2.webapp.model.ConceptDimension;
import org.brisskit.i2b2.webapp.model.ObservationFact;
import org.brisskit.i2b2.webapp.model.OntMappings;
import org.brisskit.i2b2.webapp.model.Ontology;
import org.brisskit.i2b2.webapp.model.PatientMapping;
import org.brisskit.i2b2.webapp.model.Projects;

/** ProjectsService
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public interface ProjectsService {
	public boolean createProject(Projects p);
	public boolean deleteProject(String project_id);
	public List<Projects> viewAllProjects();
	public Projects findByProjectID(String project_id);
	public int getObsCount(String project_name);
	public int getPatientsCount(String project_name);
	public int getConceptsCount(String project_name);
	public int getOntologyCount(String project_name);
	public int getMappingsCount(String project_name);
	public List<ObservationFact> getObs(String project_name, Integer views_per_page, Integer next_view);
	public List<PatientMapping> getPatients(String project_name, Integer views_per_page, Integer next_view);
	public List<ConceptDimension> getConcepts(String project_name, Integer views_per_page, Integer next_view);	
	public List<Ontology> getOntology(String project_name, Integer views_per_page, Integer next_view);
	public List<OntMappings> getMappings(String project_name, Integer views_per_page, Integer next_view);
	public List<ConceptDimension> findCodes(String project_name, String term);	
	public String mapNewCode(String project_name, String existing_code, String new_code, String new_code_desc);		
	public String deleteConceptCode(String project_name, String new_code);
	
	}
