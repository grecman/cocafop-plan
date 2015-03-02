package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class MvPopisMesExceptionService {
	
	static Logger log = Logger.getLogger(MvPopisMesExceptionService.class);
	
	@PersistenceContext(name = "MvPopisMesExceptionMesExceptionService")
	private EntityManager entityManager;
	
//	public MvPopisMesException getMvPopisMesException(long id) {
//		log.trace("###\t\t getMvPopisMesException(" + id + ");");
//		return entityManager.find(MvPopisMesException.class, id);
//	}
//
//	@Transactional
//	public void addMvPopisMesException(MvPopisMesException MvPopisMesException) {
//		log.trace("###\t\t addMvPopisMesException(" + MvPopisMesException + ")");
//		entityManager.persist(MvPopisMesException);
//	}
//
//	@Transactional
//	public void setMvPopisMesException(MvPopisMesException MvPopisMesException) {
//		log.trace("###\t\t setMvPopisMesException(" + MvPopisMesException + ")");
//		entityManager.merge(MvPopisMesException);
//	}
//
//	@Transactional
//	public void removeMvPopisMesException(MvPopisMesException MvPopisMesException) {
//		log.trace("###\t\t removeMvPopisMesException(" + MvPopisMesException + ")");
//		MvPopisMesException u = getMvPopisMesException(MvPopisMesException.getId());
//		entityManager.remove(u);
//	}
//
//	public List<MvPopisMesException> getMvPopisMesExceptionAll() {
//		log.trace("###\t\t getMvPopisMesExceptionAll();");
//		return entityManager.createQuery("SELECT u FROM MvPopisMesException u ", MvPopisMesException.class).getResultList();
//	}

}
