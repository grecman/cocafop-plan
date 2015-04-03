package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="GZ39V_PR", schema="COCAFOPPL")
public class PrView implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	private long id;

	private String beschreibung;

	private String eindat;

	private String einschl;

	private String entdat;

	private String entschl;

	@Column(name="FAM_BEN")
	private String famBen;

	private String famkz;


	private String pkz;

	private String prnr;

	public PrView() {
	}

	public long getId() {
		return id;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public String getEindat() {
		return eindat;
	}

	public String getEinschl() {
		return einschl;
	}

	public String getEntdat() {
		return entdat;
	}

	public String getEntschl() {
		return entschl;
	}

	public String getFamBen() {
		return famBen;
	}

	public String getFamkz() {
		return famkz;
	}

	public String getPkz() {
		return pkz;
	}

	public String getPrnr() {
		return prnr;
	}



}