package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.KalkulaceMtZavod;

@Service
public class KalkulaceMtZavodService {
	
	private static Logger log = Logger.getLogger(KalkulaceMtZavodService.class);
	
	@PersistenceContext(name = "KalkulaceMtZavod")
	private EntityManager entityManager;
	
	
	public KalkulaceMtZavod getKalkulaceMtZavod(long id) {
		log.trace("###\t\t getKalkulaceMtZavod(" + id + ");");
		return entityManager.find(KalkulaceMtZavod.class, id);
	}
	
	public List<KalkulaceMtZavod> getKalkulaceMtZavod(int rok) {
		log.trace("###\t\t getKalkulaceMtZavod(" + rok + ");");
		return entityManager.createQuery("SELECT u FROM KalkulaceMtZavod u WHERE u.rok=:rok ORDER BY u.kalkulace ", KalkulaceMtZavod.class).setParameter("rok", rok).getResultList();
	}
	
}
