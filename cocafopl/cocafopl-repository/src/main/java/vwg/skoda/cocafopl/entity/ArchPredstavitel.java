package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="GZ40T_PREDSTAVITEL", schema="COCAFOPPL_ARCH")
public class ArchPredstavitel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_PREDSTAVITEL_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_PREDSTAVITEL_ID_GENERATOR")
	private long id;

	@Column(name="CISLO_PRED")
	private int cisloPred;

	@Column(name="CISLO_PRED_MIN")
	private int cisloPredMin;

	@Column(name="EU_NORMA")
	private String euNorma;

	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODEL_TR")
	private String modelTr;

	@Column(name="MODELOVY_KLIC")
	private String modelovyKlic;

	@Column(name="MODELOVY_ROK")
	private int modelovyRok;

	private String obsah;

	@Column(name="PLATNOST_DO")
	private int platnostDo;

	@Column(name="PLATNOST_OD")
	private int platnostOd;

	private String poznamka;

	private String produkt;

	private String prpoz;

	private String rozlozenost;

	private String typ;

	private String vybava;

	private String vybavy;

	private String vykon;

	private String zavod;

	@ManyToOne
	@JoinColumn(name="kalkulace")
	private ArchKalkulace archKalkulace;
	
	@OneToMany(mappedBy="archPredstavitel")
	private Set<ArchPredstavitelPr> archPredstavitelPrs;
	
	@OneToMany(mappedBy="archPredstavitel")
	private Set<ArchKalkcd> archKalkcds;
	
	@OneToMany(mappedBy="archPredstavitel")
	private Set<ArchKusyProv> archKusyProvs;

	public ArchPredstavitel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCisloPred() {
		return this.cisloPred;
	}

	public void setCisloPred(int cisloPred) {
		this.cisloPred = cisloPred;
	}

	public int getCisloPredMin() {
		return this.cisloPredMin;
	}

	public void setCisloPredMin(int cisloPredMin) {
		this.cisloPredMin = cisloPredMin;
	}

	public String getEuNorma() {
		return this.euNorma;
	}

	public void setEuNorma(String euNorma) {
		this.euNorma = euNorma;
	}

	

	public String getKodZeme() {
		return this.kodZeme;
	}

	public void setKodZeme(String kodZeme) {
		this.kodZeme = kodZeme;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getModelovyKlic() {
		return this.modelovyKlic;
	}

	public void setModelovyKlic(String modelovyKlic) {
		this.modelovyKlic = modelovyKlic;
	}

	public int getModelovyRok() {
		return this.modelovyRok;
	}

	public void setModelovyRok(int modelovyRok) {
		this.modelovyRok = modelovyRok;
	}

	public String getObsah() {
		return this.obsah;
	}

	public void setObsah(String obsah) {
		this.obsah = obsah;
	}

	public int getPlatnostDo() {
		return this.platnostDo;
	}

	public void setPlatnostDo(int platnostDo) {
		this.platnostDo = platnostDo;
	}

	public int getPlatnostOd() {
		return this.platnostOd;
	}

	public void setPlatnostOd(int platnostOd) {
		this.platnostOd = platnostOd;
	}

	public String getPoznamka() {
		return this.poznamka;
	}

	public void setPoznamka(String poznamka) {
		this.poznamka = poznamka;
	}

	public String getProdukt() {
		return this.produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public String getPrpoz() {
		return this.prpoz;
	}

	public void setPrpoz(String prpoz) {
		this.prpoz = prpoz;
	}

	public String getRozlozenost() {
		return this.rozlozenost;
	}

	public void setRozlozenost(String rozlozenost) {
		this.rozlozenost = rozlozenost;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getVybava() {
		return this.vybava;
	}

	public void setVybava(String vybava) {
		this.vybava = vybava;
	}

	public String getVybavy() {
		return this.vybavy;
	}

	public void setVybavy(String vybavy) {
		this.vybavy = vybavy;
	}

	public String getVykon() {
		return this.vykon;
	}

	public void setVykon(String vykon) {
		this.vykon = vykon;
	}

	public String getZavod() {
		return this.zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

	public ArchKalkulace getGz40tKalkulace() {
		return archKalkulace;
	}

	public Set<ArchPredstavitelPr> getGz40tPredstavitelPrs() {
		return archPredstavitelPrs;
	}

	public void setGz40tKalkulace(ArchKalkulace archKalkulace) {
		this.archKalkulace = archKalkulace;
	}

	public void setGz40tPredstavitelPrs(Set<ArchPredstavitelPr> archPredstavitelPrs) {
		this.archPredstavitelPrs = archPredstavitelPrs;
	}
}
