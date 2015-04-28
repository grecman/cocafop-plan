package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="GZ39T_OFFLINE", schema="COCAFOPPL")
public class Offline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_OFFLINE_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_OFFLINE_ID_GENERATOR")
	private long id;

	private String agenda;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAS_SPUSTENI")
	private Date casSpusteni;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAS_UKONCENI")
	private Date casUkonceni;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAS_ZADANI")
	private Date casZadani;

	private String popis;

	private String status;

	private String uzivatel;

	public Offline() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgenda() {
		return this.agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public Date getCasSpusteni() {
		return this.casSpusteni;
	}

	public void setCasSpusteni(Date casSpusteni) {
		this.casSpusteni = casSpusteni;
	}

	public Date getCasUkonceni() {
		return this.casUkonceni;
	}

	public void setCasUkonceni(Date casUkonceni) {
		this.casUkonceni = casUkonceni;
	}

	public Date getCasZadani() {
		return this.casZadani;
	}

	public void setCasZadani(Date casZadani) {
		this.casZadani = casZadani;
	}

	public String getPopis() {
		return this.popis;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUzivatel() {
		return this.uzivatel;
	}

	public void setUzivatel(String uzivatel) {
		this.uzivatel = uzivatel;
	}

}