package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.Napoveda;

@Service
public class NapovedaService {
	
	static Logger log = Logger.getLogger(NapovedaService.class);

	@PersistenceContext(name = "NapovedaService")
	private EntityManager entityManager;

	public Napoveda getNapovedaId(long id) {
		log.trace("###\t\t getNapovedaId(" + id + ");");
		return entityManager.find(Napoveda.class, id);
	}
	
	@Transactional
	public void addNapoveda(Napoveda Napoveda) {
		log.trace("###\t\t addNapoveda(" + Napoveda + ")");
		entityManager.persist(Napoveda);
	}

	@Transactional
	public void removeNapoveda(long id) {
		log.trace("###\t\t removeNapoveda(" + id + ")");
		Napoveda u = getNapovedaId(id);
		entityManager.remove(u);
	}
	
	public List<Napoveda> getNapoveda() {
		log.trace("###\t\t getNapoveda();");
		List<Napoveda> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM Napoveda u ORDER BY u.poradi ", Napoveda.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
