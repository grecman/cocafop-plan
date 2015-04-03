package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Kalkulace;

@Service
public class KalkulaceService {

	static Logger log = Logger.getLogger(KalkulaceService.class);

	@PersistenceContext(name = "KalkulaceService")
	private EntityManager entityManager;

	public Kalkulace getKalkulaceId(long id) {
		log.trace("###\t\t getKalkulaceId(" + id + ");");
		return entityManager.find(Kalkulace.class, id);
	}
	
	public Kalkulace getKalkulace(int kalkulace) {
		log.trace("###\t\t getKalkulaceOne(" + kalkulace + ");");
		Kalkulace gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Kalkulace u WHERE u.kalkulace=:kalkulace", Kalkulace.class).setParameter("kalkulace", kalkulace).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	@Transactional
	public void addKalkulace(Kalkulace kalkulace) {
		log.trace("###\t\t addKalkulace(" + kalkulace + ")");
		entityManager.persist(kalkulace);
	}

	@Transactional
	public void setKalkulace(Kalkulace kalkulace) {
		log.trace("###\t\t setKalkulace(" + kalkulace + ")");
		entityManager.merge(kalkulace);
	}

	@Transactional
	public void removeKalkulace(Kalkulace kalkulace) {
		log.trace("###\t\t removeKalkulace(" + kalkulace + ")");
		Kalkulace u = getKalkulaceId(kalkulace.getId());
		entityManager.remove(u);
	}

	public List<Kalkulace> getKalkulaceAll() {
		log.trace("###\t\t getKalkulaceAll();");
		return entityManager.createQuery("SELECT u FROM Kalkulace u ORDER BY u.kalkulace DESC ", Kalkulace.class).getResultList();
	}

	public Integer getKalkulacePosledniRok() {
		log.trace("###\t\t getKalkulacePosledniRok();");
		Integer gre = null;
		try {
			String lastRok = entityManager.createQuery("SELECT substring(max(u.kalkulace),1,4) FROM Kalkulace u", String.class).getSingleResult();
			gre = lastRok == null ? null : Integer.valueOf(lastRok);
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<Integer> getKalkulaceRoky() {
		log.trace("###\t\t getKalkulaceRoky();");
		List<Integer> gre = null;
		try {
			gre = entityManager.createQuery("SELECT cast(substring(u.kalkulace,1,4) as int) FROM Kalkulace u GROUP BY substring(u.kalkulace,1,4) ORDER BY substring(u.kalkulace,1,4) DESC ", Integer.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	

	public List<Kalkulace> getKalkulaceRok(String rok) {
		log.trace("###\t\t getKalkulace(" + rok + ");");
		List<Kalkulace> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Kalkulace u WHERE substring(u.kalkulace,1,4)=:rok ORDER BY u.kalkulace", Kalkulace.class).setParameter("rok", rok).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public Kalkulace getKalkulacePlusMesic(int kalkulace) {
		log.debug("###\t\t getKalkulacePlusMesic(" + kalkulace + ");");
		Kalkulace gre;
		try {
			List<Kalkulace> kalk = entityManager.createQuery("SELECT u FROM Kalkulace u WHERE u.kalkulace > :kalkulace ORDER BY u.kalkulace ASC", Kalkulace.class).setParameter("kalkulace", kalkulace)
					.getResultList();
			gre = kalk.get(0);
		} catch (IndexOutOfBoundsException e) {
			return entityManager.createQuery("SELECT u FROM Kalkulace u WHERE u.kalkulace=:kalkulace", Kalkulace.class).setParameter("kalkulace", kalkulace).getSingleResult();
		}
		return gre;
	}

	public Kalkulace getKalkulaceMinusMesic(int kalkulace) {
		log.debug("###\t\t getKalkulaceMinusMesic(" + kalkulace + ");");
		Kalkulace gre;
		try {
			List<Kalkulace> kalk = entityManager.createQuery("SELECT u FROM Kalkulace u WHERE u.kalkulace < :kalkulace ORDER BY u.kalkulace DESC", Kalkulace.class)
					.setParameter("kalkulace", kalkulace).getResultList();
			gre = kalk.get(0);
		} catch (IndexOutOfBoundsException e) {
			return entityManager.createQuery("SELECT u FROM Kalkulace u WHERE u.kalkulace=:kalkulace", Kalkulace.class).setParameter("kalkulace", kalkulace).getSingleResult();
		}
		return gre;
	}


}
