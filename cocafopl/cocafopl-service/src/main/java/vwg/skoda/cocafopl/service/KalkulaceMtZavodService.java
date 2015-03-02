package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.KalkulaceMtZavod;

@Service
public class KalkulaceMtZavodService {
	
	static Logger log = Logger.getLogger(KalkulaceMtZavodService.class);
	
	@PersistenceContext(name = "KalkulaceService")
	private EntityManager entityManager;
	
	
	public KalkulaceMtZavod getKalkulaceMtZavod(long id) {
		log.trace("###\t\t getKalkulaceMtZavod(" + id + ");");
		return entityManager.find(KalkulaceMtZavod.class, id);
	}
	
	public List<KalkulaceMtZavod> getKalkulaceMtZavod(int rok) {
		log.trace("###\t\t getKalkulaceMtZavod(" + rok + ");");
		return entityManager.createQuery("SELECT u FROM KalkulaceMtZavod u WHERE u.rok=:rok ORDER BY u.kalkulace ", KalkulaceMtZavod.class).setParameter("rok", rok).getResultList();
	}
	
	public List<String> getIdKalkulacaAndIdMt() {
		// Spoji entity MT a KALKULACE (pres platnost) a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace :) 
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a v entite MtKalkulace jsou spojeny.
		log.trace("###\t\t getIdKalkulacaAndIdMt()");
		List<String> gre;
		try {
			gre = entityManager
					.createQuery(
							"select k.id||';'||m.id FROM Kalkulace k, Mt m WHERE m.platnostOd <= k.kalkulace AND m.platnostDo >= k.kalkulace AND m.id||k.id NOT IN (SELECT mk.gz39tMt.id || mk.gz39tKalkulace.id FROM MtKalkulace mk)",
							String.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
