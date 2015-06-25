package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MvPopisMessage;

@Service
public class MvPopisMessageService {

	static Logger log = Logger.getLogger(MvPopisMessageService.class);
	
	@PersistenceContext(name = "MvPopisMessageService")
	private EntityManager entityManager;
	
	public MvPopisMessage getMvPopisMessage(long id) {
		log.trace("###\t\t getMvPopisMessage(" + id + ");");
		return entityManager.find(MvPopisMessage.class, id);
	}

	@Transactional
	public void addMvPopisMessage(MvPopisMessage MvPopisMessage) {
		log.trace("###\t\t addMvPopisMessage(" + MvPopisMessage + ")");
		entityManager.persist(MvPopisMessage);
	}

	@Transactional
	public void setMvPopisMessage(MvPopisMessage MvPopisMessage) {
		log.trace("###\t\t setMvPopisMessage(" + MvPopisMessage + ")");
		entityManager.merge(MvPopisMessage);
	}

	@Transactional
	public void removeMvPopisMessage(MvPopisMessage MvPopisMessage) {
		log.trace("###\t\t removeMvPopisMessage(" + MvPopisMessage + ")");
		MvPopisMessage u = getMvPopisMessage(MvPopisMessage.getId());
		entityManager.remove(u);
	}

	public List<MvPopisMessage> getMvPopisMessageAll() {
		log.trace("###\t\t getMvPopisMessageAll();");
		return entityManager.createQuery("SELECT u FROM MvPopisMessage u ", MvPopisMessage.class).getResultList();
	}
	
	
	public List<MvPopisMessage> getMvPopisMessageAll(long idMvPopis) {
		log.trace("###\t\t getMvPopisMessageAll(" + idMvPopis+");");
		List<MvPopisMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM MvPopisMessage u WHERE u.gz39tMvPopis.id=:idMvPopis ORDER BY u.kod ", MvPopisMessage.class).setParameter("idMvPopis", idMvPopis).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<MvPopisMessage> getMvPopisMessageOnlyException(long idMvPopis) {
		log.trace("###\t\t getMvPopisMessageWithException(" + idMvPopis+");");
		List<MvPopisMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM MvPopisMessage u WHERE u.gz39tMvPopis.id=:idMvPopis AND u.kod IN (SELECT e.kod FROM MessageException e) ORDER BY u.kod ", MvPopisMessage.class).setParameter("idMvPopis", idMvPopis).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<MvPopisMessage> getMvPopisMessageWithoutException(long idMvPopis) {
		log.trace("###\t\t getMvPopisMessageWithoutException(" + idMvPopis+");");
		List<MvPopisMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM MvPopisMessage u WHERE u.gz39tMvPopis.id=:idMvPopis AND u.kod NOT IN (SELECT e.kod FROM MessageException e) ORDER BY u.kod ", MvPopisMessage.class).setParameter("idMvPopis", idMvPopis).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
}
