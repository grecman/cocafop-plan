package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MtKalkulace;

@Service
public class MtKalkulaceService {

	static Logger log = Logger.getLogger(MtKalkulaceService.class);

	@PersistenceContext(name = "MtKalkulaceService")
	private EntityManager entityManager;

	public MtKalkulace getMtKalkulaceId(long id) {
		log.trace("###\t\t getMtMtKalkulaceId(" + id + ");");
		return entityManager.find(MtKalkulace.class, id);
	}

	@Transactional
	public void addMtKalkulace(MtKalkulace mtKalkulace) {
		log.trace("###\t\t addMtKalkulace(" + mtKalkulace + ")");
		entityManager.persist(mtKalkulace);
	}

	@Transactional
	public void setMtKalkulace(MtKalkulace mtKalkulace) {
		log.trace("###\t\t setMtKalkulace(" + mtKalkulace + ")");
		entityManager.merge(mtKalkulace);
	}

	@Transactional
	public void removeMtKalkulace(MtKalkulace mtKalkulace) {
		log.trace("###\t\t removeMtKalkulace(" + mtKalkulace + ")");
		MtKalkulace u = getMtKalkulaceId(mtKalkulace.getId());
		entityManager.remove(u);
	}

	public List<Integer> getMtKalkulaceRoky() {
		log.trace("###\t\t getMtKalkulaceRoky();");
		List<Integer> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT cast(substring(mtk.gz39tKalkulace.kalkulace,1,4) as int) FROM MtKalkulace mtk GROUP BY substring(mtk.gz39tKalkulace.kalkulace,1,4) ORDER BY substring(mtk.gz39tKalkulace.kalkulace,1,4) DESC ",
							Integer.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<MtKalkulace> getMtKalkulace(String mt, String zavod) {
		log.trace("###\t\t getMtKalkulace(" + mt+"-"+zavod + ");");
		List<MtKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tMt.modelTr=:mt AND mtk.gz39tMt.zavod=:zavod ORDER BY mtk.gz39tKalkulace.kalkulace", MtKalkulace.class)
					.setParameter("mt", mt).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<MtKalkulace> getMtKalkulace(int kalkulace) {
		log.trace("###\t\t getMtKalkulace(" + kalkulace + ");");
		List<MtKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM MtKalkulace u WHERE u.gz39tKalkulace.kalkulace=:kalk ORDER BY u.gz39tMt.modelTr", MtKalkulace.class).setParameter("kalk", kalkulace)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<MtKalkulace> getMtKalkulaceFullPr(int kalkulace) {
		log.trace("###\t\t getMtKalkulaceFullPr(" + kalkulace + ");");
		// Vrati seznam objektu MtKalkulace, a to jen ty, kde jsou okomunikovany vsichni predstavitele (v PredstavitelKalkulace, tzn ma PR cisla v PredstavitelKalkulacePR)
		List<MtKalkulace> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT g FROM MtKalkulace g WHERE g.gz39tKalkulace.kalkulace=:kalk AND g.id NOT IN (SELECT a.gz39tMtKalkulace.id FROM PredstavitelKalkulace a WHERE a.gz39tMtKalkulace.gz39tKalkulace.kalkulace=:kalk AND a.existsPr IS NULL)",
							MtKalkulace.class).setParameter("kalk", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public MtKalkulace getMtKalkulace(int kalkulace, String mt, String zavod) {
		log.trace("###\t\t getMtKalkulace(" + kalkulace + ", " + mt + "-" + zavod + ");");
		MtKalkulace gre = null;
		try {
			gre = entityManager
					.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tKalkulace.kalkulace=:kalkulace AND mtk.gz39tMt.modelTr=:mt AND mtk.gz39tMt.zavod=:zavod ", MtKalkulace.class)
					.setParameter("kalkulace", kalkulace).setParameter("mt", mt).setParameter("zavod", zavod).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	// public MtKalkulace getMtKalkulace(int kalkulace, String mt) {
	// log.trace("###\t\t getMtKalkulace("+kalkulace+", "+mt+");");
	// MtKalkulace gre = null;
	// try {
	// gre = entityManager.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tKalkulace.kalkulace=:kalkulace AND mtk.gz39tMt.modelTr=:mt ",
	// MtKalkulace.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).getSingleResult();
	// } catch (NoResultException e) {
	// return null;
	// }
	// return gre;
	// }

	public List<Object[]> getIdKalkulacaAndIdMtForCreate() {
		// Spoji entity MT a KALKULACE pres platnost a vrati IDcka, ktere jeste nejsou v entity MtKalkulace vytvoreny,
		// aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a je treba zajistit vzdy spravny stav v entite MtKalkulace, kde jsou MT a Kalkulace
		// spojeny.
		log.trace("###\t\t getIdKalkulacaAndIdMtForCreate()");
		List<Object[]> gre;
		try {
			gre = entityManager
					.createQuery(
							"select k.id, m.id FROM Kalkulace k, Mt m WHERE m.platnostOd <= k.kalkulace AND m.platnostDo >= k.kalkulace AND m.id||k.id NOT IN (SELECT mk.gz39tMt.id || mk.gz39tKalkulace.id FROM MtKalkulace mk)",
							Object[].class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<MtKalkulace> getMtKalkulaceForDelete() {
		// Vrati vsechny objekty MtKalkulace, u ktere jiz nemaji existovat, a to z duvodu zmeny platnostiOd no DO u modelove tridy. Tyto objekty nasledne budou smazany.
		log.trace("###\t\t getMtKalkulaceForDelete()");
		List<MtKalkulace> gre;
		try {
			gre = entityManager.createQuery("select mtk FROM MtKalkulace mtk WHERE (mtk.gz39tMt.platnostOd > mtk.gz39tKalkulace.kalkulace OR mtk.gz39tMt.platnostDo < mtk.gz39tKalkulace.kalkulace)",
					MtKalkulace.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
