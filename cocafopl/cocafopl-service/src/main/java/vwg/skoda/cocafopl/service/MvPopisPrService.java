package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MvPopisPr;

@Service
public class MvPopisPrService {

	static Logger log = Logger.getLogger(MvPopisPrService.class);

	@PersistenceContext(name = "MvPopisPrService")
	private EntityManager entityManager;

	public MvPopisPr getMvPopisPrId(long id) {
		log.trace("###\t\t getMvPopisPrId(" + id + ");");
		return entityManager.find(MvPopisPr.class, id);
	}

	@Transactional
	public void addMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t addMvPopisPr(" + MvPopisPr.getPr() + ")");
		entityManager.persist(MvPopisPr);
	}

	@Transactional
	public void setMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t setMvPopisPr(" + MvPopisPr + ")");
		entityManager.merge(MvPopisPr);
	}

	@Transactional
	public void removeMvPopisPr(MvPopisPr MvPopisPr) {
		log.trace("###\t\t removeMvPopisPr(" + MvPopisPr + ")");
		MvPopisPr u = getMvPopisPrId(MvPopisPr.getId());
		entityManager.remove(u);
	}

	public List<MvPopisPr> getMvPopisPrAll() {
		log.trace("###\t\t getMvPopisPrAll();");
		return entityManager.createQuery("SELECT u FROM MvPopisPr u ", MvPopisPr.class).getResultList();
	}

	public List<MvPopisPr> getMvPopisPr(long idMvPopis) {
		log.trace("###\t\t getMvPopisPr(" + idMvPopis + ");");
		List<MvPopisPr> gre;
		try {
			gre = entityManager.createQuery("SELECT u FROM MvPopisPr u WHERE u.gz39tMvPopis.id=:idMvPopis ORDER BY u.pr ", MvPopisPr.class).setParameter("idMvPopis", idMvPopis).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public String getMvPopisPrNudle(long idMvPopis) {
		log.trace("###\t\t getMvPopisPrNudle(" + idMvPopis + ");");
		String gre;
		try {
			StringBuffer sql = new StringBuffer("SELECT '+'||LISTAGG(pr,'+') within group (order by pr) FROM cocafoppl.GZ39T_MV_POPIS_PR WHERE ID_MV_POPIS=" + idMvPopis);
			gre = (String) entityManager.createNativeQuery(sql.toString()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

	public List<String> getMvPopisPrRozdilneRodiny(String user) {
		log.trace("###\t\t getMvPopisPrRozdilneRodiny(" + user + ");");
		List<String> gre;
		try {
			gre = entityManager
					.createQuery(
							"SELECT z.rodina FROM MvPopisPr z, MvPopisPr v WHERE z.gz39tMvPopis.gz39tUser.netusername=:user AND z.gz39tMvPopis.typVozu='Z' AND v.gz39tMvPopis.gz39tUser.netusername=z.gz39tMvPopis.gz39tUser.netusername AND v.gz39tMvPopis.typVozu='V' AND z.rodina=v.rodina AND z.pr!=v.pr ",
							String.class).setParameter("user", user).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
