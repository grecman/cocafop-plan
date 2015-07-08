package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.ArchKusovnik;

@Service
public class ArchKusovnikService {

	static Logger log = Logger.getLogger(ArchKusovnikService.class);

	@PersistenceContext(name = "ArchKusovnikService")
	private EntityManager entityManager;

	public ArchKusovnik getArchKusovnikId(int id) {
		log.trace("###\t\t getArchKusovnik(" + id + ");");
		return entityManager.find(ArchKusovnik.class, id);
	}

	public List<ArchKusovnik> getArchKusovnik(int kalkulace, String produkt, String zavod, String cdilu) {
		log.trace("###\t\t getArchKusovnik(" + kalkulace + ", " + produkt + ", " + zavod + ", " + cdilu + ");");
		List<ArchKusovnik> gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT a FROM ArchKusovnik a WHERE a.gz40tKalkulace.kalkulace=:kalkulace AND a.produkt=:produkt AND a.zavod=:zavod AND a.cdilu LIKE :cdilu ", ArchKusovnik.class)
					.setParameter("kalkulace", kalkulace).setParameter("produkt", produkt).setParameter("zavod", zavod).setParameter("cdilu", cdilu).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	
	public List<ArchKusovnik> getArchKusovnik(int kalkulace, String produkt, String zavod) {
		log.trace("###\t\t getArchKusovnik(" + kalkulace + ", " + produkt + ", " + zavod + ");");
		List<ArchKusovnik> gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT a FROM ArchKusovnik a WHERE a.gz40tKalkulace.kalkulace=:kalkulace AND a.produkt=:produkt AND a.zavod=:zavod ", ArchKusovnik.class)
					.setParameter("kalkulace", kalkulace).setParameter("produkt", produkt).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	@Transactional
	public void removeArchKusovnikAll(int kalkulace) {
		log.trace("###\t\t removeArchKusovnikAll(" + kalkulace + ")");
		entityManager.createQuery("DELETE FROM ArchKusovnik a WHERE a.gz40tKalkulace.kalkulace = :kalkulace ").setParameter("kalkulace", kalkulace).executeUpdate();
	}
	
}
