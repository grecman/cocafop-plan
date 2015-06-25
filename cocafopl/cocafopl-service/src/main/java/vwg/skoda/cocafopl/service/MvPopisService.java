package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MvPopis;

@Service
public class MvPopisService {

	static Logger log = Logger.getLogger(MvPopisService.class);

	@PersistenceContext(name = "MvPopisService")
	private EntityManager entityManager;

	public MvPopis getMvPopis(long id) {
		log.trace("###\t\t getMvPopis(" + id + ");");
		return entityManager.find(MvPopis.class, id);
	}

	@Transactional
	public void addMvPopis(MvPopis MvPopis) {
		log.trace("###\t\t addMvPopis(" + MvPopis + ")");
		entityManager.persist(MvPopis);
	}

	@Transactional
	public void setMvPopis(MvPopis MvPopis) {
		log.trace("###\t\t setMvPopis(" + MvPopis + ")");
		entityManager.merge(MvPopis);
	}

	@Transactional
	public void removeMvPopis(MvPopis mvPopis) {
		log.trace("###\t\t removeMvPopis(" + mvPopis + ")");
		MvPopis u = getMvPopis(mvPopis.getId());
		entityManager.remove(u);
	}

	@Transactional
	public void removeMvPopisAll(String user) {
		log.trace("###\t\t removeMvPopisAll(" + user + ")");
		entityManager.createQuery("DELETE FROM MvPopis a WHERE a.gz39tUser.netusername=:user ").setParameter("user", user).executeUpdate();
	}
	
	public List<MvPopis> getMvPopis(String user) {
		log.trace("###\t\t getMvPopis(" + user + ");");
		List<MvPopis> gre;
		try {
			gre = entityManager.createQuery("SELECT a FROM MvPopis a WHERE a.gz39tUser.netusername = :user ", MvPopis.class).setParameter("user", user).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
