package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="GZ39T_MT_KALKULACE", schema="COCAFOPPL")
public class MtKalkulace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MT_KALKULACE_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MT_KALKULACE_ID_GENERATOR")
	private long id;

	private Integer mrok;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_EDITACE")
	private Date posledniEditace;
	
	@Column(name="POSLEDNI_EDITACE_DUVOD")
	private String posledniEditaceDuvod;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Column(name="POSLEDNI_VYPOCET_USER")
	private String posledniVypocetUser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to Kalkulace
	@ManyToOne
	@JoinColumn(name="ID_KALKULACE")
	private Kalkulace gz39tKalkulace;

	//bi-directional many-to-one association to Mt
	@ManyToOne
	@JoinColumn(name="ID_MT")
	private Mt gz39tMt;

	//bi-directional many-to-one association to PredstavitelKalkulace
	@OneToMany(mappedBy="gz39tMtKalkulace")
	private List<PredstavitelKalkulace> gz39tPredstavitelKalkulaces;

	public MtKalkulace() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getMrok() {
		return this.mrok;
	}

	public void setMrok(Integer mrok) {
		this.mrok = mrok;
	}

	public Date getPosledniEditace() {
		return this.posledniEditace;
	}

	public void setPosledniEditace(Date posledniEditace) {
		this.posledniEditace = posledniEditace;
	}
	
	
	public String getPosledniEditaceDuvod() {
		return posledniEditaceDuvod;
	}

	public void setPosledniEditaceDuvod(String posledniEditaceDuvod) {
		this.posledniEditaceDuvod = posledniEditaceDuvod;
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
	
	
	public Date getPosledniVypocet() {
		return posledniVypocet;
	}

	public String getPosledniVypocetUser() {
		return posledniVypocetUser;
	}

	public void setPosledniVypocet(Date posledniVypocet) {
		this.posledniVypocet = posledniVypocet;
	}

	public void setPosledniVypocetUser(String posledniVypocetUser) {
		this.posledniVypocetUser = posledniVypocetUser;
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

	public Kalkulace getGz39tKalkulace() {
		return this.gz39tKalkulace;
	}

	public void setGz39tKalkulace(Kalkulace gz39tKalkulace) {
		this.gz39tKalkulace = gz39tKalkulace;
	}

	public Mt getGz39tMt() {
		return this.gz39tMt;
	}

	public void setGz39tMt(Mt gz39tMt) {
		this.gz39tMt = gz39tMt;
	}

	public List<PredstavitelKalkulace> getGz39tPredstavitelKalkulaces() {
		return this.gz39tPredstavitelKalkulaces;
	}

	public void setGz39tPredstavitelKalkulaces(List<PredstavitelKalkulace> gz39tPredstavitelKalkulaces) {
		this.gz39tPredstavitelKalkulaces = gz39tPredstavitelKalkulaces;
	}

	public PredstavitelKalkulace addGz39tPredstavitelKalkulace(PredstavitelKalkulace gz39tPredstavitelKalkulace) {
		getGz39tPredstavitelKalkulaces().add(gz39tPredstavitelKalkulace);
		gz39tPredstavitelKalkulace.setGz39tMtKalkulace(this);

		return gz39tPredstavitelKalkulace;
	}

	public PredstavitelKalkulace removeGz39tPredstavitelKalkulace(PredstavitelKalkulace gz39tPredstavitelKalkulace) {
		getGz39tPredstavitelKalkulaces().remove(gz39tPredstavitelKalkulace);
		gz39tPredstavitelKalkulace.setGz39tMtKalkulace(null);

		return gz39tPredstavitelKalkulace;
	}

}