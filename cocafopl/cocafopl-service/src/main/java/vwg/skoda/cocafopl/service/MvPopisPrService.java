package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MvPopisPr;

@Service
public class MvPopisPrService {

	static Logger log = Logger.getLogger(MvPopisPrService.class);
	
	@PersistenceContext(name = "MvPopisPrService")
	private EntityManager entityManager;
	
	public MvPopisPr getMvPopisPr(long id) {
		log.trace("###\t\t getMvPopisPr(" + id + ");");
		return entityManager.find(MvPopisPr.class, id);
	}

	@Transactional
	public void addMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t addMvPopisPr(" + MvPopisPr + ")");
		entityManager.persist(MvPopisPr);
	}

	@Transactional
	public void setMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t setMvPopisPr(" + MvPopisPr + ")");
		entityManager.merge(MvPopisPr);
	}

	@Transactional
	public void removeMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t removeMvPopisPr(" + MvPopisPr + ")");
		MvPopisPr u = getMvPopisPr(MvPopisPr.getId());
		entityManager.remove(u);
	}

	public List<MvPopisPr> getMvPopisPrAll() {
		log.trace("###\t\t getMvPopisPrAll();");
		return entityManager.createQuery("SELECT u FROM MvPopisPr u ", MvPopisPr.class).getResultList();
	}
	
}
