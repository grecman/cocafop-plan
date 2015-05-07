package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the GZ40V_KALKULACE_MT_ZAV database table.
 * 
 */
@Entity
@Table(name="GZ40V_KALKULACE_MT_ZAV", schema="COCAFOPPL_ARCH")
public class ArchKalkulaceMtZavView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
		
	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	private int terka;

	private String terkach;
	
	@Column(name="MODEL_TR")
	private String modelTr;
	
	private String zavod;

	public ArchKalkulaceMtZavView() {
	}
	
	public String getId() {
		return id;
	}

	public int getKalkulace() {
		return kalkulace;
	}

	public String getKalkulacniDatum() {
		return kalkulacniDatum;
	}

	public Date getPosledniVypocet() {
		return posledniVypocet;
	}

	public Date getSchvaleno() {
		return schvaleno;
	}

	public String getSchvalil() {
		return schvalil;
	}

	public int getTerka() {
		return terka;
	}

	public String getTerkach() {
		return terkach;
	}

	public String getModelTr() {
		return modelTr;
	}

	public String getZavod() {
		return zavod;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKalkulace(int kalkulace) {
		this.kalkulace = kalkulace;
	}

	public void setKalkulacniDatum(String kalkulacniDatum) {
		this.kalkulacniDatum = kalkulacniDatum;
	}

	public void setPosledniVypocet(Date posledniVypocet) {
		this.posledniVypocet = posledniVypocet;
	}

	public void setSchvaleno(Date schvaleno) {
		this.schvaleno = schvaleno;
	}

	public void setSchvalil(String schvalil) {
		this.schvalil = schvalil;
	}

	public void setTerka(int terka) {
		this.terka = terka;
	}

	public void setTerkach(String terkach) {
		this.terkach = terkach;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}
	
	
	

}