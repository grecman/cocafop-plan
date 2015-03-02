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

	public PredstavitelKalkulace getPredstavitelKalkulace(long id) {
		log.trace("###\t\t getPredstavitelKalkulace(" + id + ");");
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
		PredstavitelKalkulace u = getPredstavitelKalkulace(predKalk.getId());
		entityManager.remove(u);
	}

	public List<Object[]> getPredstaviteleKalkulaceKtereZatimNeexistuji(long idPredstavitele) {
		log.trace("###\t\t getPredstaviteleKalkulaceKtereZatimNeexistuji(" + idPredstavitele + ");");
		// metoda vrati seznam ID MtKalkulace a ID Predstavitele, pro ktere je nutne vyrvorit novy "radek" v entite PredstavitelKalkulace
		List<Object[]> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT mtk.id, p.id FROM MtKalkulace mtk, Predstavitel p WHERE p.id=:idPredstavitele AND mtk.gz39tMt.modelTr=p.gz39tMt.modelTr AND mtk.gz39tKalkulace.kalkulace >= p.platnostOd AND mtk.gz39tKalkulace.kalkulace <= p.platnostDo AND mtk.id||p.id NOT IN (SELECT pk.gz39tMtKalkulace.id||pk.gz39tPredstavitel.id FROM PredstavitelKalkulace pk)",
							Object[].class).setParameter("idPredstavitele", idPredstavitele).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		// for (Object[] result : gre) { System.out.println("ID mtk: "+result[0]); System.out.println("ID p  : "+result[1]); }
		return gre;
	}

	public List<PredstavitelKalkulace> getPredstaviteleKalkulaceKtereNemajiExistovat() {
		log.trace("###\t\t getPredstaviteleKalkulaceKtereNemajiExistovat()");
		// metoda vrati vsechny zaznamy z entity PredstavitelKalkulace, u ktery je "kalkulace" (z entity MtKalkulace potažmo Kalkulace) mimo platnost Predstavitele
		// toto je zapricineno, ze nekdo nejdrive vytvori Predstavitele platneho př: 2015/01-2015/12 (v te chvili se vytvori zaznamy v PredstavitekKalkulace) a nasledne snizi
		// platnost na 2015/01-2015/06 a proto by se meli zaznamy od 201507-201512 smazat
		List<PredstavitelKalkulace> gre = null;
		try {
			gre = entityManager
					.createQuery(
							"SELECT pk FROM PredstavitelKalkulace pk WHERE (pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace < pk.gz39tPredstavitel.platnostOd OR pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace > pk.gz39tPredstavitel.platnostDo)",
							PredstavitelKalkulace.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
