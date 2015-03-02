package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GZ39T_MV_POPIS_MESSAGE database table.
 * 
 */
@Entity
@Table(name="GZ39T_MV_POPIS_MESSAGE", schema="COCAFOPPL")
@NamedQuery(name="MvPopisMessage.findAll", query="SELECT m FROM MvPopisMessage m")
public class MvPopisMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MV_POPIS_MESSAGE_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MV_POPIS_MESSAGE_ID_GENERATOR")
	private long id;

	private String kod;

	private String text;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	//bi-directional many-to-one association to MvPopis
	@ManyToOne
	@JoinColumn(name="ID_MV_POPIS")
	private MvPopis gz39tMvPopis;

	public MvPopisMessage() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKod() {
		return this.kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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

	public MvPopis getGz39tMvPopis() {
		return gz39tMvPopis;
	}

	public void setGz39tMvPopis(MvPopis gz39tMvPopis) {
		this.gz39tMvPopis = gz39tMvPopis;
	}



}