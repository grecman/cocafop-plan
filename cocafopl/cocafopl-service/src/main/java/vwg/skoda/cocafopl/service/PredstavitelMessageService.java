package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.PredstavitelMessage;

@Service
public class PredstavitelMessageService {

	static Logger log = Logger.getLogger(PredstavitelMessageService.class);
	
	@PersistenceContext(name = "PredstavitelMessageService")
	private EntityManager entityManager;
	
	
	public PredstavitelMessage getPredstavitelMessageId(long id) {
		log.trace("###\t\t getPredstavitelMessage(" + id + ");");
		return entityManager.find(PredstavitelMessage.class, id);
	}
	
	@Transactional
	public void addPredstavitelMessage(PredstavitelMessage predstavitelMessage) {
		log.trace("###\t\t addPredstavitelMessage(" + predstavitelMessage + ")");
		entityManager.persist(predstavitelMessage);
	}

	@Transactional
	public void setPredstavitelMessage(PredstavitelMessage predstavitelMessage) {
		log.trace("###\t\t setPredstavitelMessage(" + predstavitelMessage + ")");
		entityManager.merge(predstavitelMessage);
	}

	@Transactional
	public void removePredstavitelMessage(PredstavitelMessage predstavitelMessage) {
		log.trace("###\t\t removePredstavitelMessage(" + predstavitelMessage + ")");
		PredstavitelMessage u = getPredstavitelMessageId(predstavitelMessage.getId());
		entityManager.remove(u);
	}
	
	@Transactional
	public void removePredstavitelMessageAll(long idPredstavitelKalkulace) {
		log.trace("###\t\t removePredstavitelMessageAll(" + idPredstavitelKalkulace + ")");
		entityManager.createQuery("DELETE FROM PredstavitelMessage a WHERE a.gz39tPredstavitelKalkulace.id = :idPk ").setParameter("idPk", idPredstavitelKalkulace).executeUpdate();
	}
	
	
	public List<PredstavitelMessage> getPredstavitelMessageAll(long idPredstavitelKalkulace) {
		log.trace("###\t\t getPredstavitelMessageAll(" + idPredstavitelKalkulace+");");
		List<PredstavitelMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelMessage u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace ORDER BY u.kod ", PredstavitelMessage.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<PredstavitelMessage> getPredstavitelMessageOnlyException(long idPredstavitelKalkulace) {
		log.trace("###\t\t getPredstavitelMessageWithException(" + idPredstavitelKalkulace+");");
		List<PredstavitelMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelMessage u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace AND u.kod IN (SELECT e.kod FROM MessageException e) ORDER BY u.kod ", PredstavitelMessage.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<PredstavitelMessage> getPredstavitelMessageWithoutException(long idPredstavitelKalkulace) {
		log.trace("###\t\t getPredstavitelMessageWithoutException(" + idPredstavitelKalkulace+");");
		List<PredstavitelMessage> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelMessage u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace AND u.kod NOT IN (SELECT e.kod FROM MessageException e) ORDER BY u.kod ", PredstavitelMessage.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
}
