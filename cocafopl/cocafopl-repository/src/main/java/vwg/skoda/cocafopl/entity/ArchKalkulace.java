package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the GZ40T_KALKULACE database table.
 * 
 */
@Entity
@Table(name="GZ40T_KALKULACE", schema="COCAFOPPL_ARCH")
public class ArchKalkulace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	private int terka;

	private String terkach;

	//bi-directional many-to-one association to ArchCenikSap
	@OneToMany(mappedBy="gz40tKalkulace")
	private Set<ArchCenikSap> gz40tCenikSaps;

	//bi-directional many-to-one association to ArchKurzCzk
	@OneToMany(mappedBy="gz40tKalkulace")
	private Set<ArchKurzCzk> gz40tKurzCzks;

	//bi-directional many-to-one association to ArchKurzEur
	@OneToMany(mappedBy="gz40tKalkulace")
	private Set<ArchKurzEur> gz40tKurzEurs;

	//bi-directional many-to-one association to ArchKusovnik
	@OneToMany(mappedBy="gz40tKalkulace")
	private Set<ArchKusovnik> gz40tKusovniks;

	//bi-directional many-to-one association to ArchPredstavitel
	@OneToMany(mappedBy="gz40tKalkulace")
	private Set<ArchPredstavitel> gz40tPredstavitels;

	public ArchKalkulace() {
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

	public int getTerka() {
		return this.terka;
	}

	public void setTerka(int terka) {
		this.terka = terka;
	}

	public String getTerkach() {
		return this.terkach;
	}

	public void setTerkach(String terkach) {
		this.terkach = terkach;
	}

	public Set<ArchCenikSap> getGz40tCenikSaps() {
		return this.gz40tCenikSaps;
	}

	public void setGz40tCenikSaps(Set<ArchCenikSap> gz40tCenikSaps) {
		this.gz40tCenikSaps = gz40tCenikSaps;
	}

	public ArchCenikSap addGz40tCenikSap(ArchCenikSap gz40tCenikSap) {
		getGz40tCenikSaps().add(gz40tCenikSap);
		gz40tCenikSap.setGz40tKalkulace(this);

		return gz40tCenikSap;
	}

	public ArchCenikSap removeGz40tCenikSap(ArchCenikSap gz40tCenikSap) {
		getGz40tCenikSaps().remove(gz40tCenikSap);
		gz40tCenikSap.setGz40tKalkulace(null);

		return gz40tCenikSap;
	}

	public Set<ArchKurzCzk> getGz40tKurzCzks() {
		return this.gz40tKurzCzks;
	}

	public void setGz40tKurzCzks(Set<ArchKurzCzk> gz40tKurzCzks) {
		this.gz40tKurzCzks = gz40tKurzCzks;
	}

	public ArchKurzCzk addGz40tKurzCzk(ArchKurzCzk gz40tKurzCzk) {
		getGz40tKurzCzks().add(gz40tKurzCzk);
		gz40tKurzCzk.setGz40tKalkulace(this);

		return gz40tKurzCzk;
	}

	public ArchKurzCzk removeGz40tKurzCzk(ArchKurzCzk gz40tKurzCzk) {
		getGz40tKurzCzks().remove(gz40tKurzCzk);
		gz40tKurzCzk.setGz40tKalkulace(null);

		return gz40tKurzCzk;
	}

	public Set<ArchKurzEur> getGz40tKurzEurs() {
		return this.gz40tKurzEurs;
	}

	public void setGz40tKurzEurs(Set<ArchKurzEur> gz40tKurzEurs) {
		this.gz40tKurzEurs = gz40tKurzEurs;
	}

	public ArchKurzEur addGz40tKurzEur(ArchKurzEur gz40tKurzEur) {
		getGz40tKurzEurs().add(gz40tKurzEur);
		gz40tKurzEur.setGz40tKalkulace(this);

		return gz40tKurzEur;
	}

	public ArchKurzEur removeGz40tKurzEur(ArchKurzEur gz40tKurzEur) {
		getGz40tKurzEurs().remove(gz40tKurzEur);
		gz40tKurzEur.setGz40tKalkulace(null);

		return gz40tKurzEur;
	}

	public Set<ArchKusovnik> getGz40tKusovniks() {
		return this.gz40tKusovniks;
	}

	public void setGz40tKusovniks(Set<ArchKusovnik> gz40tKusovniks) {
		this.gz40tKusovniks = gz40tKusovniks;
	}

	public ArchKusovnik addGz40tKusovnik(ArchKusovnik gz40tKusovnik) {
		getGz40tKusovniks().add(gz40tKusovnik);
		gz40tKusovnik.setGz40tKalkulace(this);

		return gz40tKusovnik;
	}

	public ArchKusovnik removeGz40tKusovnik(ArchKusovnik gz40tKusovnik) {
		getGz40tKusovniks().remove(gz40tKusovnik);
		gz40tKusovnik.setGz40tKalkulace(null);

		return gz40tKusovnik;
	}

	public Set<ArchPredstavitel> getGz40tPredstavitels() {
		return this.gz40tPredstavitels;
	}

	public void setGz40tPredstavitels(Set<ArchPredstavitel> gz40tPredstavitels) {
		this.gz40tPredstavitels = gz40tPredstavitels;
	}

	public ArchPredstavitel addGz40tPredstavitel(ArchPredstavitel gz40tPredstavitel) {
		getGz40tPredstavitels().add(gz40tPredstavitel);
		gz40tPredstavitel.setGz40tKalkulace(this);

		return gz40tPredstavitel;
	}

	public ArchPredstavitel removeGz40tPredstavitel(ArchPredstavitel gz40tPredstavitel) {
		getGz40tPredstavitels().remove(gz40tPredstavitel);
		gz40tPredstavitel.setGz40tKalkulace(null);

		return gz40tPredstavitel;
	}

}