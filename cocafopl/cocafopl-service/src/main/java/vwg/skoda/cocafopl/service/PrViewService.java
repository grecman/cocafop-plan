package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.PrView;

@Service
public class PrViewService {

	private static Logger log = Logger.getLogger(PrViewService.class);

	@PersistenceContext(name = "PrViewService")
	private EntityManager entityManager;

	public PrView getPrView(String produkt, String prnr) {
		log.trace("###\t\t getPrView(" + produkt + ", " + prnr + ");");
		PrView gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PrView u WHERE u.pkz=:produkt AND u.prnr=:prnr ", PrView.class)
					.setParameter("produkt", produkt).setParameter("prnr", prnr).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<PrView> getPrViews(String produkt, String rodina ) {
		log.trace("###\t\t getPrView(" + produkt + ", "+rodina+");");
		List<PrView> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PrView u WHERE u.pkz=:produkt AND u.famkz=:rodina ORDER by u.prnr ", PrView.class)
					.setParameter("produkt", produkt).setParameter("rodina", rodina).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
