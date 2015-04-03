package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_KUSY_PROV", schema="COCAFOPPL_ARCH")
public class ArchKusyProv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_KUSY_PROV_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KUSY_PROV_ID_GENERATOR")
	private long id;

	private String bza;

	private String cdilu;

	@Column(name="CISLO_PRED")
	private int cisloPred;

	private String cizav;

	private int kalkulace;

	private String local;

	private int mnfin;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String skoda;

	private String vw;

	private String zavod;
	
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL")
	private ArchPredstavitel archPredstavitel;


	public ArchKusyProv() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBza() {
		return this.bza;
	}

	public void setBza(String bza) {
		this.bza = bza;
	}

	public String getCdilu() {
		return this.cdilu;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
	}

	public int getCisloPred() {
		return this.cisloPred;
	}

	public void setCisloPred(int cisloPred) {
		this.cisloPred = cisloPred;
	}

	public String getCizav() {
		return this.cizav;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public int getKalkulace() {
		return this.kalkulace;
	}

	public void setKalkulace(int kalkulace) {
		this.kalkulace = kalkulace;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getMnfin() {
		return this.mnfin;
	}

	public void setMnfin(int mnfin) {
		this.mnfin = mnfin;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getSkoda() {
		return this.skoda;
	}

	public void setSkoda(String skoda) {
		this.skoda = skoda;
	}

	public String getVw() {
		return this.vw;
	}

	public void setVw(String vw) {
		this.vw = vw;
	}

	public String getZavod() {
		return this.zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

}