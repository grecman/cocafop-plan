package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MessageExceptionService {
	
	static Logger log = Logger.getLogger(MessageExceptionService.class);
	
	@PersistenceContext(name = "MessageException")
	private EntityManager entityManager;
	
//	public List<MessageException> getMessageException(String agenda) {
//		log.trace("###\t\t getMessageException(" + agenda+");");
//		List<MessageException> gre;
//		try {
//			gre = entityManager.createQuery("SELECT u FROM MessageException u WHERE u.agenda=:agenda ORDER BY u.kod ", MessageException.class).setParameter("agenda", agenda).getResultList();
//		} catch (NoResultException e) {
//			return null;
//		}
//		return gre;
//	}

	

}
