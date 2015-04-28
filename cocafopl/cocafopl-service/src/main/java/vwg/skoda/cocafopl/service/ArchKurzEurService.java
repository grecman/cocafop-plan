package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchKurzEur;

@Service
public class ArchKurzEurService {
	static Logger log = Logger.getLogger(ArchKurzEurService.class);

	@PersistenceContext(name = "ArchKurzEurService")
	private EntityManager entityManager;
	
	public ArchKurzEur getArchKurzEurId(long id) {
		log.trace("###\t\t getArchKurzEur(" + id + ");");
		return entityManager.find(ArchKurzEur.class, id);
	}
	
	public ArchKurzEur getArchKurzEur(int kalkulace) {
		log.trace("###\t\t getArchKurzEurOne(" + kalkulace + ");");
		ArchKurzEur gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKurzEur u WHERE u.kalkulace=:kalkulace", ArchKurzEur.class).setParameter("kalkulace", kalkulace).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	@Transactional
	public void addArchKurzEur(ArchKurzEur archKurzEur) {
		log.trace("###\t\t addArchKurzEur(" + archKurzEur + ")");
		entityManager.persist(archKurzEur);
	}

	@Transactional
	public void setArchKurzEur(ArchKurzEur archKurzEur) {
		log.trace("###\t\t setArchKurzEur(" + archKurzEur + ")");
		entityManager.merge(archKurzEur);
	}

	@Transactional
	public void removeArchKurzEur(ArchKurzEur archKurzEur) {
		log.trace("###\t\t removeArchKurzEur(" + archKurzEur + ")");
		ArchKurzEur u = getArchKurzEurId(archKurzEur.getId());
		entityManager.remove(u);
	}



}
