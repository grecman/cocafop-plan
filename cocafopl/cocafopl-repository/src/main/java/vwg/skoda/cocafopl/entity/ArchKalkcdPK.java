package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GZ40T_KALKCD database table.
 * 
 */
@Embeddable
public class ArchKalkcdPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int kalkulace;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String zavod;

	private String cdilu;

	private String cizav;

	private String verze;

	@Column(name="CISLO_PRED")
	private int cisloPred;

	public ArchKalkcdPK() {
	}
	public int getKalkulace() {
		return this.kalkulace;
	}
	public void setKalkulace(int kalkulace) {
		this.kalkulace = kalkulace;
	}
	public String getModelTr() {
		return this.modelTr;
	}
	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}
	public String getZavod() {
		return this.zavod;
	}
	public void setZavod(String zavod) {
		this.zavod = zavod;
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
	public String getVerze() {
		return this.verze;
	}
	public void setVerze(String verze) {
		this.verze = verze;
	}
	public int getCisloPred() {
		return this.cisloPred;
	}
	public void setCisloPred(int cisloPred) {
		this.cisloPred = cisloPred;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArchKalkcdPK)) {
			return false;
		}
		ArchKalkcdPK castOther = (ArchKalkcdPK)other;
		return 
			(this.kalkulace == castOther.kalkulace)
			&& this.modelTr.equals(castOther.modelTr)
			&& this.zavod.equals(castOther.zavod)
			&& this.cdilu.equals(castOther.cdilu)
			&& this.cizav.equals(castOther.cizav)
			&& this.verze.equals(castOther.verze)
			&& (this.cisloPred == castOther.cisloPred);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.kalkulace ^ (this.kalkulace >>> 32)));
		hash = hash * prime + this.modelTr.hashCode();
		hash = hash * prime + this.zavod.hashCode();
		hash = hash * prime + this.cdilu.hashCode();
		hash = hash * prime + this.cizav.hashCode();
		hash = hash * prime + this.verze.hashCode();
		hash = hash * prime + ((int) (this.cisloPred ^ (this.cisloPred >>> 32)));
		
		return hash;
	}
}