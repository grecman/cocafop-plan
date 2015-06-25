package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Mt;

@Service
public class MtService {
	
	static Logger log = Logger.getLogger(MtService.class);
	
	@PersistenceContext(name = "MtService")
	private EntityManager entityManager;
	
	
	public Mt getMt(long id) {
		log.trace("###\t\t getMt(" + id + ");");
		return entityManager.find(Mt.class, id);
	}
	
	@Transactional
	public void addMt(Mt mt) {
		log.trace("###\t\t addMt(" + mt + ")");
		entityManager.persist(mt);
	}

	@Transactional
	public void setMt(Mt mt) {
		log.trace("###\t\t setMt(" + mt + ")");
		entityManager.merge(mt);
	}

	@Transactional
	public void removeMt(Mt mt) {
		log.trace("###\t\t removeMt(" + mt + ")");
		Mt u = getMt(mt.getId());
		entityManager.remove(u);
	}

	public List<Mt> getMtAll() {
		log.trace("###\t\t getMtAll();");
		return entityManager.createQuery("SELECT u FROM Mt u ORDER BY u.modelTr, u.zavod ", Mt.class).getResultList();
	}
	
	public List<Mt> getMtActual() {
		log.trace("###\t\t getMtAll();");
		return entityManager.createQuery("SELECT u FROM Mt u WHERE u.platnostDo >= to_char(sysdate,'RRRRMM') ORDER BY u.modelTr, u.zavod ", Mt.class).getResultList();
	}
	
	public Mt getMt(String mt, String zavod) {
		log.trace("###\t\t getMt(" + mt+"-"+zavod + ");");
		Mt gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Mt u WHERE u.modelTr=:modetTr AND u.zavod=:zavod  ORDER BY u.modelTr, u.zavod ", Mt.class).setParameter("modetTr", mt).setParameter("zavod", zavod).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<Mt> getMtPlatneK(int rrrrmm) {
		log.trace("###\t\t getMtPlatneK(" + rrrrmm + ");");
		List<Mt> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Mt u WHERE u.platnostOd <= :plat AND u.platnostDo >= :plat  ORDER BY u.modelTr, u.zavod ", Mt.class).setParameter("plat", rrrrmm).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
