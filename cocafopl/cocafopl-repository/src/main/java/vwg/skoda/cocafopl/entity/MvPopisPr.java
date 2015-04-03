package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="GZ39T_MV_POPIS_PR", schema="COCAFOPPL")
public class MvPopisPr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MV_POPIS_PR_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MV_POPIS_PR_ID_GENERATOR")
	private long id;

	private String pr;

	@Column(name="PR_EDITOVANE")
	private String prEditovane;

	private String rodina;

	private String typ;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;
	
	private String paket;

	//bi-directional many-to-one association to MvPopis
	@ManyToOne
	@JoinColumn(name="ID_MV_POPIS")
	private MvPopis gz39tMvPopis;

	public MvPopisPr() {
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
	
	public String getPaket() {
		return paket;
	}

	public void setPaket(String paket) {
		this.paket = paket;
	}

	public MvPopis getGz39tMvPopis() {
		return this.gz39tMvPopis;
	}

	public void setGz39tMvPopis(MvPopis gz39tMvPopis) {
		this.gz39tMvPopis = gz39tMvPopis;
	}

}