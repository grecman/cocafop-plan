package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ArchKusyProvService {

	static Logger log = Logger.getLogger(ArchKusyProvService.class);

	@PersistenceContext(name = "ArchKusyProvService")
	private EntityManager entityManager;
/*
	public List<ArchKusyProv> getArchKusyProv(int kalkulace, String mt, String zavod, Integer cisloPred, String cdilu) {
		log.debug("###\t\t getArchKusyProv(" + kalkulace + ", " + mt + "-" + zavod + ", " + cisloPred + ", " + cdilu + ");");
		List<ArchKusyProv> gre = null;
		if (cisloPred == null) {
			try {
				gre = entityManager
						.createQuery("SELECT a FROM ArchKusyProv a WHERE a.id.kalkulace=:kalkulace AND a.id.modelTr=:mt AND a.id.zavod=:zavod AND a.id.cdilu LIKE :cdilu ", ArchKusyProv.class)
						.setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cdilu", cdilu).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		} else {
			try {
				gre = entityManager
						.createQuery("SELECT a FROM ArchKusyProv a WHERE a.id.kalkulace=:kalkulace AND a.id.modelTr=:mt AND a.id.zavod=:zavod AND a.id.cisloPred=:cisloPred AND a.id.cdilu LIKE :cdilu ", ArchKusyProv.class)
						.setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cisloPred", cisloPred).setParameter("cdilu", cdilu).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		}
		return gre;
	}
*/
}
