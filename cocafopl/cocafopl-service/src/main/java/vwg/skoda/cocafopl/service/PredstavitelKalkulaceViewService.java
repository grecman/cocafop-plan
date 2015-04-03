package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.PredstavitelKalkulaceView;

@Service
public class PredstavitelKalkulaceViewService {

	private static Logger log = Logger.getLogger(KalkulaceMtZavodService.class);

	@PersistenceContext(name = "PredstavitelKalkulaceView")
	private EntityManager entityManager;

	public PredstavitelKalkulaceView getPredstavitelKalkulaceView(long idPk) {
		log.trace("###\t\t getPredstavitelKalkulaceView(" + idPk + ");");
		return entityManager.createQuery("SELECT u FROM PredstavitelKalkulaceView u WHERE u.idPk=:idPk ", PredstavitelKalkulaceView.class).setParameter("idPk", idPk).getSingleResult();
	}

	public List<PredstavitelKalkulaceView> getPredstavitelKalkulaceView(int kalkulace, String modelTr, String zavod) {
		log.trace("###\t\t getPredstavitelKalkulaceView(" + kalkulace + ", " + modelTr + "-" + zavod + ");");
		List<PredstavitelKalkulaceView> gre;
		try {
			gre = entityManager
					.createQuery("SELECT u FROM PredstavitelKalkulaceView u WHERE u.kalkulace=:kalkulace AND u.modelTr=:modelTr AND u.zavod=:zavod ORDER by u.cisloPred ",
							PredstavitelKalkulaceView.class).setParameter("kalkulace", kalkulace).setParameter("modelTr", modelTr).setParameter("zavod", zavod).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
}
