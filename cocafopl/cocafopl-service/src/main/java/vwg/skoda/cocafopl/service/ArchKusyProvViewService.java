package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.ArchKusyProvView;

@Service
public class ArchKusyProvViewService {

	static Logger log = Logger.getLogger(ArchKusyProvViewService.class);

	@PersistenceContext(name = "ArchKusyProvViewService")
	private EntityManager entityManager;

	public List<ArchKusyProvView> getArchKusyProvView(int kalkulace, String mt, String zavod, Integer cisloPred, String cdilu) {
		log.trace("###\t\t getArchKusyProvView(" + kalkulace + ", " + mt + "-" + zavod + ", " + cisloPred + ", " + cdilu + ");");
		List<ArchKusyProvView> gre = null;
		if (cisloPred == null) {
			try {
				gre = entityManager
						.createQuery("SELECT a FROM ArchKusyProvView a WHERE a.id.kalkulace=:kalkulace AND a.modelTr=:mt AND a.zavod=:zavod AND a.cdilu LIKE :cdilu ", ArchKusyProvView.class)
						.setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cdilu", cdilu).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		} else {
			try {
				gre = entityManager
						.createQuery("SELECT a FROM ArchKusyProvView a WHERE a.kalkulace=:kalkulace AND a.modelTr=:mt AND a.zavod=:zavod AND a.cisloPred=:cisloPred AND a.cdilu LIKE :cdilu ",
								ArchKusyProvView.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cisloPred", cisloPred)
						.setParameter("cdilu", cdilu).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		}
		return gre;
	}

	public List<ArchKusyProvView> getArchKusyProvView(int kalkulace, String cdilu) {
		log.trace("###\t\t getArchKusyProvView(" + kalkulace + ", " + cdilu + ");");
		List<ArchKusyProvView> gre = null;
		try {
			gre = entityManager.createQuery("SELECT a FROM ArchKusyProvView a WHERE a.kalkulace=:kalkulace AND a.cdilu LIKE :cdilu ", ArchKusyProvView.class).setParameter("kalkulace", kalkulace)
					.setParameter("cdilu", cdilu).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
