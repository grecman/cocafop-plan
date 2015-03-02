package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Predstavitel;

@Service
public class PredstavitelService {
	
	static Logger log = Logger.getLogger(PredstavitelService.class);

	@PersistenceContext(name = "PredstavitelService")
	private EntityManager entityManager;

	public Predstavitel getPredstavitel(long id) {
		log.trace("###\t\t getPredstavitel(" + id + ");");
		return entityManager.find(Predstavitel.class, id);
	}

	@Transactional
	public void addPredstavitel(Predstavitel predstavitel) {
		log.trace("###\t\t addPredstavitel(" + predstavitel + ")");
		entityManager.persist(predstavitel);
	}

	@Transactional
	public void setPredstavitel(Predstavitel predstavitel) {
		log.trace("###\t\t setPredstavitel(" + predstavitel + ")");
		entityManager.merge(predstavitel);
	}

	@Transactional
	public void removePredstavitel(Predstavitel predstavitel) {
		log.trace("###\t\t removePredstavitel(" + predstavitel + ")");
		Predstavitel u = getPredstavitel(predstavitel.getId());
		entityManager.remove(u);
	}

	public List<Predstavitel> getPredstavitelAll() {
		log.trace("###\t\t getPredstavitelAll();");
		return entityManager.createQuery("SELECT u FROM Predstavitel u ORDER BY u.predstavitel DESC ", Predstavitel.class).getResultList();
	}
	
	public List<Integer> getPredstavitelRoky() {
		log.trace("###\t\t getPredstavitelRoky();");
		List<Integer> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u.rok FROM Predstavitel u GROUP BY u.rok ORDER BY u.rok DESC", Integer.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<Predstavitel> getPredstaviteleVRoce(int rok) {
		log.trace("###\t\t getPredstaviteleVRoce("+rok+");");
		List<Predstavitel> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.rok=:rok ORDER BY u.rok DESC", Predstavitel.class).setParameter("rok", rok).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public Predstavitel getPredstavitel(Integer rok, String mt, Integer cisloPred) {
		log.trace("###\t\t getPredstavitel("+rok+", "+mt+", "+cisloPred+");");
		Predstavitel gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.rok=:rok AND u.gz39tMt.modelTr=:mt AND u.cisloPred=:cisloPred ", Predstavitel.class).setParameter("rok", rok).setParameter("mt", mt).setParameter("cisloPred", cisloPred).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<Predstavitel> getPredstavitel(int rok, String mt, String zavod) {
		log.trace("###\t\t getPredstavitel("+rok+", "+mt+", "+zavod+");");
		List<Predstavitel> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.rok=:rok AND u.gz39tMt.modelTr=:mt AND u.gz39tMt.zavod=:zavod ORDER BY u.cisloPred ", Predstavitel.class).setParameter("rok", rok).setParameter("mt", mt).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
