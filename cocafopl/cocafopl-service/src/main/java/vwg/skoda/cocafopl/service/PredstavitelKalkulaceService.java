package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;

@Service
public class PredstavitelKalkulaceService {

	static Logger log = Logger.getLogger(PredstavitelService.class);

	@PersistenceContext(name = "PredstavitelService")
	private EntityManager entityManager;

	public PredstavitelKalkulace getPredstavitelKalkulaceId(long id) {
		log.trace("###\t\t getPredstavitelKalkulaceId(" + id + ");");
		return entityManager.find(PredstavitelKalkulace.class, id);
	}

	@Transactional
	public void addPredstavitelKalkulace(PredstavitelKalkulace predKalk) {
		log.trace("###\t\t addPredstavitelKalkulace(" + predKalk + ")");
		entityManager.persist(predKalk);
	}

	@Transactional
	public void setPredstavitelKalkulace(PredstavitelKalkulace predKalk) {
		log.trace("###\t\t setPredstavitelKalkulace(" + predKalk + ")");
		entityManager.merge(predKalk);
	}

	@Transactional
	public void removePredstavitelKalkulace(PredstavitelKalkulace predKalk) {
		log.trace("###\t\t removePredstavitelKalkulace(" + predKalk + ")");
		PredstavitelKalkulace u = getPredstavitelKalkulaceId(predKalk.getId());
		entityManager.remove(u);
	}

	public List<PredstavitelKalkulace> getPredstaviteleKalkulace(String mt, int kalkulace) {
		log.trace("###\t\t getPredstaviteleKalkulace(" + mt + ", " + kalkulace + ");");
		List<PredstavitelKalkulace> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"select pk FROM PredstavitelKalkulace pk WHERE pk.gz39tMtKalkulace.gz39tMt.modelTr=:mt AND pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace=:kalkulace ORDER BY pk.gz39tPredstavitel.cisloPred ",
							PredstavitelKalkulace.class).setParameter("mt", mt).setParameter("kalkulace", kalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<PredstavitelKalkulace> getPredstaviteleKalkulace(int cisloPred) {
		log.trace("###\t\t getPredstaviteleKalkulace(" + cisloPred + ");");
		List<PredstavitelKalkulace> gre = null;
		try {
			gre = entityManager
					.createQuery("select pk FROM PredstavitelKalkulace pk WHERE pk.gz39tPredstavitel.cisloPred=:cisloPred ORDER BY pk.gz39tPredstavitel.cisloPred ", PredstavitelKalkulace.class)
					.setParameter("cisloPred", cisloPred).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<Object[]> getPredstavitelKalkulaceKtereZatimNeexistuji() {
		log.trace("###\t\t getPredstaviteleKalkulaceKtereZatimNeexistuji();");
		// metoda vrati seznam ID MtKalkulace a ID Predstavitele, pro ktere je nutne vyrvorit novy "radek" v entite PredstavitelKalkulace
		List<Object[]> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT mtk.id, p.id FROM MtKalkulace mtk, Predstavitel p WHERE mtk.gz39tMt.modelTr=p.gz39tMt.modelTr AND mtk.gz39tKalkulace.kalkulace >= p.platnostOd AND mtk.gz39tKalkulace.kalkulace <= p.platnostDo AND mtk.id||p.id NOT IN (SELECT pk.gz39tMtKalkulace.id||pk.gz39tPredstavitel.id FROM PredstavitelKalkulace pk)",
							Object[].class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		// for (Object[] result : gre) { System.out.println("ID mtk: "+result[0]); System.out.println("ID p  : "+result[1]); }
		return gre;
	}

	public List<PredstavitelKalkulace> getPredstaviteleKalkulaceKtereNemajiExistovat(long idPredstavitele) {
		log.trace("###\t\t getPredstaviteleKalkulaceKtereNemajiExistovat(" + idPredstavitele + ");");
		// metoda vrati vsechny zaznamy z entity PredstavitelKalkulace, u kterych je "kalkulace" (z entity MtKalkulace potažmo Kalkulace) mimo platnost Predstavitele
		// toto je zapricineno, ze nekdo nejdrive vytvori Predstavitele platneho př: 2015/01-2015/12 (v te chvili se vytvori zaznamy v PredstavitekKalkulace) a nasledne snizi
		// platnost Predstavitele na 2015/01-2015/06 a proto by se meli zaznamy v PredstavitekKalkulace od 201507-201512 smazat.
		List<PredstavitelKalkulace> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT pk FROM PredstavitelKalkulace pk WHERE pk.gz39tPredstavitel.id=:idPredstavitele AND (pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace < pk.gz39tPredstavitel.platnostOd OR pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace > pk.gz39tPredstavitel.platnostDo)",
							PredstavitelKalkulace.class).setParameter("idPredstavitele", idPredstavitele).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
