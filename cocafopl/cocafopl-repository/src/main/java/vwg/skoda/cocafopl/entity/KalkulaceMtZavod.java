package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

}