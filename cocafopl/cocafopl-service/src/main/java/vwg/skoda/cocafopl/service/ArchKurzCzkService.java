package vwg.skoda.cocafopl.service;

import java.util.List;

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
	
	public List<ArchKurzCzk> getArchKurzCzk(int kalkulace) {
		log.trace("###\t\t getArchKurzCzkOne(" + kalkulace + ");");
		List<ArchKurzCzk> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKurzCzk u WHERE u.gz40tKalkulace.kalkulace=:kalkulace ", ArchKurzCzk.class).setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	@Transactional
	public void removeArchKurzCzkAll(int kalkulace) {
		log.trace("###\t\t removeArchKurzCzkAll(" + kalkulace + ")");
		entityManager.createQuery("DELETE FROM ArchKurzCzk a WHERE a.gz40tKalkulace.kalkulace = :kalkulace ").setParameter("kalkulace", kalkulace).executeUpdate();
	}



}
