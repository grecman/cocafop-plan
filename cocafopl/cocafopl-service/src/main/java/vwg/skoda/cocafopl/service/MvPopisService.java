package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
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
	public void removeMvPopis(MvPopis MvPopis) {
		log.trace("###\t\t removeMvPopis(" + MvPopis + ")");
		MvPopis u = getMvPopis(MvPopis.getId());
		entityManager.remove(u);
	}

	public List<MvPopis> getMvPopisAll() {
		log.trace("###\t\t getMvPopisAll();");
		return entityManager.createQuery("SELECT u FROM MvPopis u ", MvPopis.class).getResultList();
	}

	// public MvPopis getMvPopis(String ????) {
	// log.trace("###\t\t getMvPopis("+????+");");
	// return entityManager.createQuery("SELECT u FROM MvPopis u WHERE u.?????=:????", MvPopis.class).setParameter("????", ????).getSingleResult();
	// }

}
