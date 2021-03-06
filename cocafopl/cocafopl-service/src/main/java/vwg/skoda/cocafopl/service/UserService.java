package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.User;

@Service
public class UserService {
	
	static Logger log = Logger.getLogger(UserService.class);
	
	@PersistenceContext(name = "UserService")
	private EntityManager entityManager;
	
	public User getUser(long id) {
		log.trace("###\t\t getUser("+id+");");
		return entityManager.find(User.class, id);
	}	
	
	
	public User getUser(String netusername) {
		log.trace("###\t\t getUser("+netusername+");");
		return entityManager.createQuery("SELECT u FROM User u WHERE u.netusername=:netusername", User.class).setParameter("netusername", netusername).getSingleResult();
	}
	
	public List<User> getUsers() {
		log.trace("###\t\t getUsers();");
		return entityManager.createQuery("SELECT u FROM User u ORDER BY NLSSORT(u.prijmeni, 'NLS_SORT=CZECH')", User.class).getResultList();
	}

	@Transactional
 	public void addUser(User user) {
		log.trace("###\t\t addUser("+user+")");
		entityManager.persist(user);		
	}
	
	@Transactional
 	public void updateUser(User user) {
		log.trace("###\t\t updateUser("+user+")");
		entityManager.merge(user);		
	}
	
	@Transactional
 	public void removeUser(User user) {
		log.trace("###\t\t removeUser("+user+")");
		User u = getUser(user.getId());
		entityManager.remove(u);		
	}	

	public Boolean existUser(String netusername) {
		log.trace("###\t\t existUser(" + netusername +")");
		try {
			String neco = null;
			neco =  entityManager.createQuery("SELECT m.netusername FROM User m WHERE m.netusername=:netusername",	String.class).setParameter("netusername", netusername).getSingleResult();
			log.trace("\t\t   ... User nalezen ("+neco+")");
			return true;
		} catch (Exception e) {
			log.error("\t\t   ... User neexistuje (nasledne bude zalozen):",e);
			return false;
		}
	}
 	
// 	public String getDbName(){
// 		log.trace("###\t\t getDbName()");
// 		Object globalName = entityManager.createNativeQuery("SELECT GLOBAL_NAME FROM GLOBAL_NAME ").getSingleResult();
// 		return globalName.toString();
// 	}

//	public Date getDbTime() {
//		log.debug("###\t\t getDbTime();");
//		return entityManager.createQuery("SELECT current_timestamp() FROM User  WHERE rownum=1", Date.class).getSingleResult();
//	}


}
