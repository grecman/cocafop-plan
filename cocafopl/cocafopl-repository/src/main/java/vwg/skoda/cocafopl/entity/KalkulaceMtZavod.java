package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GZ39V_KALKULACE_MT_ZAVOD database table.
 * 
 */
@Entity
@Table(name="GZ39V_KALKULACE_MT_ZAVOD", schema="COCAFOPPL")
public class KalkulaceMtZavod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KALKULACE")
	private long idKalkulace;

	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Column(name="MOD_ZAV")
	private String modZav;

	private int rok;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	public KalkulaceMtZavod() {
	}

	public long getIdKalkulace() {
		return idKalkulace;
	}

	public int getKalkulace() {
		return kalkulace;
	}

	public String getKalkulacniDatum() {
		return kalkulacniDatum;
	}

	public String getModZav() {
		return modZav;
	}

	public int getRok() {
		return rok;
	}


	public Date getSchvaleno() {
		return schvaleno;
	}


	public String getSchvalil() {
		return schvalil;
	}


	public void setIdKalkulace(long idKalkulace) {
		this.idKalkulace = idKalkulace;
	}


	public void setKalkulace(int kalkulace) {
		this.kalkulace = kalkulace;
	}


	public void setKalkulacniDatum(String kalkulacniDatum) {
		this.kalkulacniDatum = kalkulacniDatum;
	}


	public void setModZav(String modZav) {
		this.modZav = modZav;
	}


	public void setRok(int rok) {
		this.rok = rok;
	}


	public void setSchvaleno(Date schvaleno) {
		this.schvaleno = schvaleno;
	}


	public void setSchvalil(String schvalil) {
		this.schvalil = schvalil;
	}


}