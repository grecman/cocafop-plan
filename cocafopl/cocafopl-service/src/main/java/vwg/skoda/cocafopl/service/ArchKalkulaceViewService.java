package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.ArchKalkulaceView;

@Service
public class ArchKalkulaceViewService {
	
	static Logger log = Logger.getLogger(ArchKalkulaceViewService.class);

	@PersistenceContext(name = "ArchKalkulaceViewService")
	private EntityManager entityManager;
	
	public List<ArchKalkulaceView> getArchKalkulaceViewAll() {
		log.trace("###\t\t getArchKalkulaceViewAll();");
		List<ArchKalkulaceView> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKalkulaceView u ORDER BY u.kalkulace DESC ", ArchKalkulaceView.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	

}
