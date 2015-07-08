package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.PredstavitelPr;

@Service
public class PredstavitelPrService {
	
	static Logger log = Logger.getLogger(PredstavitelPrService.class);
	
	@PersistenceContext(name = "PredstavitelPrService")
	private EntityManager entityManager;
	
	
	public PredstavitelPr getPredstavitelPrId(long id) {
		log.trace("###\t\t getPredstavitelPr(" + id + ");");
		return entityManager.find(PredstavitelPr.class, id);
	}
	
	@Transactional
	public void addPredstavitelPr(PredstavitelPr predstavitelPr) {
		log.trace("###\t\t addPredstavitelPr(" + predstavitelPr + ")");
		entityManager.persist(predstavitelPr);
	}

	@Transactional
	public void setPredstavitelPr(PredstavitelPr predstavitelPr) {
		log.trace("###\t\t setPredstavitelPr(" + predstavitelPr + ")");
		entityManager.merge(predstavitelPr);
	}

	@Transactional
	public void removePredstavitelPr(PredstavitelPr predstavitelPr) {
		log.trace("###\t\t removePredstavitelPr(" + predstavitelPr + ")");
		PredstavitelPr u = getPredstavitelPrId(predstavitelPr.getId());
		entityManager.remove(u);
	}
	
	@Transactional
	public void removePredstavitelPrAll(long idPredstavitelKalkulace) {
		log.trace("###\t\t removePredstavitelPrAll(" + idPredstavitelKalkulace + ")");
		entityManager.createQuery("DELETE FROM PredstavitelPr a WHERE a.gz39tPredstavitelKalkulace.id = :idPk ").setParameter("idPk", idPredstavitelKalkulace).executeUpdate();
	}
	
	public List<PredstavitelPr> getPredstavitelPr(long idPredstavitelKalkulace) {
		log.trace("###\t\t getPredstavitelPr(" + idPredstavitelKalkulace+");");
		List<PredstavitelPr> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelPr u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace ORDER BY u.pr ", PredstavitelPr.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public String getPredstavitelPrNudle(long idPredstavitelKalkulace) {
		log.trace("###\t\t getPredstavitelPrNudle(" + idPredstavitelKalkulace+");");
		String gre;
		try {
			StringBuffer sql = new StringBuffer("SELECT '+'||LISTAGG(decode(pr_editovane,null,pr,pr_editovane),'+') within group (order by pr) FROM cocafoppl.GZ39T_PREDSTAVITEL_PR WHERE id_predstavitel_kalkulace="+idPredstavitelKalkulace);
			gre =  (String) entityManager.createNativeQuery(sql.toString()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public PredstavitelPr getPredstavitelPr(long idPredstavitelKalkulace, String pr) {
		log.trace("###\t\t getPredstavitelPr(" + idPredstavitelKalkulace+", "+pr+");");
		PredstavitelPr gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelPr u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace AND u.pr=:pr ", PredstavitelPr.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).setParameter("pr", pr).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}
	
	public PredstavitelPr getPredstavitelPrDleRodiny(long idPredstavitelKalkulace, String rodina) {
		log.trace("###\t\t getPredstavitelPr(" + idPredstavitelKalkulace+", "+rodina+");");
		PredstavitelPr gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM PredstavitelPr u WHERE u.gz39tPredstavitelKalkulace.id=:idPredstavitelKalkulace AND u.rodina=:rodina ", PredstavitelPr.class).setParameter("idPredstavitelKalkulace", idPredstavitelKalkulace).setParameter("rodina", rodina).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
