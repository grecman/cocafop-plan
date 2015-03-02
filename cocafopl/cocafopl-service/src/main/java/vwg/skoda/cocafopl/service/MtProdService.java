package vwg.skoda.cocafopl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.MtProd;


@Service
public class MtProdService {
	
	static Logger log = Logger.getLogger(MtProdService.class);
	
	@PersistenceContext(name = "MtProdService")
	private EntityManager entityManager;
	
	
	public MtProd getMtProd(String id) {
		log.trace("###\t\t getMtProd(" + id + ");");
		return entityManager.find(MtProd.class, id);
	}
	
	public Iterable<String> findMtByString(String string) {
		log.debug("\t### findMtProdByString("+string.toUpperCase()+")");
		Iterable<String> gre = entityManager
				.createQuery(
						"select u.modelovaTrida from MtProd u where u.modelovaTrida like :string AND u.modelovaTrida NOT IN (select b.modelTr FROM Mt b)",
						String.class).setParameter("string", "%"+ string.toUpperCase() + "%")
				.getResultList();
		log.debug("\t### findMtProdByString("+gre.toString()+")");
	return gre;
	}


}
