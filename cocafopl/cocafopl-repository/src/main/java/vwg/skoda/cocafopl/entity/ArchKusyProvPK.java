package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GZ40T_KUSY_PROV database table.
 * 
 */
@Embeddable
public class ArchKusyProvPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int kalkulace;

	@Column(name="MODEL_TR")
	private String modelTr;

	private String zavod;

	@Column(name="CISLO_PRED")
	private Integer cisloPred;

	private String cdilu;

	private String cizav;

	public ArchKusyProvPK() {
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
	public Integer getCisloPred() {
		return this.cisloPred;
	}
	public void setCisloPred(Integer cisloPred) {
		this.cisloPred = cisloPred;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArchKusyProvPK)) {
			return false;
		}
		ArchKusyProvPK castOther = (ArchKusyProvPK)other;
		return 
			(this.kalkulace == castOther.kalkulace)
			&& this.modelTr.equals(castOther.modelTr)
			&& this.zavod.equals(castOther.zavod)
			&& (this.cisloPred == castOther.cisloPred)
			&& this.cdilu.equals(castOther.cdilu)
			&& this.cizav.equals(castOther.cizav);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.kalkulace ^ (this.kalkulace >>> 32)));
		hash = hash * prime + this.modelTr.hashCode();
		hash = hash * prime + this.zavod.hashCode();
		hash = hash * prime + ((int) (this.cisloPred ^ (this.cisloPred >>> 32)));
		hash = hash * prime + this.cdilu.hashCode();
		hash = hash * prime + this.cizav.hashCode();
		
		return hash;
	}
}