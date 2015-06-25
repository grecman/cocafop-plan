package vwg.skoda.cocafopl.service;

import java.math.BigDecimal;
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

	public ArchKalkulace getArchKalkulaceId(int id) {
		log.trace("###\t\t getArchKalkulace(" + id + ");");
		return entityManager.find(ArchKalkulace.class, id);
	}

	// public ArchKalkulace getArchKalkulace(int kalkulace) {
	// log.trace("###\t\t getArchKalkulaceOne(" + kalkulace + ");");
	// ArchKalkulace gre;
	// try {
	// gre = entityManager.createQuery("SELECT u FROM ArchKalkulace u WHERE u.kalkulace=:kalkulace", ArchKalkulace.class).setParameter("kalkulace", kalkulace).getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// }
	// return gre;
	// }

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
		ArchKalkulace u = getArchKalkulaceId(archKalkulace.getKalkulace());
		entityManager.remove(u);
	}

	public List<ArchKalkulace> getArchKalkulaceAll() {
		log.trace("###\t\t getArchKalkulaceAll();");
		List<ArchKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKalkulace u ORDER BY u.kalkulace DESC ", ArchKalkulace.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<ArchKalkulace> getArchKalkulaceAllSchvalene() {
		log.trace("###\t\t getArchKalkulaceAllSchvalene();");
		List<ArchKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM ArchKalkulace u WHERE u.schvalil IS NOT NULL ORDER BY u.kalkulace DESC ", ArchKalkulace.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public Integer getTerka() {
		log.trace("###\t\t getTerka();");
		BigDecimal gre = null;
		try {
			StringBuffer sql = new StringBuffer("select tech.GZ20F_GET_TERKA('A','%O','%') from dual");
			gre = (BigDecimal) entityManager.createNativeQuery(sql.toString()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre.intValue();
	}

	public String getTerkach() {
		log.trace("###\t\t getTerkach();");
		String gre = null;
		try {
			StringBuffer sql = new StringBuffer("select terkach from tech.gz20t08 where terka in (select tech.GZ20F_GET_TERKA('A','%O','%') from dual) ");
			gre = (String) entityManager.createNativeQuery(sql.toString()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
