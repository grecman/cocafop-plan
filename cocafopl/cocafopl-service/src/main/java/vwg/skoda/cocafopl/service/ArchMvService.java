package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchMv;

@Service
public class ArchMvService {
	static Logger log = Logger.getLogger(ArchMvService.class);

	@PersistenceContext(name = "ArchMvService")
	private EntityManager entityManager;
	
	
	public ArchMv getArchMvId(long id) {
		log.trace("###\t\t getArchMv(" + id + ");");
		return entityManager.find(ArchMv.class, id);
	}

	@Transactional
	public void addArchMv(ArchMv archMv) {
		log.trace("###\t\t addArchMv(" + archMv + ")");
		entityManager.persist(archMv);
	}

	@Transactional
	public void setArchMv(ArchMv archMv) {
		log.trace("###\t\t setArchMv(" + archMv + ")");
		entityManager.merge(archMv);
	}

	@Transactional
	public void removeArchMv(ArchMv archMv) {
		log.trace("###\t\t removeArchMv(" + archMv + ")");
		ArchMv u = getArchMvId(archMv.getId());
		entityManager.remove(u);
	}
	
	public ArchMv getArchMv(int kalkulace, String mt, String zavod, int cisloPred) {
		log.trace("###\t\t getArchMv(" + kalkulace + ", " + mt + "-" + zavod + ", " + cisloPred + ");");
		ArchMv gre;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM ArchMv u WHERE u.gz40tKalkulace.kalkulace=:kalkulace AND u.modelTr=:mt AND u.zavod=:zavod AND u.cisloPred=:cisloPred ",
							ArchMv.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).setParameter("cisloPred", cisloPred).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	



}
