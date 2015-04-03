package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="GZ40T_KALKULACE", schema="COCAFOPPL_ARCH")
public class ArchKalkulace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_KALKULACE_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_KALKULACE_ID_GENERATOR")
	private long id;

	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	private Integer terka;

	private String terkach;
	
	@OneToMany(mappedBy="archKalkulace")
	private List<ArchPredstavitel> archPredstavitels;
	
	@OneToMany(mappedBy="archKalkulace")
	private List<ArchCenikSap> archCenikSaps;

	@OneToMany(mappedBy="archKalkulace")
	private List<ArchKusovnik> archKusovniks;

	@OneToMany(mappedBy="archKalkulace")
	private List<ArchKurzCzk> archKurzCzks;

	@OneToMany(mappedBy="archKalkulace")
	private List<ArchKurzEur> archKurzEurs;

	
	public ArchKalkulace() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getKalkulace() {
		return this.kalkulace;
	}

	public void setKalkulace(int kalkulace) {
		this.kalkulace = kalkulace;
	}

	public String getKalkulacniDatum() {
		return this.kalkulacniDatum;
	}

	public void setKalkulacniDatum(String kalkulacniDatum) {
		this.kalkulacniDatum = kalkulacniDatum;
	}

	public Date getPosledniVypocet() {
		return this.posledniVypocet;
	}

	public void setPosledniVypocet(Date posledniVypocet) {
		this.posledniVypocet = posledniVypocet;
	}

	public Date getSchvaleno() {
		return this.schvaleno;
	}

	public void setSchvaleno(Date schvaleno) {
		this.schvaleno = schvaleno;
	}

	public String getSchvalil() {
		return this.schvalil;
	}

	public void setSchvalil(String schvalil) {
		this.schvalil = schvalil;
	}

	public Integer getTerka() {
		return this.terka;
	}

	public void setTerka(Integer terka) {
		this.terka = terka;
	}

	public String getTerkach() {
		return this.terkach;
	}

	public void setTerkach(String terkach) {
		this.terkach = terkach;
	}

}