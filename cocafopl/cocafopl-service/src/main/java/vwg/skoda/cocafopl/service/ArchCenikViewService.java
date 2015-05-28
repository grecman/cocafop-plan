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
		log.trace("###\t\t getArchCenikView(" + kalkulace + ", " + cdilu + ", " + zavod +", "+dodavatel+ " );");
		List<ArchCenikView> gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchCenikView u WHERE u.kalkulace=:kalkulace AND u.cdilu LIKE :cdilu AND u.zavod=:zavod AND u.dodavatel LIKE :dodavatel ", ArchCenikView.class)
					.setParameter("kalkulace", kalkulace).setParameter("cdilu", cdilu).setParameter("zavod", zavod).setParameter("dodavatel", dodavatel).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	

}
