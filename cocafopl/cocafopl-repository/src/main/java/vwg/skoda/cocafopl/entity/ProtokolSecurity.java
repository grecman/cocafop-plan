package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the GZ39T_PROTOKOL_SECURITY database table.
 * 
 */
@Entity
@Table(name="GZ39T_PROTOKOL_SECURITY", schema="COCAFOPPL")
@NamedQuery(name="ProtokolSecurity.findAll", query="SELECT p FROM ProtokolSecurity p")
public class ProtokolSecurity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_PROTOKOL_SECURITY_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_PROTOKOL_SECURITY_ID_GENERATOR")
	private long id;

	private String action;

	private String data;

	private String netusername;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public ProtokolSecurity() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNetusername() {
		return this.netusername;
	}

	public void setNetusername(String netusername) {
		this.netusername = netusername;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}