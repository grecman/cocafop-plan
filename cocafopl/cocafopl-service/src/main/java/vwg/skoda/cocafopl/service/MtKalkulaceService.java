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

	public MtKalkulace getMtKalkulace(long id) {
		log.trace("###\t\t getMtMtKalkulace(" + id + ");");
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
		MtKalkulace u = getMtKalkulace(mtKalkulace.getId());
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

	public List<MtKalkulace> getMtKalkulace(int kalkulace) {
		log.trace("###\t\t getMtKalkulace("+kalkulace+");");
		List<MtKalkulace> gre = null;
		try {
			gre = entityManager.createQuery("SELECT u FROM MtKalkulace u WHERE u.gz39tKalkulace.kalkulace=:kalk", MtKalkulace.class).setParameter("kalk", kalkulace).getResultList();
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
	
	public List<MtKalkulace> getMtKalkulace(String mt) {
		log.trace("###\t\t getMtKalkulace("+mt+");");
		List<MtKalkulace>  gre = null;
		try {
			gre = entityManager.createQuery("SELECT mtk FROM MtKalkulace mtk WHERE mtk.gz39tMt.modelTr=:mt ", MtKalkulace.class).setParameter("mt", mt).getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return gre;
	}

}
