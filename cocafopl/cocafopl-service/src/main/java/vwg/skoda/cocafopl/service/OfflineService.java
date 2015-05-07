package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Offline;

@Service
public class OfflineService {
	
	static Logger log = Logger.getLogger(OfflineService.class);

	@PersistenceContext(name = "OfflineService")
	private EntityManager entityManager;

	public Offline getOfflineId(long id) {
		log.trace("###\t\t getOfflineId(" + id + ");");
		return entityManager.find(Offline.class, id);
	}

	@Transactional
	public void addOffline(Offline offline) {
		log.trace("###\t\t addOffline(" + offline + ")");
		entityManager.persist(offline);
	}

	@Transactional
	public void setOffline(Offline offline) {
		log.trace("###\t\t setOffline(" + offline + ")");
		entityManager.merge(offline);
	}

	@Transactional
	public void removeOffline(Offline offline) {
		log.trace("###\t\t removeOffline(" + offline + ")");
		Offline u = getOfflineId(offline.getId());
		entityManager.remove(u);
	}
	
	public List<Offline> getOffline() {
		log.trace("###\t\t getOffline();");
		List<Offline> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Offline u ORDER BY u.casZadani DESC ", Offline.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<Offline> getOfflineAgendaNeukoncena(String agenda) {
		log.trace("###\t\t getOfflineAgendaNeukoncena();");
		List<Offline> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Offline u WHERE u.agenda=:agenda AND u.casUkonceni IS NULL ORDER BY u.casZadani DESC ", Offline.class).setParameter("agenda", agenda).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
