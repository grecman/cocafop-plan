package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchKurzCzk;

@Service
public class ArchKurzCzkService {
	
	static Logger log = Logger.getLogger(ArchKurzCzkService.class);

	@PersistenceContext(name = "ArchKurzCzkService")
	private EntityManager entityManager;
	
	public ArchKurzCzk getArchKurzCzkId(long id) {
		log.trace("###\t\t getArchKurzCzk(" + id + ");");
		return entityManager.find(ArchKurzCzk.class, id);
	}
	
	public ArchKurzCzk getArchKurzCzk(int kalkulace) {
		log.trace("###\t\t getArchKurzCzkOne(" + kalkulace + ");");
		ArchKurzCzk gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKurzCzk u WHERE u.kalkulace=:kalkulace", ArchKurzCzk.class).setParameter("kalkulace", kalkulace).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public void removeArchKurzCzk(int kalkulace) {
		log.trace("###\t\t removeArchKurzCzk(" + kalkulace + ");");
		entityManager.createQuery("DELETE u FROM ArchKurzCzk u WHERE u.gz40tKalkulace=:kalkulace", ArchKurzCzk.class).setParameter("kalkulace", kalkulace).getSingleResult();
	}

	@Transactional
	public void removeArchKurzCzk(ArchKurzCzk archKurzCzk) {
		log.trace("###\t\t removeArchKurzCzk(" + archKurzCzk + ")");
		ArchKurzCzk u = getArchKurzCzkId(archKurzCzk.getId());
		entityManager.remove(u);
	}



}
