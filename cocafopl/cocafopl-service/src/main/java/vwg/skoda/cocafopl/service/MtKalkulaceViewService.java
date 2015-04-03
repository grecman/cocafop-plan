package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.MtKalkulaceView;

@Service
public class MtKalkulaceViewService {
	
	static Logger log = Logger.getLogger(MtKalkulaceViewService.class);

	@PersistenceContext(name = "MtKalkulaceViewService")
	private EntityManager entityManager;

	
	public List<MtKalkulaceView> getMtKalkulaceView(int kalkulace) {
		log.trace("###\t\t getMtKalkulaceView("+kalkulace+");");
		List<MtKalkulaceView> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM MtKalkulaceView u WHERE u.kalkulace=:kalk ORDER BY u.modelTr", MtKalkulaceView.class).setParameter("kalk", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
