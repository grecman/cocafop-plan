package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="GZ39T_MESSAGE_EXCEPTIONS", schema="COCAFOPPL")
public class MessageException implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MESSAGE_EXCEPTIONS_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MESSAGE_EXCEPTIONS_ID_GENERATOR")
	private long id;

	private String agenda;

	private String kod;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	public MessageException() {
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

	public String getKod() {
		return this.kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
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

}