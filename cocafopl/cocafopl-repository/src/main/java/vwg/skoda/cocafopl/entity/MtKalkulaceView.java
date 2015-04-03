package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="GZ39V_MT_KALKULACE", schema="COCAFOPPL")
public class MtKalkulaceView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODEL_TR")
	private String modelTr;

	private int mrok;

	@Temporal(TemporalType.DATE)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Column(name="POSLEDNI_VYPOCET_USER")
	private String posledniVypocetUser;

	private String produkt;

	@Temporal(TemporalType.DATE)
	private Date schvaleno;

	private String schvalil;

	private String zavod;
	
	private String popis;
	
	@Column(name="POSLEDNI_EDITACE")
	private Date posledniEditace;
	
	@Column(name="POSLEDNI_EDITACE_DUVOD")
	private String posledniEditaceDuvod;
	
	@Column(name="PR_ANO_NE")
	private String prAnoNe;

	public MtKalkulaceView() {
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

	public String getKodZeme() {
		return kodZeme;
	}

	public String getModelTr() {
		return modelTr;
	}

	public int getMrok() {
		return mrok;
	}

	public Date getPosledniVypocet() {
		return posledniVypocet;
	}

	public String getPosledniVypocetUser() {
		return posledniVypocetUser;
	}

	public Date getPosledniEditace() {
		return posledniEditace;
	}

	public String getPosledniEditaceDuvod() {
		return posledniEditaceDuvod;
	}

	public String getProdukt() {
		return produkt;
	}

	public Date getSchvaleno() {
		return schvaleno;
	}

	public String getSchvalil() {
		return schvalil;
	}

	public String getZavod() {
		return zavod;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPopis() {
		return popis;
	}

	public String getPrAnoNe() {
		return prAnoNe;
	}

	public void setPrAnoNe(String prAnoNe) {
		this.prAnoNe = prAnoNe;
	}
	
	
}