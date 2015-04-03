package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_KURZ_CZK", schema="COCAFOPPL_ARCH")
public class ArchKurzCzk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_KURZ_CZK_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KURZ_CZK_ID_GENERATOR")
	private long id;

	@Column(name="KURZ_I")
	private float kurzI;

	@Column(name="KURZ_I_DAT")
	private String kurzIDat;

	@Column(name="KURZ_M")
	private float kurzM;

	@Column(name="KURZ_M_DAT")
	private String kurzMDat;

	@Column(name="KURZ_S")
	private float kurzS;

	@Column(name="KURZ_S_DAT")
	private String kurzSDat;

	@Column(name="SAP_MENA")
	private String sapMena;
	
	@ManyToOne
	@JoinColumn(name="kalkulace")
	private ArchKalkulace archKalkulace;

	public ArchKurzCzk() {
	}

	public long getId() {
		return id;
	}

	public float getKurzI() {
		return kurzI;
	}

	public String getKurzIDat() {
		return kurzIDat;
	}

	public float getKurzM() {
		return kurzM;
	}

	public String getKurzMDat() {
		return kurzMDat;
	}

	public float getKurzS() {
		return kurzS;
	}

	public String getKurzSDat() {
		return kurzSDat;
	}

	public String getSapMena() {
		return sapMena;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setKurzI(float kurzI) {
		this.kurzI = kurzI;
	}

	public void setKurzIDat(String kurzIDat) {
		this.kurzIDat = kurzIDat;
	}

	public void setKurzM(float kurzM) {
		this.kurzM = kurzM;
	}

	public void setKurzMDat(String kurzMDat) {
		this.kurzMDat = kurzMDat;
	}

	public void setKurzS(float kurzS) {
		this.kurzS = kurzS;
	}

	public void setKurzSDat(String kurzSDat) {
		this.kurzSDat = kurzSDat;
	}

	public void setSapMena(String sapMena) {
		this.sapMena = sapMena;
	}


}