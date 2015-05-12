package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.ArchKalkulaceMtZavView;

@Service
public class ArchKalkulaceMtZavViewService {

	static Logger log = Logger.getLogger(ArchKalkulaceMtZavViewService.class);

	@PersistenceContext(name = "ArchKalkulaceViewService")
	private EntityManager entityManager;

	public ArchKalkulaceMtZavView getArchKalkulaceMtZavViewId(String id) {
		log.trace("###\t\t getArchKalkulaceMtZavViewId(" + id + ");");
		return entityManager.find(ArchKalkulaceMtZavView.class, id);
	}
	
	public List<ArchKalkulaceMtZavView> getArchKalkulaceMtZavView() {
		log.trace("###\t\t getArchKalkulaceMtZavView();");
		List<ArchKalkulaceMtZavView> gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchKalkulaceMtZavView u ORDER BY u.kalkulace DESC ", ArchKalkulaceMtZavView.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public ArchKalkulaceMtZavView getArchKalkulaceMtZavView(int kalkulace, String mt, String zavod) {
		log.trace("###\t\t getArchKalkulaceMtZavView(" + kalkulace + ", " + mt + "-" + zavod + " );");
		ArchKalkulaceMtZavView gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchKalkulaceMtZavView u WHERE u.kalkulace=:kalkulace AND u.modelTr=:mt AND u.zavod=:zavod ORDER BY u.modelTr, u.zavod ", ArchKalkulaceMtZavView.class)
					.setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<ArchKalkulaceMtZavView> getArchKalkulaceMtZavView(String mt, String zavod) {
		log.trace("###\t\t getArchKalkulaceMtZavView(" +  mt + "-" + zavod + " );");
		List<ArchKalkulaceMtZavView> gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchKalkulaceMtZavView u WHERE u.modelTr=:mt AND u.zavod=:zavod ORDER BY u.modelTr, u.zavod ", ArchKalkulaceMtZavView.class)
					.setParameter("mt", mt).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<ArchKalkulaceMtZavView> getArchKalkulaceMtZavView(int kalkulace) {
		log.trace("###\t\t getArchKalkulaceMtZavView(" + kalkulace + ");");
		List<ArchKalkulaceMtZavView> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKalkulaceMtZavView u WHERE u.kalkulace=:kalkulace ORDER BY u.modelTr, u.zavod ", ArchKalkulaceMtZavView.class)
					.setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	


}
