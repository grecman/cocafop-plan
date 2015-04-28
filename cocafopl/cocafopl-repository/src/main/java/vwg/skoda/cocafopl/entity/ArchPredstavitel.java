package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="GZ40T_PREDSTAVITEL", schema="COCAFOPPL_ARCH")
public class ArchPredstavitel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_PREDSTAVITEL_ID_GENERATOR", sequenceName="COCAFOPPL_ARCH.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_PREDSTAVITEL_ID_GENERATOR")
	private long id;

	@Column(name="CISLO_PRED")
	private int cisloPred;

	@Column(name="CISLO_PRED_MIN")
	private Integer cisloPredMin;

	private Boolean comix;

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

	//bi-directional many-to-one association to ArchKalkulace
	@ManyToOne
	@JoinColumn(name="KALKULACE")
	private ArchKalkulace gz40tKalkulace;

	//bi-directional many-to-one association to ArchPredstavitelPr
	@OneToMany(mappedBy="gz40tPredstavitel")
	private Set<ArchPredstavitelPr> gz40tPredstavitelPrs;

	public ArchPredstavitel() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCisloPred() {
		return cisloPred;
	}

	public Integer getCisloPredMin() {
		return cisloPredMin;
	}

	public int getModelovyRok() {
		return modelovyRok;
	}

	public int getPlatnostDo() {
		return platnostDo;
	}

	public int getPlatnostOd() {
		return platnostOd;
	}

	public void setCisloPred(int cisloPred) {
		this.cisloPred = cisloPred;
	}

	public void setCisloPredMin(Integer cisloPredMin) {
		this.cisloPredMin = cisloPredMin;
	}

	public void setModelovyRok(int modelovyRok) {
		this.modelovyRok = modelovyRok;
	}

	public void setPlatnostDo(int platnostDo) {
		this.platnostDo = platnostDo;
	}

	public void setPlatnostOd(int platnostOd) {
		this.platnostOd = platnostOd;
	}

	public Boolean getComix() {
		return this.comix;
	}

	public void setComix(Boolean comix) {
		this.comix = comix;
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


	public String getObsah() {
		return this.obsah;
	}

	public void setObsah(String obsah) {
		this.obsah = obsah;
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
		return this.gz40tKalkulace;
	}

	public void setGz40tKalkulace(ArchKalkulace gz40tKalkulace) {
		this.gz40tKalkulace = gz40tKalkulace;
	}

	public Set<ArchPredstavitelPr> getGz40tPredstavitelPrs() {
		return this.gz40tPredstavitelPrs;
	}

	public void setGz40tPredstavitelPrs(Set<ArchPredstavitelPr> gz40tPredstavitelPrs) {
		this.gz40tPredstavitelPrs = gz40tPredstavitelPrs;
	}

	public ArchPredstavitelPr addGz40tPredstavitelPr(ArchPredstavitelPr gz40tPredstavitelPr) {
		getGz40tPredstavitelPrs().add(gz40tPredstavitelPr);
		gz40tPredstavitelPr.setGz40tPredstavitel(this);

		return gz40tPredstavitelPr;
	}

	public ArchPredstavitelPr removeGz40tPredstavitelPr(ArchPredstavitelPr gz40tPredstavitelPr) {
		getGz40tPredstavitelPrs().remove(gz40tPredstavitelPr);
		gz40tPredstavitelPr.setGz40tPredstavitel(null);

		return gz40tPredstavitelPr;
	}

}