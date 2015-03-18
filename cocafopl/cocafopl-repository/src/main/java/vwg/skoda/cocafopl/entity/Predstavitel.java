package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GZ39T_PREDSTAVITEL database table.
 * 
 */
@Entity
@Table(name="GZ39T_PREDSTAVITEL", schema="COCAFOPPL")
public class Predstavitel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_PREDSTAVITEL_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_PREDSTAVITEL_ID_GENERATOR")
	private long id;

	private Integer cetnost;

	@Column(name="CISLO_PRED")
	private Integer cisloPred;

	@Column(name="CISLO_PRED_MIN")
	private Integer cisloPredMin;

	private Boolean comix;

	@Column(name="EU_NORMA")
	private String euNorma;

	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODELOVY_KLIC")
	private String modelovyKlic;

//	@Column(name="MODELOVY_ROK")
//	private Integer modelovyRok;

	private String obsah;

	@Column(name="PLATNOST_DO")
	private Integer platnostDo;

	@Column(name="PLATNOST_OD")
	private Integer platnostOd;

	private String poznamka;

	private Integer rok;

	private String rozlozenost;

	private String typ;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	private String vybava;

	private String vybavy;

	private String vykon;

	//bi-directional many-to-one association to PredstavitelKalkulace
	@OneToMany(mappedBy="gz39tPredstavitel")
	private List<PredstavitelKalkulace> gz39tPredstavitelKalkulaces;
	
	//bi-directional many-to-one association to Mt
	@ManyToOne
	@JoinColumn(name="ID_MT")
	private Mt gz39tMt;

	public Predstavitel() {
	}

		
	public Mt getGz39tMt() {
		return gz39tMt;
	}

	public void setGz39tMt(Mt gz39tMt) {
		this.gz39tMt = gz39tMt;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getCetnost() {
		return this.cetnost;
	}

	public void setCetnost(Integer cetnost) {
		this.cetnost = cetnost;
	}

	public Integer getCisloPred() {
		return this.cisloPred;
	}

	public void setCisloPred(Integer cisloPred) {
		this.cisloPred = cisloPred;
	}

	public Integer getCisloPredMin() {
		return this.cisloPredMin;
	}

	public void setCisloPredMin(Integer cisloPredMin) {
		this.cisloPredMin = cisloPredMin;
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

	public String getModelovyKlic() {
		return this.modelovyKlic;
	}

	public void setModelovyKlic(String modelovyKlic) {
		this.modelovyKlic = modelovyKlic;
	}

//	public Integer getModelovyRok() {
//		return this.modelovyRok;
//	}

//	public void setModelovyRok(Integer modelovyRok) {
//		this.modelovyRok = modelovyRok;
//	}

	public String getObsah() {
		return this.obsah;
	}

	public void setObsah(String obsah) {
		this.obsah = obsah;
	}

	public Integer getPlatnostDo() {
		return this.platnostDo;
	}

	public void setPlatnostDo(Integer platnostDo) {
		this.platnostDo = platnostDo;
	}

	public Integer getPlatnostOd() {
		return this.platnostOd;
	}

	public void setPlatnostOd(Integer platnostOd) {
		this.platnostOd = platnostOd;
	}

	public String getPoznamka() {
		return this.poznamka;
	}

	public void setPoznamka(String poznamka) {
		this.poznamka = poznamka;
	}

	public Integer getRok() {
		return this.rok;
	}

	public void setRok(Integer rok) {
		this.rok = rok;
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

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
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

	public List<PredstavitelKalkulace> getGz39tPredstavitelKalkulaces() {
		return this.gz39tPredstavitelKalkulaces;
	}

	public void setGz39tPredstavitelKalkulaces(List<PredstavitelKalkulace> gz39tPredstavitelKalkulaces) {
		this.gz39tPredstavitelKalkulaces = gz39tPredstavitelKalkulaces;
	}

	public PredstavitelKalkulace addGz39tPredstavitelKalkulace(PredstavitelKalkulace gz39tPredstavitelKalkulace) {
		getGz39tPredstavitelKalkulaces().add(gz39tPredstavitelKalkulace);
		gz39tPredstavitelKalkulace.setGz39tPredstavitel(this);

		return gz39tPredstavitelKalkulace;
	}

	public PredstavitelKalkulace removeGz39tPredstavitelKalkulace(PredstavitelKalkulace gz39tPredstavitelKalkulace) {
		getGz39tPredstavitelKalkulaces().remove(gz39tPredstavitelKalkulace);
		gz39tPredstavitelKalkulace.setGz39tPredstavitel(null);

		return gz39tPredstavitelKalkulace;
	}

}