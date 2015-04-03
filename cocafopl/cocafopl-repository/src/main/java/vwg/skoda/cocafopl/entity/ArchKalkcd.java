package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_KALKCD", schema="COCAFOPPL_ARCH")
public class ArchKalkcd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_KALKCD_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KALKCD_ID_GENERATOR")
	private long id;

	private String cdilu;

	@Column(name="CISLO_PRED")
	private int cisloPred;

	private String cizav;

	private int kalkulace;

	private int kalvar;

	private float matiac;

	private float matiace;

	private float matibc;

	private float matibce;

	private float matsac;

	private float matsace;

	private float matsbc;

	private float matsbce;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String prpoz;

	private int terka;

	private String verze;

	private String zavod;
	
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL")
	private ArchPredstavitel archPredstavitel;

	public ArchKalkcd() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getKalvar() {
		return this.kalvar;
	}

	public void setKalvar(int kalvar) {
		this.kalvar = kalvar;
	}

	public float getMatiac() {
		return this.matiac;
	}

	public void setMatiac(float matiac) {
		this.matiac = matiac;
	}

	public float getMatiace() {
		return this.matiace;
	}

	public void setMatiace(float matiace) {
		this.matiace = matiace;
	}

	public float getMatibc() {
		return this.matibc;
	}

	public void setMatibc(float matibc) {
		this.matibc = matibc;
	}

	public float getMatibce() {
		return this.matibce;
	}

	public void setMatibce(float matibce) {
		this.matibce = matibce;
	}

	public float getMatsac() {
		return this.matsac;
	}

	public void setMatsac(float matsac) {
		this.matsac = matsac;
	}

	public float getMatsace() {
		return this.matsace;
	}

	public void setMatsace(float matsace) {
		this.matsace = matsace;
	}

	public float getMatsbc() {
		return this.matsbc;
	}

	public void setMatsbc(float matsbc) {
		this.matsbc = matsbc;
	}

	public float getMatsbce() {
		return this.matsbce;
	}

	public void setMatsbce(float matsbce) {
		this.matsbce = matsbce;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getPrpoz() {
		return this.prpoz;
	}

	public void setPrpoz(String prpoz) {
		this.prpoz = prpoz;
	}

	public int getTerka() {
		return this.terka;
	}

	public void setTerka(int terka) {
		this.terka = terka;
	}

	public String getVerze() {
		return this.verze;
	}

	public void setVerze(String verze) {
		this.verze = verze;
	}

	public String getZavod() {
		return this.zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

}