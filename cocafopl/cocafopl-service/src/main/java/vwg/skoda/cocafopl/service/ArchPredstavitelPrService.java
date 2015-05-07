package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	public void addArchPredstavitelPr(ArchPredstavitelPr predstavitelPr) {
		log.trace("###\t\t addArchPredstavitelPr(" + predstavitelPr + ")");
		entityManager.persist(predstavitelPr);
	}

	
	public List<ArchPredstavitelPr> getArchPredstavitelPr(long idPredstavitel) {
		log.trace("###\t\t getArchPredstavitelPr(" + idPredstavitel+");");
		List<ArchPredstavitelPr> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchPredstavitelPr u WHERE u.gz40tPredstavitel.id=:idPredstavitel ORDER BY u.rodina ", ArchPredstavitelPr.class).setParameter("idPredstavitel", idPredstavitel).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}



}
