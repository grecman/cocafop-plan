package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="GZ39T_PREDSTAVITEL_PR", schema="COCAFOPPL")
public class PredstavitelPr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_PREDSTAVITEL_PR_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE_PR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_PREDSTAVITEL_PR_ID_GENERATOR")
	private long id;

	private String pr;

	@Column(name="PR_EDITOVANE")
	private String prEditovane;

	private String rodina;

	private String typ;
	
	private String paket;

	public String getPaket() {
		return this.paket;
	}

	public void setPaket(String paket) {
		this.paket = paket;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to PredstavitelKalkulace
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL_KALKULACE")
	private PredstavitelKalkulace gz39tPredstavitelKalkulace;

	public PredstavitelPr() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPr() {
		return this.pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getPrEditovane() {
		return this.prEditovane;
	}

	public void setPrEditovane(String prEditovane) {
		this.prEditovane = prEditovane;
	}

	public String getRodina() {
		return this.rodina;
	}

	public void setRodina(String rodina) {
		this.rodina = rodina;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public PredstavitelKalkulace getGz39tPredstavitelKalkulace() {
		return this.gz39tPredstavitelKalkulace;
	}

	public void setGz39tPredstavitelKalkulace(PredstavitelKalkulace gz39tPredstavitelKalkulace) {
		this.gz39tPredstavitelKalkulace = gz39tPredstavitelKalkulace;
	}

}