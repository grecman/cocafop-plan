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
		log.trace("###\t\t getArchPredstavitel(" + id + ");");
		return entityManager.find(ArchPredstavitel.class, id);
	}

	public List<ArchPredstavitel> getArchPredstavitel(int kalkulace) {
		log.trace("###\t\t getArchPredstavitelOne(" + kalkulace + ");");
		List<ArchPredstavitel> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchPredstavitel u WHERE u.archKalkulace.kalkulace=:kalkulace", ArchPredstavitel.class).setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<ArchPredstavitel> getArchPredstavitel(int kalkulace, String modelova_trida) {
		log.trace("###\t\t getArchPredstavitelOne(" + kalkulace +", "+modelova_trida+ ");");
		List<ArchPredstavitel> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchPredstavitel u WHERE u.archKalkulace.kalkulace=:kalkulace AND u.modelTr=:modelova_trida ORDER BY u.cisloPred", ArchPredstavitel.class).setParameter("kalkulace", kalkulace).setParameter("modelova_trida", modelova_trida).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
