package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchCenikSapService {
	
	static Logger log = Logger.getLogger(ArchCenikSapService.class);

	@PersistenceContext(name = "ArchCenikSapService")
	private EntityManager entityManager;
	
	@Transactional
	public void removeArchCenikSapAll(int kalkulace) {
		log.trace("###\t\t removeArchCenikSapAll(" + kalkulace + ")");
		entityManager.createQuery("DELETE FROM ArchCenikSap a WHERE a.gz40tKalkulace.kalkulace = :kalkulace ").setParameter("kalkulace", kalkulace).executeUpdate();
	}



}
