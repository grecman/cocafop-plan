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

	public MtKalkulace getMtKalkulaceOne(long id) {
		log.trace("###\t\t getMtMtKalkulaceOne(" + id + ");");
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
		MtKalkulace u = getMtKalkulaceOne(mtKalkulace.getId());
		entityManager.remove(u);
	}
	
	public List<Integer> getMtKalkulaceRoky() {
		log.trace("###\t\t getMtKalkulaceRoky();");
		List<Integer> gre = null;
		try {
			gre = entityManager.createQuery("SELECT cast(substring(mtk.gz39tKalkulace.kalkulace,1,4) as int) FROM MtKalkulace mtk GROUP BY substring(mtk.gz39tKalkulace.kalkulace,1,4) ORDER BY substring(mtk.gz39tKalkulace.kalkulace,1,4) DESC ", Integer.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<String> getMtKalkulaceMtZavod(String rok) {
		log.trace("###\t\t getMtKalkulaceMtZavod("+rok+");");
		List<String> gre = null;
		try {
			gre = entityManager.createQuery("SELECT mtk.gz39tMt.modelTr||'-'||mtk.gz39tMt.zavod FROM MtKalkulace mtk WHERE substring(mtk.gz39tKalkulace.kalkulace,1,4)=:rok GROUP BY mtk.gz39tMt.modelTr,mtk.gz39tMt.zavod ORDER BY mtk.gz39tMt.modelTr ", String.class).setParameter("rok", rok).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	
	public List<MtKalkulace> getMtKalkulace(String mt) {
		log.trace("###\t\t getMtKalkulace("+mt+");");
		List<MtKalkulace>  gre = null;
		try {
			gre = entityManager.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tMt.modelTr=:mt ORDER BY mtk.gz39tKalkulace.kalkulace", MtKalkulace.class).setParameter("mt", mt).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<MtKalkulace> getMtKalkulace(int kalkulace) {
		log.trace("###\t\t getMtKalkulace("+kalkulace+");");
		List<MtKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM MtKalkulace u WHERE u.gz39tKalkulace.kalkulace=:kalk ORDER BY u.gz39tMt.modelTr", MtKalkulace.class).setParameter("kalk", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public MtKalkulace getMtKalkulace(int kalkulace, String mt) {
		log.trace("###\t\t getMtKalkulace("+kalkulace+", "+mt+");");
		MtKalkulace gre = null;
		try {
			gre = entityManager.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tKalkulace.kalkulace=:kalkulace AND mtk.gz39tMt.modelTr=:mt ", MtKalkulace.class).setParameter("kalkulace", kalkulace).setParameter("mt", mt).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<String> getIdKalkulacaAndIdMtForCreate_() {
		// Spoji entity MT a KALKULACE (jen neschvalene!) pres platnost a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, 
		// aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a je treba zajistit vzdy spravny stav v entite MtKalkulace, kde jsou MT a Kalkulace spojeny.
		log.trace("###\t\t getIdKalkulacaAndIdMtForCreate_()");
		List<String> gre;
		try {
			gre = entityManager
					.createQuery(
							"select k.id||';'||m.id FROM Kalkulace k, Mt m WHERE k.schvaleno IS NULL AND m.platnostOd <= k.kalkulace AND m.platnostDo >= k.kalkulace AND m.id||k.id NOT IN (SELECT mk.gz39tMt.id || mk.gz39tKalkulace.id FROM MtKalkulace mk)",
							String.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<Object[]> getIdKalkulacaAndIdMtForCreate() {
		// Spoji entity MT a KALKULACE (jen neschvalene!) pres platnost a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, 
		// aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a je treba zajistit vzdy spravny stav v entite MtKalkulace, kde jsou MT a Kalkulace spojeny.
		log.trace("###\t\t getIdKalkulacaAndIdMtForCreate()");
		List<Object[]> gre;
		try {
			gre = entityManager
					.createQuery(
							"select k.id, m.id FROM Kalkulace k, Mt m WHERE k.schvaleno IS NULL AND m.platnostOd <= k.kalkulace AND m.platnostDo >= k.kalkulace AND m.id||k.id NOT IN (SELECT mk.gz39tMt.id || mk.gz39tKalkulace.id FROM MtKalkulace mk)",
							Object[].class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public List<MtKalkulace> getMtKalkulaceForDelete() {
		// Vrati vsechny objekty MtKalkulace, u ktere jiz nemaji existovat, a to z duvodu zkraceni platnostiDo u modelove tridy
		log.trace("###\t\t getMtKalkulaceForDelete()");
		List<MtKalkulace> gre;
		try {
			gre = entityManager
					.createQuery(
							"select mtk FROM MtKalkulace mtk WHERE mtk.gz39tKalkulace.schvaleno IS NULL AND mtk.gz39tMt.platnostDo < mtk.gz39tKalkulace.kalkulace",
							MtKalkulace.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}


}
