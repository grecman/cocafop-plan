package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GZ40T_KURZ_EUR database table.
 * 
 */
@Entity
@Table(name="GZ40T_KURZ_EUR", schema="COCAFOPPL_ARCH")
public class ArchKurzEur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="GZ40T_KURZ_EUR_ID_GENERATOR", sequenceName="COCAFOPPL_ARCH.HIBERNATE_SEQUENCE")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KURZ_EUR_ID_GENERATOR")
	private long id;

	@Column(name="KURZ_I")
	private BigDecimal kurzI;

	@Column(name="KURZ_I_DAT")
	private String kurzIDat;

	@Column(name="KURZ_M")
	private BigDecimal kurzM;

	@Column(name="KURZ_M_DAT")
	private String kurzMDat;

	@Column(name="KURZ_S")
	private BigDecimal kurzS;

	@Column(name="KURZ_S_DAT")
	private String kurzSDat;

	@Column(name="SAP_MENA")
	private String sapMena;

	//bi-directional many-to-one association to ArchKalkulace
	@ManyToOne
	@JoinColumn(name="KALKULACE")
	private ArchKalkulace gz40tKalkulace;

	public ArchKurzEur() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getKurzI() {
		return this.kurzI;
	}

	public void setKurzI(BigDecimal kurzI) {
		this.kurzI = kurzI;
	}

	public String getKurzIDat() {
		return this.kurzIDat;
	}

	public void setKurzIDat(String kurzIDat) {
		this.kurzIDat = kurzIDat;
	}

	public BigDecimal getKurzM() {
		return this.kurzM;
	}

	public void setKurzM(BigDecimal kurzM) {
		this.kurzM = kurzM;
	}

	public String getKurzMDat() {
		return this.kurzMDat;
	}

	public void setKurzMDat(String kurzMDat) {
		this.kurzMDat = kurzMDat;
	}

	public BigDecimal getKurzS() {
		return this.kurzS;
	}

	public void setKurzS(BigDecimal kurzS) {
		this.kurzS = kurzS;
	}

	public String getKurzSDat() {
		return this.kurzSDat;
	}

	public void setKurzSDat(String kurzSDat) {
		this.kurzSDat = kurzSDat;
	}

	public String getSapMena() {
		return this.sapMena;
	}

	public void setSapMena(String sapMena) {
		this.sapMena = sapMena;
	}

	public ArchKalkulace getGz40tKalkulace() {
		return this.gz40tKalkulace;
	}

	public void setGz40tKalkulace(ArchKalkulace gz40tKalkulace) {
		this.gz40tKalkulace = gz40tKalkulace;
	}

}