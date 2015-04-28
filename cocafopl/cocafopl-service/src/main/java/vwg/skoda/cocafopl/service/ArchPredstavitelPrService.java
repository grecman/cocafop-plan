package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchPredstavitelPr;

@Service
public class ArchPredstavitelPrService {
	
	static Logger log = Logger.getLogger(ArchPredstavitelPrService.class);

	@PersistenceContext(name = "ArchPredstavitelPrService")
	private EntityManager entityManager;

	@Transactional
	public void addArchPredstavitelPr(ArchPredstavitelPr archPredstavitelPr) {
		log.trace("###\t\t addArchPredstavitel(" + archPredstavitelPr + ")");
		entityManager.persist(archPredstavitelPr);
	}

	@Transactional
	public void removeArchPredstavitel(ArchPredstavitelPr archPredstavitelPr) {
		log.trace("###\t\t removeArchPredstavitel(" + archPredstavitelPr + ")");
		ArchPredstavitelPr u = getArchPredstavitelId(archPredstavitelPr.getId());
		entityManager.remove(u);
	}
	
	public ArchPredstavitelPr getArchPredstavitelId(long id) {
		log.trace("###\t\t getArchPredstavitel(" + id + ");");
		return entityManager.find(ArchPredstavitelPr.class, id);
	}



}
