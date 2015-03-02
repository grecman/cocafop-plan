package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="GZ39T_KALKULACE", schema="COCAFOPPL")
@NamedQuery(name="Kalkulace.findAll", query="SELECT k FROM Kalkulace k")
public class Kalkulace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_KALKULACE_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_KALKULACE_ID_GENERATOR")
	private long id;

	private Integer kalkulace;
	
	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POSLEDNI_VYPOCET")
	private Date posledniVypocet;

	@Column(name="POSLEDNI_VYPOCET_USER")
	private String posledniVypocetUser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date schvaleno;

	private String schvalil;

	private Integer terka;

	private String terkach;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to MtKalkulace
	@OneToMany(mappedBy="gz39tKalkulace")
	private List<MtKalkulace> gz39tMtKalkulaces;
	
	public Kalkulace() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getKalkulace() {
		return this.kalkulace;
	}

	public void setKalkulace(Integer kalkulace) {
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

	public String getPosledniVypocetUser() {
		return this.posledniVypocetUser;
	}

	public void setPosledniVypocetUser(String posledniVypocetUser) {
		this.posledniVypocetUser = posledniVypocetUser;
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
		return terkach;
	}

	public void setTerkach(String terkach) {
		this.terkach = terkach;
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

	public List<MtKalkulace> getGz39tMtKalkulaces() {
		return this.gz39tMtKalkulaces;
	}

	public void setGz39tMtKalkulaces(List<MtKalkulace> gz39tMtKalkulaces) {
		this.gz39tMtKalkulaces = gz39tMtKalkulaces;
	}

	public MtKalkulace addGz39tMtKalkulace(MtKalkulace gz39tMtKalkulace) {
		getGz39tMtKalkulaces().add(gz39tMtKalkulace);
		gz39tMtKalkulace.setGz39tKalkulace(this);

		return gz39tMtKalkulace;
	}

	public MtKalkulace removeGz39tMtKalkulace(MtKalkulace gz39tMtKalkulace) {
		getGz39tMtKalkulaces().remove(gz39tMtKalkulace);
		gz39tMtKalkulace.setGz39tKalkulace(null);

		return gz39tMtKalkulace;
	}

}