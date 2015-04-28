package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GZ40T_KUSOVNIK database table.
 * 
 */
@Entity
@Table(name="GZ40T_KUSOVNIK", schema="COCAFOPPL_ARCH")
public class ArchKusovnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="GZ40T_KUSOVNIK_ID_GENERATOR", sequenceName="COCAFOPPL_ARCH.HIBERNATE_SEQUENCE")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KUSOVNIK_ID_GENERATOR")
	private long id;

	@Column(name="BEN_CZ")
	private String benCz;

	@Column(name="BEN_D")
	private String benD;

	@Column(name="BEN_US")
	private String benUs;

	private String bza;

	private String cdilu;

	private String cizav;

	private String einschl;

	private String entschl;

	private String genev;

	private BigDecimal lfdnr;

	private String local;

	private String me;

	private BigDecimal mnpoz;

	private String produkt;

	private String prpod;

	private String serdo;

	private String serod;

	private String skoda;

	private String vw;

	private String zavod;

	//bi-directional many-to-one association to ArchKalkulace
	@ManyToOne
	@JoinColumn(name="KALKULACE")
	private ArchKalkulace gz40tKalkulace;

	public ArchKusovnik() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBenCz() {
		return this.benCz;
	}

	public void setBenCz(String benCz) {
		this.benCz = benCz;
	}

	public String getBenD() {
		return this.benD;
	}

	public void setBenD(String benD) {
		this.benD = benD;
	}

	public String getBenUs() {
		return this.benUs;
	}

	public void setBenUs(String benUs) {
		this.benUs = benUs;
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

	public String getCizav() {
		return this.cizav;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public String getEinschl() {
		return this.einschl;
	}

	public void setEinschl(String einschl) {
		this.einschl = einschl;
	}

	public String getEntschl() {
		return this.entschl;
	}

	public void setEntschl(String entschl) {
		this.entschl = entschl;
	}

	public String getGenev() {
		return this.genev;
	}

	public void setGenev(String genev) {
		this.genev = genev;
	}

	public BigDecimal getLfdnr() {
		return this.lfdnr;
	}

	public void setLfdnr(BigDecimal lfdnr) {
		this.lfdnr = lfdnr;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getMe() {
		return this.me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public BigDecimal getMnpoz() {
		return this.mnpoz;
	}

	public void setMnpoz(BigDecimal mnpoz) {
		this.mnpoz = mnpoz;
	}

	public String getProdukt() {
		return this.produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public String getPrpod() {
		return this.prpod;
	}

	public void setPrpod(String prpod) {
		this.prpod = prpod;
	}

	public String getSerdo() {
		return this.serdo;
	}

	public void setSerdo(String serdo) {
		this.serdo = serdo;
	}

	public String getSerod() {
		return this.serod;
	}

	public void setSerod(String serod) {
		this.serod = serod;
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

	public ArchKalkulace getGz40tKalkulace() {
		return this.gz40tKalkulace;
	}

	public void setGz40tKalkulace(ArchKalkulace gz40tKalkulace) {
		this.gz40tKalkulace = gz40tKalkulace;
	}

}