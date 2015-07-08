package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.ArchCenikView;

@Service
public class ArchCenikViewService {

	static Logger log = Logger.getLogger(ArchCenikViewService.class);

	@PersistenceContext(name = "ArchKalkulaceViewService")
	private EntityManager entityManager;

	public List<ArchCenikView> getArchCenikView(int kalkulace, String cdilu, String zavod, String dodavatel) {
		zavod = zavod.isEmpty() ? "%" : "%"+zavod+"%";
		dodavatel = dodavatel.isEmpty() ? "%" : "%" + dodavatel.toUpperCase() + "%";
		log.debug("###\t\t getArchCenikView(" + kalkulace + ", " + cdilu + ", " + zavod + ", " + dodavatel + " );");

		List<ArchCenikView> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT u FROM ArchCenikView u WHERE u.kalkulace=:kalkulace AND u.cdilu LIKE :cdilu AND u.cizav LIKE :zavod AND UPPER(NVL(u.dodavatel,' ')) LIKE :dodavatel  ORDER BY u.cdilu,u.cizav,u.pred ",
							ArchCenikView.class).setParameter("kalkulace", kalkulace).setParameter("cdilu", cdilu).setParameter("zavod", zavod).setParameter("dodavatel", dodavatel).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<ArchCenikView> getArchCenikView(int kalkulace) {
		log.trace("###\t\t getArchCenikView(" + kalkulace + ");");

		List<ArchCenikView> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchCenikView u WHERE u.kalkulace=:kalkulace  ", ArchCenikView.class).setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
