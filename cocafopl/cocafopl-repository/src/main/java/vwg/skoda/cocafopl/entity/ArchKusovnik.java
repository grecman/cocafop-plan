package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_KUSOVNIK", schema="COCAFOPPL_ARCH")
public class ArchKusovnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_KUSOVNIK_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KUSOVNIK_ID_GENERATOR")
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

	private long lfdnr;

	private String local;

	private String me;

	private float mnpoz;

	private String produkt;

	private String prpod;

	private String serdo;

	private String serod;

	private String skoda;

	private String vw;

	private String zavod;
	
	@ManyToOne
	@JoinColumn(name="kalkulace")
	private ArchKalkulace archKalkulace;

	public ArchKusovnik() {
	}

	public long getId() {
		return id;
	}

	public String getBenCz() {
		return benCz;
	}

	public String getBenD() {
		return benD;
	}

	public String getBenUs() {
		return benUs;
	}

	public String getBza() {
		return bza;
	}

	public String getCdilu() {
		return cdilu;
	}

	public String getCizav() {
		return cizav;
	}

	public String getEinschl() {
		return einschl;
	}

	public String getEntschl() {
		return entschl;
	}

	public long getLfdnr() {
		return lfdnr;
	}

	public String getLocal() {
		return local;
	}

	public String getMe() {
		return me;
	}

	public float getMnpoz() {
		return mnpoz;
	}

	public String getProdukt() {
		return produkt;
	}

	public String getPrpod() {
		return prpod;
	}

	public String getSerdo() {
		return serdo;
	}

	public String getSerod() {
		return serod;
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

	public void setBenD(String benD) {
		this.benD = benD;
	}

	public void setBenUs(String benUs) {
		this.benUs = benUs;
	}

	public void setBza(String bza) {
		this.bza = bza;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public void setEinschl(String einschl) {
		this.einschl = einschl;
	}

	public void setEntschl(String entschl) {
		this.entschl = entschl;
	}

	public void setLfdnr(long lfdnr) {
		this.lfdnr = lfdnr;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public void setMnpoz(float mnpoz) {
		this.mnpoz = mnpoz;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public void setPrpod(String prpod) {
		this.prpod = prpod;
	}

	public void setSerdo(String serdo) {
		this.serdo = serdo;
	}

	public void setSerod(String serod) {
		this.serod = serod;
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