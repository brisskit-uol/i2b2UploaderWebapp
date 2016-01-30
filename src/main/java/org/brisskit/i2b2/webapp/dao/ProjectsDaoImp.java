package org.brisskit.i2b2.webapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.brisskit.i2b2.webapp.model.ConceptDimension;
import org.brisskit.i2b2.webapp.model.ObservationFact;
import org.brisskit.i2b2.webapp.model.ObservationFactFull;
import org.brisskit.i2b2.webapp.model.OntMappings;
import org.brisskit.i2b2.webapp.model.Ontology;
import org.brisskit.i2b2.webapp.model.PatientMapping;
import org.brisskit.i2b2.webapp.model.Projects;
import org.brisskit.i2b2.webapp.model.TableAccess;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;

/** ProjectsDaoImp
*
* @author Shajid Issa
* @version 1.0 
* @date 23/03/2015
*/

public class ProjectsDaoImp implements ProjectsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	final static Logger log = LogManager.getLogger(ProjectsDaoImp.class);	
	static BufferedReader r;
	
		
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
	
	
	public SessionFactory getSessionFactory() {
		log.info("ProjectsUserDao - getSessionFactory");
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		log.info("ProjectsUserDao - setSessionFactory");
		this.sessionFactory = sessionFactory;
	}
	
	public void persist(Projects transientInstance) {
		log.debug("persisting Projects instance");
		try {
			 getSessionFactory().getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
			
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	

	@SuppressWarnings("unchecked")
	public boolean attachDirty(Projects instance) {
		log.debug("attaching dirty Projects instance");
		SessionFactory session = getSessionFactory();
		
		try {
			//sessionFact.openSession().saveOrUpdate(instance);
			session.getCurrentSession().save(instance);
			//session.close();
			//sessionFact.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
			return true;
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			return false;
			//throw re;
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Projects> viewAllProjects() {
		// TODO Auto-generated method stub
		
		log.info("ProjectsDaoImp viewAllProjects 1");
		
		Session session =   getSession(); //getSessionFactory().openSession();
		
		log.info("ProjectsDaoImp viewAllProjects 2");
		
		List<Projects> projects = new ArrayList<Projects>();

		log.info("ProjectsDaoImp viewAllProjects 3");
		
		//getSessionFactory().getCurrentSession().createQuery("set schema i2b2portal");
		
		log.info("ProjectsDaoImp viewAllProjects 4");
		
		projects = session.createQuery("from Projects").list();
		log.info("ProjectsDaoImp viewAllProjects 5");
		
		////session.close();
		return projects;
	}

	public void attachClean(Projects instance) {
		log.debug("attaching clean Projects instance");
		try {
			 getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Projects persistentInstance) {
		log.debug("deleting Projects instance");
		try {
			 getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Projects merge(Projects detachedInstance) {
		log.debug("merging Projects instance");
		try {
			Projects result = (Projects)  getSessionFactory().getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Projects findById(int id) {
		log.debug("getting Projects instance with id: " + id);
		try {
			Projects instance = (Projects)  getSessionFactory().getCurrentSession()
					.get("Projects", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Projects findByProjectId(String project_id) {
		log.debug("getting Projects instance with project_id: " + project_id);
		
		Session session =   getSession(); //getSessionFactory().openSession();
		
		List<Projects> projects = new ArrayList<Projects>();

		projects = session.createQuery("from Projects where project_id=?").setParameter(0, project_id).list();
		
		////session.close();
		
		if (projects.size() == 1) {
			log.info("UserDaoImpl - findByUserName users.size() = " + projects.size() );			
			return projects.get(0);
		} else {
			log.info("UserDaoImpl - findByUserName users.size() = null" );			
			return null;
		}
		
		//return projects;
	
	}

	public List findByExample(Projects instance) {
		log.debug("finding Projects instance by example");
		try {
			List results =  getSessionFactory().getCurrentSession().createCriteria(
					"Projects").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
		public int getObsCount(String project_name) {
		Session session =   getSession(); //getSessionFactory().openSession();
		String hql = "select COUNT(*) FROM "+project_name+".observation_fact";
       // Long count = ((Long) session.createSQLQuery(hql).uniqueResult()).longValue();
		Number count = ((Number) session.createSQLQuery(hql).uniqueResult()).intValue();
		////session.close();
		return count.intValue();
	}
	
	public int getPatientsCount(String project_name) {
		Session session =   getSession(); //getSessionFactory().openSession();
		String hql = "select COUNT(*) FROM "+project_name+".patient_mapping";
       // Long count = ((Long) session.createSQLQuery(hql).uniqueResult()).longValue();
		Number count = ((Number) session.createSQLQuery(hql).uniqueResult()).intValue();
		////session.close();
		return count.intValue();
	}
	
	public int getConceptsCount(String project_name) {
		Session session =   getSession(); //getSessionFactory().openSession();
		String hql = "select COUNT(*) FROM "+project_name+".concept_dimension";
       // Long count = ((Long) session.createSQLQuery(hql).uniqueResult()).longValue();
		Number count = ((Number) session.createSQLQuery(hql).uniqueResult()).intValue();
		////session.close();
		return count.intValue();
	}
	
	public int getOntologyCount(String project_name) {
		Session session =   getSession(); //getSessionFactory().openSession();
		String hql = "select COUNT(*) FROM "+project_name+"." +project_name;
       // Long count = ((Long) session.createSQLQuery(hql).uniqueResult()).longValue();
		Number count = ((Number) session.createSQLQuery(hql).uniqueResult()).intValue();
		////session.close();
		return count.intValue();
	}
	
	public int getMappingsCount(String project_name) {
		Session session =   getSession(); //getSessionFactory().openSession();
		String hql = "select COUNT(*) FROM i2b2portal.mappings where project_id='"+project_name+"'";
       // Long count = ((Long) session.createSQLQuery(hql).uniqueResult()).longValue();
		Number count = ((Number) session.createSQLQuery(hql).uniqueResult()).intValue();
		////session.close();
		return count.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ObservationFact> getObs(String project_name, Integer views_per_page, Integer next_view) {
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getObs 1");
		List<ObservationFact> obs = new ArrayList<ObservationFact>();
		log.info("ProjectsUserDao - getObs 2");
		//session.createQuery("set schema shajid");
		log.info("ProjectsUserDao - getObs 3 ");
		//obs = session.createQuery("from observation_fact").list();
		//log.info("ProjectsUserDao - getObs 4" + obs.size());
		
/*		
		String hql = "select encounter_num, patient_num FROM p7.observation_fact";
        Query findQuery = session.createSQLQuery(hql);
        findQuery.setFirstResult(10);
        findQuery.setMaxResults(10);
                
        List<Object[]> entities = findQuery.list();
        for (Object[] entity : entities) {
        	
        	//ObservationFact o = new ObservationFact();
        	
        	//o.setEncounter_num(Integer.parseInt((String) entity[0]));
        	//obs.add(o);
        	
        	
            for (Object entityCol : entity) {
            	log.info("X " + entityCol);
            }
            log.info("X");
        }
 */       
        
		/*
        List<ObservationFact> o = (List<ObservationFact>) session.createSQLQuery(
        	    "select encounter_num FROM p7.observation_fact")
        	    .addEntity(ObservationFact.class)
        	    .list(); 
        */	   
        	           
        //List<ObservationFact> o  = (List<ObservationFact>) session.createSQLQuery("select encounter_num FROM p7.observation_fact").addScalar("encounter_num",StandardBasicTypes.INTEGER ).setResultTransformer(Transformers.aliasToBean(ObservationFact.class));
        
        Query findQuery  = session.createSQLQuery("select PA.patient_ide, OFA.encounter_num, OFA.patient_num, OFA.concept_cd, OFA.valtype_cd, OFA.tval_char, OFA.nval_num FROM "+project_name+".observation_fact OFA join "+project_name+".patient_mapping PA on OFA.patient_num = PA.patient_num order by OFA.patient_num")
                .setResultTransformer(new AliasToBeanResultTransformer(ObservationFact.class));
        
        //Query findQuery  = session.createSQLQuery("select encounter_num, patient_num, concept_cd, valtype_cd, tval_char, nval_num FROM p7.observation_fact order by patient_num")
        //        .setResultTransformer(new AliasToBeanResultTransformer(ObservationFact.class));
        findQuery.setFirstResult(next_view);
        findQuery.setMaxResults(views_per_page);
        List<ObservationFact> o = findQuery.list();
        
        log.info("ProjectsUserDao - getObs 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getObs 5 ");
		////session.close();
		log.info("ProjectsUserDao - getObs 6 ");
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public List<PatientMapping> getPatients(String project_name, Integer views_per_page, Integer next_view) {
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getPatients 1");
		List<PatientMapping> patients = new ArrayList<PatientMapping>();
		log.info("ProjectsUserDao - getPatients 2");
		log.info("ProjectsUserDao - getPatients 3 ");
		
        Query findQuery  = session.createSQLQuery("select PA.patient_ide, PA.patient_num FROM "+project_name+".patient_mapping PA order by PA.patient_num")
                .setResultTransformer(new AliasToBeanResultTransformer(PatientMapping.class));
        
        findQuery.setFirstResult(next_view);
        findQuery.setMaxResults(views_per_page);
        List<PatientMapping> o = findQuery.list();
        
        log.info("ProjectsUserDao - getPatients 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getPatients 5 ");
		////session.close();
		log.info("ProjectsUserDao - getPatients 6 ");
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public List<ConceptDimension> findCodes(String project_name, String term)
	{
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getPatients 1");
		List<ConceptDimension> patients = new ArrayList<ConceptDimension>();
		log.info("ProjectsUserDao - getPatients 2");
		log.info("ProjectsUserDao - getPatients 3 ");
		
        Query findQuery  = session.createSQLQuery("select CP.concept_path, CP.concept_cd, CP.name_char FROM "+project_name+".concept_dimension CP where LOWER(CP.concept_cd) like LOWER('%"+term+"%')  order by CP.concept_path")
                .setResultTransformer(new AliasToBeanResultTransformer(ConceptDimension.class));
        
        //findQuery.setFirstResult(next_view);
        //findQuery.setMaxResults(views_per_page);
        List<ConceptDimension> o = findQuery.list();
        
        log.info("ProjectsUserDao - getPatients 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getPatients 5 ");
		////session.close();
		log.info("ProjectsUserDao - getPatients 6 ");
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public List<ConceptDimension> getConcepts(String project_name, Integer views_per_page, Integer next_view) {
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getPatients 1");
		List<ConceptDimension> patients = new ArrayList<ConceptDimension>();
		log.info("ProjectsUserDao - getPatients 2");
		log.info("ProjectsUserDao - getPatients 3 ");
		
        Query findQuery  = session.createSQLQuery("select CP.concept_path, CP.concept_cd, CP.name_char FROM "+project_name+".concept_dimension CP order by CP.concept_path")
                .setResultTransformer(new AliasToBeanResultTransformer(ConceptDimension.class));
        
        findQuery.setFirstResult(next_view);
        findQuery.setMaxResults(views_per_page);
        List<ConceptDimension> o = findQuery.list();
        
        log.info("ProjectsUserDao - getPatients 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getPatients 5 ");
		////session.close();
		log.info("ProjectsUserDao - getPatients 6 ");
		return o;
	}

	
	@SuppressWarnings("unchecked")
	public List<OntMappings> getMappings(String project_name, Integer views_per_page, Integer next_view) {
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getPatients 1");
		List<OntMappings> patients = new ArrayList<OntMappings>();
		log.info("ProjectsUserDao - getPatients 2");
		log.info("ProjectsUserDao - getPatients 3 ");
		
        Query findQuery  = session.createSQLQuery("select MP.id, MP.project_id, MP.username, MP.current_code, MP.new_code, MP.new_code_desc FROM i2b2portal.mappings MP where MP.project_id='"+project_name+"' order by MP.project_id")
                .setResultTransformer(new AliasToBeanResultTransformer(OntMappings.class));
        
        findQuery.setFirstResult(next_view);
        findQuery.setMaxResults(views_per_page);
        List<OntMappings> o = findQuery.list();
        
        log.info("ProjectsUserDao - getPatients 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getPatients 5 ");
		////session.close();
		log.info("ProjectsUserDao - getPatients 6 ");
		return o;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Ontology> getOntology(String project_name, Integer views_per_page, Integer next_view) {
		Session session =   getSession(); //getSessionFactory().openSession();
		log.info("ProjectsUserDao - getPatients 1");
		List<Ontology> patients = new ArrayList<Ontology>();
		log.info("ProjectsUserDao - getPatients 2");
		log.info("ProjectsUserDao - getPatients 3 ");
		
        Query findQuery  = session.createSQLQuery("select ONT.c_hlevel, "
        										+ " ONT.c_fullname,"
        										+ " ONT.c_name,"
        										+ " ONT.c_synonym_cd,"
        										+ " ONT.c_visualattributes,"
        										+ " ONT.c_totalnum,"
        										+ " ONT.c_basecode,"
        										+ " ONT.c_metadataxml,"
        										+ " ONT.c_facttablecolumn,"
        										+ " ONT.c_tablename,"
        										+ " ONT.c_columnname,"
        										+ " ONT.c_columndatatype,"
        										+ " ONT.c_operator,"
        										+ " ONT.c_dimcode,"
        										+ " ONT.c_comment,"
        										+ " ONT.c_tooltip"
        										+ " FROM "+project_name+"."+project_name+" ONT order by ONT.c_fullname, ONT.c_hlevel")
                .setResultTransformer(new AliasToBeanResultTransformer(Ontology.class));
        
        findQuery.setFirstResult(next_view);
        findQuery.setMaxResults(views_per_page);
        List<Ontology> o = findQuery.list();
        
        log.info("ProjectsUserDao - getPatients 4 ");
        //obs = findQuery.list();
        log.info("ProjectsUserDao - getPatients 5 ");
		////session.close();
		log.info("ProjectsUserDao - getPatients 6 ");
		return o;
	}


	public String mapNewCode(String project_name, String existing_code, String new_code, String new_code_desc)
	{
		
		// find from observations where existing codes exist
		// create concepts and ontology rows
		// create table_acess
		String message = "{ \"message\":\"mapped " + existing_code + "\", \"error\":\"0\" }";
		
		System.out.println(project_name);
		System.out.println(existing_code);
		System.out.println(new_code);
		
		Session session =   getSession(); //getSessionFactory().openSession();
		
		
		// Check if code has been already mapped
		Query findQuery_mapping  = session.createSQLQuery("select "
				+ "id,"
				+ "project_id,"
				+ "username,"
				+ "current_code,"
				+ "new_code,"
				+ "new_code_desc"
				+ " FROM i2b2portal.mappings where current_code = '"+existing_code+"' and project_id='"+project_name+"'")
				.setResultTransformer(new AliasToBeanResultTransformer(OntMappings.class));
	 
	   //List<TableAccess> o2 = findQuery2.list();
		OntMappings existing_mapping = (OntMappings) findQuery_mapping.uniqueResult();
	   
	   if (existing_mapping != null) 
	   {
		   message = "{ \"message\":\"unable to map current code of " + existing_code + " as it has existing mapping\", \"error\":\"1\" }";
		   return message;
	   }
	   
	   Query findQuery_mapping2  = session.createSQLQuery("select "
				+ "id,"
				+ "project_id,"
				+ "username,"
				+ "current_code,"
				+ "new_code,"
				+ "new_code_desc"
				+ " FROM i2b2portal.mappings where new_code = '"+new_code+"' and project_id='"+project_name+"'")
				.setResultTransformer(new AliasToBeanResultTransformer(OntMappings.class));
	 
	   //List<TableAccess> o2 = findQuery2.list();
	   OntMappings existing_mapping2 = (OntMappings) findQuery_mapping2.uniqueResult();
	   
	   if (existing_mapping2 != null) 
	   {
		   message = "{ \"message\":\"unable to map to new code of " + new_code + " as it has been previously mapped\", \"error\":\"2\" }";
		   return message;
	   }	   
	 
		
		
		
		
		
		
		
		Query findQuery  = session.createSQLQuery("select TBA.c_table_cd, "
					+ " TBA.c_table_name,"
					+ " TBA.c_protected_access,"
					+ " TBA.c_hlevel,"
					+ " TBA.c_fullname,"
					+ " TBA.c_name,"
					+ " TBA.c_synonym_cd,"
					+ " TBA.c_visualattributes,"
					+ " TBA.c_totalnum,"
					+ " TBA.c_basecode,"
					+ " TBA.c_metadataxml,"
					+ " TBA.c_facttablecolumn,"
					+ " TBA.c_dimtablename,"
					+ " TBA.c_columnname,"
					+ " TBA.c_columndatatype,"
					+ " TBA.c_operator,"
					+ " TBA.c_dimcode,"
					+ " TBA.c_comment,"
					+ " TBA.c_tooltip,"
					+ " TBA.c_entry_date,"
					+ " TBA.c_change_date,"
					+ " TBA.valuetype_cd"
					+ " FROM "+project_name+"."+"table_access TBA where TBA.c_table_cd like '%"+project_name+"%' order by TBA.c_table_cd")
					.setResultTransformer(new AliasToBeanResultTransformer(TableAccess.class));
		 
		List<TableAccess> o = findQuery.list();
		
		System.out.println(o.size());
		
		if(o.size() == 1)
		{
			TableAccess main = (TableAccess) o.get(0);
			System.out.println(main.getC_table_cd() + " " + 
			                   main.getC_table_name() + " " + 
			                   main.getC_fullname() + " " + 
			                   main.getC_name() + " " +
			                   main.getC_dimcode() + " " +
			                   main.getC_tooltip() + " " 
					           );
			
			main.setC_fullname("\\snomed\\");
			main.setC_name("snomed");
			main.setC_dimcode("\\snomed\\");
			main.setC_tooltip("snomed");
			
			
			SQLQuery sql = session.createSQLQuery("insert into "+project_name+"."+"table_access("
												+ "c_table_cd,"
												+ "c_table_name,"
												+ "c_protected_access,"
												+ "c_hlevel,"
												+ "c_fullname,"
												+ "c_name,"
												+ "c_synonym_cd,"
												+ "c_visualattributes,"
												//+ "c_totalnum,"
												//+ "c_basecode,"
												//+ "c_metadataxml,"
												+ "c_facttablecolumn,"
												+ "c_dimtablename,"
												+ "c_columnname,"
												+ "c_columndatatype,"
												+ "c_operator,"
												+ "c_dimcode,"
												//+ "c_comment,"
												+ "c_tooltip"
												//+ "c_entry_date,"
												//+ "c_change_date,"
												//+ "c_status_cd,"												
												//+ "valuetype_cd"
												+ ") "
												+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); //15
			sql.setString(0, main.getC_table_cd());
			sql.setString(1, main.getC_table_name());
			sql.setCharacter(2, main.getC_protected_access());
			sql.setInteger(3, main.getC_hlevel());
			sql.setString(4, main.getC_fullname());
			sql.setString(5, main.getC_name());			
			sql.setCharacter(6, main.getC_synonym_cd());
			sql.setString(7, main.getC_visualattributes());			
			//sql.setInteger(8, main.getC_totalnum());			
			//sql.setString(9, main.getC_basecode());
			//sql.setString(10, main.getC_metadataxml());
			sql.setString(8, main.getC_facttablecolumn());
			sql.setString(9, main.getC_dimtablename());
			sql.setString(10, main.getC_columnname());
			sql.setString(11, main.getC_columndatatype());
			sql.setString(12, main.getC_operator());
			sql.setString(13, main.getC_dimcode());
			//sql.setString(17, main.getC_comment());
			sql.setString(14, main.getC_tooltip());
			//sql.setDate(19, main.getC_entry_date());
			//sql.setDate(20, main.getC_change_date());
			//sql.setCharacter(21, main.getC_status_cd());
			//sql.setString(22, main.getValuetype_cd());
			sql.executeUpdate();
			
			
			
			
			SQLQuery sql2 = session.createSQLQuery("insert into "+project_name+"."+ project_name + "("
					+ "c_hlevel,"
					+ "c_fullname,"
					+ "c_name,"
					+ "c_synonym_cd,"
					+ "c_visualattributes,"
					//+ "c_totalnum,"
//					+ "c_basecode,"
//					+ "c_metadataxml,"
					+ "c_facttablecolumn,"
					+ "c_tablename,"
					+ "c_columnname,"
					+ "c_columndatatype,"
					+ "c_operator,"
					+ "c_dimcode,"
//					+ "c_comment,"
					+ "c_tooltip,"
					+ "m_applied_path,"
					+ "update_date,"
					+ "download_date,"
					+ "import_date,"				
					+ "sourcesystem_cd"
					+ ") "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); //7
					sql2.setInteger(0, 0);
					sql2.setString(1, "\\snomed\\");
					sql2.setString(2, "snomed");
					sql2.setCharacter(3, 'N');					
					sql2.setString(4, "FA");
//					sql2.setString(5, "");		
//					sql2.setString(6, "");
					sql2.setString(5, "concept_cd");
					sql2.setString(6, "concept_dimension");
					sql2.setString(7, "concept_path");
					sql2.setString(8, "T");
					sql2.setString(9, "LIKE");
					sql2.setString(10, "\\snomed\\");
//					sql2.setString(13, "");
					sql2.setString(11, "\\snomed\\");
					sql2.setString(12, "@");
					long now = System.currentTimeMillis();
					sql2.setDate(13, new Date(now));
					sql2.setDate(14, new Date(now));
					sql2.setDate(15, new Date(now));
					sql2.setString(16, project_name);					
					
			sql2.executeUpdate();
		}
	
		if (new_code !=null  && !new_code.equals(""))
		{
			//String[] sp = new_code.split("#");
			
			SQLQuery sql = session.createSQLQuery("insert into "+project_name+"."+"concept_dimension("
					+ "concept_path,"
					+ "concept_cd,"
					+ "name_char,"
					+ "update_date,"
					+ "download_date,"
					+ "import_date,"
					+ "sourcesystem_cd"
					+ ") "
					+ " values (?,?,?,?,?,?,?)"); //7
					sql.setString(0, "\\snomed\\" + new_code + "\\");
					sql.setString(1, new_code);
					sql.setString(2, new_code);
					long now = System.currentTimeMillis();
					sql.setDate(3, new Date(now));
					sql.setDate(4, new Date(now));
					sql.setDate(5, new Date(now));
					sql.setString(6, project_name);
				
			try {		
			sql.executeUpdate();
			}
			catch (Exception e) { }
			
			
			// SELECT EXISTING FROM ONTOLOGY, THE COPY AND PLACE
			
			Query findQuery2  = session.createSQLQuery("select "
					+ "c_hlevel,"
					+ "c_fullname,"
					+ "c_name,"
					+ "c_synonym_cd,"
					+ "c_visualattributes,"
					//+ "c_totalnum,"
					+ "c_basecode,"
					+ "c_metadataxml,"
					+ "c_facttablecolumn,"
					+ "c_tablename,"
					+ "c_columnname,"
					+ "c_columndatatype,"
					+ "c_operator,"
					+ "c_dimcode,"
//					+ "c_comment,"
					+ "c_tooltip,"
					+ "m_applied_path,"
					+ "update_date,"
					+ "download_date,"
					+ "import_date,"				
					+ "sourcesystem_cd"
					+ " FROM "+project_name+"."+project_name + " where c_basecode = '"+existing_code+"'")
					.setResultTransformer(new AliasToBeanResultTransformer(Ontology.class));
		 
		   //List<TableAccess> o2 = findQuery2.list();
		   Ontology existing_o = (Ontology) findQuery2.uniqueResult();
		   
		   System.out.println("EXISTING " + existing_o.getC_basecode());
		   System.out.println("EXISTING " + existing_o.getC_metadataxml());
		   
		   
		   
		   
		   /********/
		/*
			String new_code_desc = "";
			
			Pattern patt = Pattern.compile(new_code);
		     
			try {
				//BufferedReader r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/ICD10.txt")));
				//r = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/SNOMED_CUT.txt")));
				r.mark(0);		
					  String line;
			      while ((line = r.readLine()) != null) {
			         Matcher m = patt.matcher(line);
			         while (m.find()) {
			            //System.out.println(m.group(0));
			            int start = m.start(0);
			            int end = m.end(0);
			            //Use CharacterIterator.substring(offset, end);
			            //System.out.println(line.substring(start, end));
			            System.out.println(line);
			            String[] split = line.split("\\s",2);	
			            new_code_desc = split[1];
			         }
			         
			      }
			      //r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		*/
			/*********/
			
		   
		   
		   
		   
		   
		   // Add new ontology code under the icd10 node
		   SQLQuery sql2 = session.createSQLQuery("insert into "+project_name+"."+ project_name + "("
					+ "c_hlevel,"
					+ "c_fullname,"
					+ "c_name,"
					+ "c_synonym_cd,"
					+ "c_visualattributes,"
					//+ "c_totalnum,"
					+ "c_basecode,"
					+ "c_metadataxml,"
					+ "c_facttablecolumn,"
					+ "c_tablename,"
					+ "c_columnname,"
					+ "c_columndatatype,"
					+ "c_operator,"
					+ "c_dimcode,"
//					+ "c_comment,"
					+ "c_tooltip,"
					+ "m_applied_path,"
					+ "update_date,"
					+ "download_date,"
					+ "import_date,"				
					+ "sourcesystem_cd"
					+ ") "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); //7
					sql2.setInteger(0, 1);
					sql2.setString(1, "\\snomed\\"+new_code+"\\");
					sql2.setString(2, new_code_desc); // text string from ontology file
					sql2.setCharacter(3, 'N');					
					sql2.setString(4, "LA");
					sql2.setString(5, new_code);		
					sql2.setString(6, existing_o.getC_metadataxml());
					sql2.setString(7, "concept_cd");
					sql2.setString(8, "concept_dimension");
					sql2.setString(9, "concept_path");
					sql2.setString(10, "T");
					sql2.setString(11, "LIKE");
					sql2.setString(12, "\\snomed\\"+new_code+"\\");
//					sql2.setString(13, "");
					sql2.setString(13, "\\snomed\\"+new_code+"\\");
					sql2.setString(14, "@");
					sql2.setDate(15, new Date(now));
					sql2.setDate(16, new Date(now));
					sql2.setDate(17, new Date(now));
					sql2.setString(18, project_name);					
					
			sql2.executeUpdate();

			// need to put the mapping in table somewhere
			
			SQLQuery sql4 = session.createSQLQuery("insert into i2b2portal.mappings ("
					+ "project_id,"
					+ "current_code,"
					+ "new_code,"
					+ "new_code_desc"
					+ ") "
					+ " values (?,?,?,?)"); //7
					sql4.setString(0, project_name);
					sql4.setString(1, existing_code);
					sql4.setString(2, new_code); 
					sql4.setString(3, new_code_desc);				
			sql4.executeUpdate();


			
			// find all existing observation and copy them for this new leaf
			
			 Query findQueryO  = session.createSQLQuery("select "
			 											+ "encounter_num, "
			 											+ "patient_num, "
			 											+ "concept_cd, "
			 											+ "provider_id, "
			 											+ "start_date, "
			 											+ "modifier_cd, "
			 											+ "instance_num, "
			 											+ "valtype_cd, "
			 											+ "tval_char, "
			 											+ "nval_num, "
			 											+ "update_date,"
			 											+ "download_date,"
			 											+ "import_date,"				
			 											+ "sourcesystem_cd,"		 											
			 											+ "text_search_index "
			 											+ "FROM "+project_name+".observation_fact where concept_cd = '"+existing_code+"'")
		                .setResultTransformer(new AliasToBeanResultTransformer(ObservationFactFull.class));
		       
			List<ObservationFactFull> o2O = findQueryO.list();
			
			for (int i = 0; i < o2O.size(); i++) {
				ObservationFactFull my_o = (ObservationFactFull) o2O.get(i);
				//System.out.println(o.getId());
				
				SQLQuery sql3 = session.createSQLQuery("insert into "+project_name+".observation_fact ("
						    + "encounter_num, "
							+ "patient_num, "
							+ "concept_cd, "
							+ "provider_id, "
							+ "start_date, "
							+ "modifier_cd, "
							+ "instance_num, "
							+ "valtype_cd, "
							+ "tval_char, "
							+ "nval_num, "
							+ "update_date,"
							+ "download_date,"
							+ "import_date,"				
							+ "sourcesystem_cd,"		 											
							+ "text_search_index "
						+ ") "
						+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); //15
						sql3.setInteger(0, my_o.getEncounter_num()/*+10000000*/);
						sql3.setInteger(1, my_o.getPatient_num());
						sql3.setString(2, new_code); 
						sql3.setString(3, my_o.getProvider_id());
						sql3.setDate(4, my_o.getStart_date());
						sql3.setString(5, my_o.getModifier_cd());
						sql3.setInteger(6, my_o.getInstance_num());
						sql3.setString(7, my_o.getValtype_cd());
						sql3.setString(8, my_o.getTval_char());
						sql3.setBigDecimal(9, my_o.getNval_num());
						sql3.setDate(10, my_o.getUpdate_date());
						sql3.setDate(11, my_o.getDownload_date());
						sql3.setDate(12, my_o.getImport_date());
						sql3.setString(13, my_o.getSourcesystem_cd());
						sql3.setInteger(14, my_o.getText_search_index()+10000000);
											
						
				sql3.executeUpdate();
			}
			
			
			
							
		}
		
		////session.close();
		
	       
		return message;
		
	}
	
	public boolean deleteProject(String project_id) {
		log.debug("1 deleteProject with project_id: " + project_id);
		
		Session session =   getSession(); //getSessionFactory().openSession();
		
		log.debug("X1: " + project_id);
		
		int i = session.createQuery("DELETE from Projects where project_id=?").setParameter(0, project_id).executeUpdate();
		log.debug("X2: " + project_id);
		//session.createSQLQuery("DELETE from i2b2portal.mappings where project_id='" + project_id + "'");
		
		
		Query deleteQuery = session.createSQLQuery("DELETE from i2b2portal.mappings where project_id=?");
		deleteQuery.setString(0, project_id);
		int updated = deleteQuery.executeUpdate();
		
		log.debug("3 deleteProject with project_id: " + project_id);
		
		////session.close();
		
		if (i == 1) { 
			return true;	
		}
		else
		{
			return false;	
		}
		
		
	
	}
	
	@SuppressWarnings("unchecked")
	public String deleteConceptCode(String project_name, String new_code)
	{
				
		Session session =   getSession(); //getSessionFactory().openSession();
			
		// delete ontology
		Query deleteQuery2 = session.createSQLQuery("DELETE from "+project_name+"."+ project_name + " where c_basecode=?");
		deleteQuery2.setString(0, new_code);
		int updated2 = deleteQuery2.executeUpdate();
		
		// delete concept dimention
		Query deleteQuery3 = session.createSQLQuery("DELETE from "+project_name+".concept_dimension where concept_cd=?");
		deleteQuery3.setString(0, new_code);
		int updated3 = deleteQuery3.executeUpdate();
		
		// delete observations
		Query deleteQuery4 = session.createSQLQuery("DELETE from "+project_name+".observation_fact where concept_cd=?");
		deleteQuery4.setString(0, new_code);
		int updated4 = deleteQuery4.executeUpdate();
		
		// delete mappings
		Query deleteQuery1 = session.createSQLQuery("DELETE from i2b2portal.mappings where project_id=? and new_code=?");
		deleteQuery1.setString(0, project_name);
		deleteQuery1.setString(1, new_code);
		int updated1 = deleteQuery1.executeUpdate();
		
		
        //Query findQuery  = session.createSQLQuery("select CP.concept_path, CP.concept_cd, CP.name_char FROM "+project_name+".concept_dimension CP where LOWER(CP.concept_cd) like LOWER('%"+term+"%')  order by CP.concept_path")
        //        .setResultTransformer(new AliasToBeanResultTransformer(ConceptDimension.class));
        
        
        return "";
	}



}