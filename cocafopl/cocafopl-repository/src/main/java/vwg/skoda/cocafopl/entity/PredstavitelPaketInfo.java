package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GZ39T_PREDSTAVITEL_PAKET_INFO database table.
 * 
 */
@Entity
@Table(name="GZ39T_PREDSTAVITEL_PAKET_INFO", schema="COCAFOPPL")
@NamedQuery(name="PredstavitelPaketInfo.findAll", query="SELECT p FROM PredstavitelPaketInfo p")
public class PredstavitelPaketInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_PREDSTAVITEL_PAKET_INFO_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_PREDSTAVITEL_PAKET_INFO_ID_GENERATOR")
	private long id;

	private String paket;

	private String pr;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to PredstavitelKalkulace
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL_KALKULACE")
	private PredstavitelKalkulace gz39tPredstavitelKalkulace;

	public PredstavitelPaketInfo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPaket() {
		return this.paket;
	}

	public void setPaket(String paket) {
		this.paket = paket;
	}

	public String getPr() {
		return this.pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
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