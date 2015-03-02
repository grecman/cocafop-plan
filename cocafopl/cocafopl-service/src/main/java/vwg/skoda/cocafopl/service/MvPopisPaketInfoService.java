package vwg.skoda.cocafopl.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vwg.skoda.cocafopl.entity.MvPopisPaketInfo;

@Service
public class MvPopisPaketInfoService {

	static Logger log = Logger.getLogger(MvPopisPaketInfoService.class);
	
	@PersistenceContext(name = "MvPopisPaketInfoService")
	private EntityManager entityManager;
	
	public MvPopisPaketInfo getMvPopisPaketInfo(long id) {
		log.trace("###\t\t getMvPopisPaketInfo(" + id + ");");
		return entityManager.find(MvPopisPaketInfo.class, id);
	}

	@Transactional
	public void addMvPopisPaketInfo(MvPopisPaketInfo MvPopisPaketInfo) {
		log.trace("###\t\t addMvPopisPaketInfo(" + MvPopisPaketInfo + ")");
		entityManager.persist(MvPopisPaketInfo);
	}

	@Transactional
	public void setMvPopisPaketInfo(MvPopisPaketInfo MvPopisPaketInfo) {
		log.trace("###\t\t setMvPopisPaketInfo(" + MvPopisPaketInfo + ")");
		entityManager.merge(MvPopisPaketInfo);
	}

	@Transactional
	public void removeMvPopisPaketInfo(MvPopisPaketInfo MvPopisPaketInfo) {
		log.trace("###\t\t removeMvPopisPaketInfo(" + MvPopisPaketInfo + ")");
		MvPopisPaketInfo u = getMvPopisPaketInfo(MvPopisPaketInfo.getId());
		entityManager.remove(u);
	}

	public List<MvPopisPaketInfo> getMvPopisPaketInfoAll() {
		log.trace("###\t\t getMvPopisPaketInfoAll();");
		return entityManager.createQuery("SELECT u FROM MvPopisPaketInfo u ", MvPopisPaketInfo.class).getResultList();
	}
	
}
