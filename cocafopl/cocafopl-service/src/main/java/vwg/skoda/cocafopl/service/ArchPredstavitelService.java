package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchPredstavitel;

@Service
public class ArchPredstavitelService {
	static Logger log = Logger.getLogger(ArchPredstavitelService.class);

	@PersistenceContext(name = "ArchPredstavitelService")
	private EntityManager entityManager;

	@Transactional
	public void addArchPredstavitel(ArchPredstavitel archPredstavitel) {
		log.trace("###\t\t addArchPredstavitel(" + archPredstavitel + ")");
		entityManager.persist(archPredstavitel);
	}

	@Transactional
	public void setArchPredstavitel(ArchPredstavitel archPredstavitel) {
		log.trace("###\t\t setArchPredstavitel(" + archPredstavitel + ")");
		entityManager.merge(archPredstavitel);
	}

	@Transactional
	public void removeArchPredstavitel(ArchPredstavitel archPredstavitel) {
		log.trace("###\t\t removeArchPredstavitel(" + archPredstavitel + ")");
		ArchPredstavitel u = getArchPredstavitelId(archPredstavitel.getId());
		entityManager.remove(u);
	}

	public ArchPredstavitel getArchPredstavitelId(long id) {
		log.trace("###\t\t getArchPredstavitelId(" + id + ");");
		return entityManager.find(ArchPredstavitel.class, id);
	}

	public List<ArchPredstavitel> getArchPredstavitel(int kalkulace, String modelova_trida, String zavod) {
		log.trace("###\t\t getArchPredstavitel(" + kalkulace + ", " + modelova_trida +"-"+zavod+");");
		List<ArchPredstavitel> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchPredstavitel u WHERE u.gz40tKalkulace.kalkulace=:kalkulace AND u.modelTr=:modelova_trida AND u.zavod=:zavod ORDER BY u.cisloPred", ArchPredstavitel.class)
					.setParameter("kalkulace", kalkulace).setParameter("modelova_trida", modelova_trida).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public ArchPredstavitel getArchPredstavitel(int kalkulace, String mt, String zavod, int cisloPred) {
		log.trace("###\t\t getArchPredstavitel(" + kalkulace + ", " + mt + "-" + zavod + ", " + cisloPred + ");");
		ArchPredstavitel gre;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchPredstavitel u WHERE u.gz40tKalkulace.kalkulace=:kalkulace AND u.modelTr=:mt AND u.zavod=:zavod AND u.cisloPred=:cisloPred ",
							ArchPredstavitel.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cisloPred", cisloPred).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<ArchPredstavitel> getArchPredstavitelProMinuleCislo(int kalkulace, String mt, String zavod, String mk, String kodZeme) {
		log.trace("###\t\t getArchPredstavitelProMinuleCislo(" + kalkulace + ", " + mt + "-" + zavod + ", " + mk + ", "+kodZeme+");");
		List<ArchPredstavitel> gre;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchPredstavitel u WHERE u.gz40tKalkulace.kalkulace=:kalkulace AND u.modelTr=:mt AND u.zavod=:zavod AND u.modelovyKlic=:mk AND u.kodZeme=:kodZeme ",
							ArchPredstavitel.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("mk", mk).setParameter("kodZeme", kodZeme).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;			
	}
	
	public List<Integer> getArchPredstavitelCislaPredVRoce(String rok, String mt) {
		log.trace("###\t\t getArchPredstavitelCislaPredVRoce(" + rok + ", " + mt + ");");
		List<Integer> gre;
		try {
			gre = entityManager
					.createQuery("SELECT a.cisloPred FROM ArchPredstavitel a WHERE a.modelTr=:mt AND substring(a.gz40tKalkulace.kalkulace, 1, 4)=:rok GROUP BY a.cisloPred ORDER BY a.cisloPred ",
							Integer.class).setParameter("rok", rok).setParameter("mt", mt).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;			
	}

}
