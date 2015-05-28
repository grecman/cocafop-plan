package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Protokol;

@Service
public class ProtokolService {
	
	static Logger log = Logger.getLogger(ProtokolService.class);
	
	@PersistenceContext(name = "ProtokolService")
	private EntityManager entityManager;

	@Transactional
 	public void addProtokol(Protokol newProtokol) {
		log.trace("###\t\t addProtokol("+newProtokol.getAction()+")");
		entityManager.persist(newProtokol);		
	}
 	
	public List<Protokol> getUserLogin(String netusername) {
		log.debug("###\t\t getUserLogin("+netusername+");");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.netusername=:netusername AND a.action LIKE 'Login%' ORDER BY a.time desc", Protokol.class).setParameter("netusername", netusername).getResultList();
	}
	
	public List<Protokol> getAllUserActivity(String netusername) {
		log.debug("###\t\t getAllUserActivity("+netusername+");");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.action != 'Login do aplikace' AND a.netusername=:netusername ORDER BY a.time desc", Protokol.class).setParameter("netusername", netusername).getResultList();
	}
	
	public List<Protokol> getAllLogin() {
		log.debug("###\t\t getAllLogin();");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.action LIKE 'Login%' ORDER BY a.time desc", Protokol.class).getResultList();
	}
	
	public List<Protokol> getAll() {
		log.debug("###\t\t getAll();");
		return entityManager.createQuery("SELECT a FROM Protokol ORDER BY a.time desc", Protokol.class).getResultList();
	}

	public List<Protokol> getAllWithoutLogin() {
		log.debug("###\t\t getAllWithoutLogin();");
		return entityManager.createQuery("SELECT a FROM Protokol a WHERE a.action != 'Login do aplikace' ORDER BY a.time desc", Protokol.class).getResultList();
	}
	
	public List<Protokol> getAllUsers() {
		log.debug("###\t\t getAllUsers();");
		return entityManager.createQuery("SELECT distinct a.netusername FROM Protokol a ORDER BY a.netusername", Protokol.class).getResultList();
	}
	
 	public String getDbName(){
 		log.debug("###\t\t getDbName()");
 		Object globalName = entityManager.createNativeQuery("SELECT GLOBAL_NAME FROM GLOBAL_NAME ").getSingleResult();
 		return globalName.toString();
 	}
 	

}
