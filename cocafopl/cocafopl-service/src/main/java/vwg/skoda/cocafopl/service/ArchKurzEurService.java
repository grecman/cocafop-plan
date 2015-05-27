package vwg.skoda.cocafopl.service;

import java.util.List;

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
	
	public List<ArchKurzEur> getArchKurzEur(int kalkulace) {
		log.trace("###\t\t getArchKurzEurOne(" + kalkulace + ");");
		List<ArchKurzEur> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKurzEur u WHERE u.gz40tKalkulace.kalkulace=:kalkulace ", ArchKurzEur.class).setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	@Transactional
	public void removeArchKurzEurAll(int kalkulace) {
		log.trace("###\t\t removeArchKurzEurAll(" + kalkulace + ")");
		entityManager.createQuery("DELETE FROM ArchKurzEur a WHERE a.gz40tKalkulace.kalkulace = :kalkulace ").setParameter("kalkulace", kalkulace).executeUpdate();
	}


}
