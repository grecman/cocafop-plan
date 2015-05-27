package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GZ40V_KUSY_PROV", schema="COCAFOPPL_ARCH")
public class ArchKusyProvView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	@Column(name="BEN_CZ")
	private String benCz;

	private String bza;

	private String cdilu;

	@Column(name="CISLO_PRED")
	private Integer cisloPred;

	private String cizav;

	private Integer kalkulace;

	private String local;

	private String me;

	private Integer mnfin;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String produkt;

	private String skoda;

	private String vw;

	private String zavod;

	public ArchKusyProvView() {
	}

	public long getId() {
		return id;
	}

	public String getBenCz() {
		return benCz;
	}

	public String getBza() {
		return bza;
	}

	public String getCdilu() {
		return cdilu;
	}

	public Integer getCisloPred() {
		return cisloPred;
	}

	public String getCizav() {
		return cizav;
	}

	public Integer getKalkulace() {
		return kalkulace;
	}

	public String getLocal() {
		return local;
	}

	public String getMe() {
		return me;
	}

	public Integer getMnfin() {
		return mnfin;
	}

	public String getModelTr() {
		return modelTr;
	}

	public String getProdukt() {
		return produkt;
	}

	public String getSkoda() {
		return skoda;
	}

	public String getVw() {
		return vw;
	}

	public String getZavod() {
		return zavod;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setBenCz(String benCz) {
		this.benCz = benCz;
	}

	public void setBza(String bza) {
		this.bza = bza;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
	}

	public void setCisloPred(Integer cisloPred) {
		this.cisloPred = cisloPred;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public void setKalkulace(Integer kalkulace) {
		this.kalkulace = kalkulace;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public void setMnfin(Integer mnfin) {
		this.mnfin = mnfin;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public void setSkoda(String skoda) {
		this.skoda = skoda;
	}

	public void setVw(String vw) {
		this.vw = vw;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}
	
	
}