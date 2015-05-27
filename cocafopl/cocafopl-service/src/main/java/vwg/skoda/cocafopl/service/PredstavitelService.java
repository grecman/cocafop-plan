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

	public Predstavitel getPredstavitel(String mt, Integer cisloPred) {
		log.trace("###\t\t getPredstavitel(" + mt + ", " + cisloPred + ");");
		Predstavitel gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.gz39tMt.modelTr=:mt AND u.cisloPred=:cisloPred ", Predstavitel.class).setParameter("mt", mt)
					.setParameter("cisloPred", cisloPred).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	// public Predstavitel getPredstavitel(String mk, String kodZeme) {
	// log.trace("###\t\t getPredstavitel(" + mk + ", " + kodZeme + ");");
	// Predstavitel gre = null;
	// try {
	// gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.modelovyKlic=:mk AND u.kodZeme=:kodZeme ", Predstavitel.class).setParameter("mk", mk)
	// .setParameter("kodZeme", kodZeme).getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// }
	// return gre;
	// }

	public List<Predstavitel> getPredstavitele(String mt, String zavod) {
		log.trace("###\t\t getPredstavitel(" + mt + ", " + zavod + ");");
		List<Predstavitel> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.gz39tMt.modelTr=:mt AND u.gz39tMt.zavod=:zavod ORDER BY u.cisloPred ", Predstavitel.class).setParameter("mt", mt)
					.setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public Predstavitel getPredstavitelComix(String mt, String zavod, String mk) {
		log.trace("###\t\t getPredstavitelComix(" + mt + ", " + zavod + ", " + mk + ");");
		Predstavitel gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM Predstavitel u WHERE u.gz39tMt.modelTr=:mt AND u.gz39tMt.zavod=:zavod AND u.modelovyKlic=:mk AND u.comix=1", Predstavitel.class)
					.setParameter("mt", mt).setParameter("zavod", zavod).setParameter("zavod", zavod).setParameter("mk", mk).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public Predstavitel getPredstavitelComix(int cisloPred, String mt, String zavod, String mk) {
		log.trace("###\t\t getPredstavitelComix(" + cisloPred + ", " + mt + ", " + zavod + ", " + mk + ");");
		Predstavitel gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM Predstavitel u WHERE u.cisloPred!=:cisloPred AND u.gz39tMt.modelTr=:mt AND u.gz39tMt.zavod=:zavod AND u.modelovyKlic=:mk AND u.comix=1", Predstavitel.class)
					.setParameter("cisloPred", cisloPred).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("zavod", zavod).setParameter("mk", mk).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
