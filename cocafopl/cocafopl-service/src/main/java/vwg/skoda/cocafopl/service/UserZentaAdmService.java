package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.UserZentaAdm;

@Service
public class UserZentaAdmService {

	static Logger log = Logger.getLogger(UserZentaAdmService.class);
	
	@PersistenceContext(name = "UserZentaAdmService")
	private EntityManager entityManager;
	
	public UserZentaAdm getUserZentaAdm(String id) {
		log.trace("###\t\t getUserZentaAdm("+id+");");
		return entityManager.find(UserZentaAdm.class, id);
	}

//	public UserZentaAdm getUser(String netusername) {
//		log.trace("###\t\t getUser("+netusername+");");
//		return entityManager.createQuery("SELECT u FROM UserZentaAdm u WHERE u.netusername=:netusername", UserZentaAdm.class).setParameter("netusername", netusername).getSingleResult();
//	}
	
}
