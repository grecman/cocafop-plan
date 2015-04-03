package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchKalkulace;

@Service
public class ArchKalkulaceService {
	
	static Logger log = Logger.getLogger(ArchKalkulaceService.class);

	@PersistenceContext(name = "ArchKalkulaceService")
	private EntityManager entityManager;

	public ArchKalkulace getArchKalkulaceId(long id) {
		log.trace("###\t\t getArchKalkulace(" + id + ");");
		return entityManager.find(ArchKalkulace.class, id);
	}
	
	public ArchKalkulace getArchKalkulace(int kalkulace) {
		log.trace("###\t\t getArchKalkulaceOne(" + kalkulace + ");");
		ArchKalkulace gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKalkulace u WHERE u.kalkulace=:kalkulace", ArchKalkulace.class).setParameter("kalkulace", kalkulace).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	@Transactional
	public void addArchKalkulace(ArchKalkulace archKalkulace) {
		log.trace("###\t\t addArchKalkulace(" + archKalkulace + ")");
		entityManager.persist(archKalkulace);
	}

	@Transactional
	public void setArchKalkulace(ArchKalkulace archKalkulace) {
		log.trace("###\t\t setArchKalkulace(" + archKalkulace + ")");
		entityManager.merge(archKalkulace);
	}

	@Transactional
	public void removeArchKalkulace(ArchKalkulace archKalkulace) {
		log.trace("###\t\t removeArchKalkulace(" + archKalkulace + ")");
		ArchKalkulace u = getArchKalkulaceId(archKalkulace.getId());
		entityManager.remove(u);
	}

	public List<ArchKalkulace> getArchKalkulaceAll() {
		log.trace("###\t\t getArchKalkulaceAll();");
		return entityManager.createQuery("SELECT u FROM ArchKalkulace u ORDER BY u.kalkulace DESC ", ArchKalkulace.class).getResultList();
	}


}
