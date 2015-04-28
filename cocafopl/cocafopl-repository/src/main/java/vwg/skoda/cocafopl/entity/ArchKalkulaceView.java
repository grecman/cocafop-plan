package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the GZ40V_KALKULACE database table.
 * 
 */
@Entity
@Table(name="GZ40V_KALKULACE", schema="COCAFOPPL_ARCH")
public class ArchKalkulaceView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Column(name="MOD_ZAV")
	private String modZav;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	private int terka;

	private String terkach;

	public ArchKalkulaceView() {
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
	
	

}