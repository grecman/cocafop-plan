package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_MV", schema="COCAFOPPL_ARCH")
public class ArchMv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_MV_ID_GENERATOR", sequenceName="COCAFOPPL_ARCH.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_MV_ID_GENERATOR")
	private long id;

	private Integer kalkulace;

	private String mk;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String produkt;

	@Column(name="PRPOZ_VV")
	private String prpozVv;

	@Column(name="PRPOZ_ZV")
	private String prpozZv;

	private String username;

	private String zavod;
	
	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;
	
	@Column(name="KOD_ZEME")
	private String kodZeme;

	private Integer mrok;
	
	private String jmeno;
	
	private String prijmeni;
	
	private String oddeleni;
	
	private String popis;
	
	@Column(name="VYBAVY_ZV")
	private String vybavyZv;
	
	@Column(name="VYBAVY_VV")
	private String vybavyVv;
	
	@Column(name="ID_ZV")
	private Long idZv;
	
	@Column(name="ID_VV")
	private Long idVv;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	public ArchMv() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
		
	public String getPopis() {
		return popis;
	}

	public String getVybavyZv() {
		return vybavyZv;
	}

	public String getVybavyVv() {
		return vybavyVv;
	}

	public Long getIdZv() {
		return idZv;
	}

	public Long getIdVv() {
		return idVv;
	}

	public Date getUtime() {
		return utime;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public void setVybavyZv(String vybavyZv) {
		this.vybavyZv = vybavyZv;
	}

	public void setVybavyVv(String vybavyVv) {
		this.vybavyVv = vybavyVv;
	}

	public void setIdZv(Long idZv) {
		this.idZv = idZv;
	}

	public void setIdVv(Long idVv) {
		this.idVv = idVv;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getKalkulacniDatum() {
		return kalkulacniDatum;
	}

	public String getKodZeme() {
		return kodZeme;
	}

	public Integer getMrok() {
		return mrok;
	}

	public String getJmeno() {
		return jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public String getOddeleni() {
		return oddeleni;
	}

	public void setKalkulacniDatum(String kalkulacniDatum) {
		this.kalkulacniDatum = kalkulacniDatum;
	}

	public void setKodZeme(String kodZeme) {
		this.kodZeme = kodZeme;
	}

	public void setMrok(Integer mrok) {
		this.mrok = mrok;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public void setOddeleni(String oddeleni) {
		this.oddeleni = oddeleni;
	}

	public Integer getKalkulace() {
		return this.kalkulace;
	}

	public void setKalkulace(Integer kalkulace) {
		this.kalkulace = kalkulace;
	}

	public String getMk() {
		return this.mk;
	}

	public void setMk(String mk) {
		this.mk = mk;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public String getProdukt() {
		return this.produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public String getPrpozVv() {
		return this.prpozVv;
	}

	public void setPrpozVv(String prpozVv) {
		this.prpozVv = prpozVv;
	}

	public String getPrpozZv() {
		return this.prpozZv;
	}

	public void setPrpozZv(String prpozZv) {
		this.prpozZv = prpozZv;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZavod() {
		return this.zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

}