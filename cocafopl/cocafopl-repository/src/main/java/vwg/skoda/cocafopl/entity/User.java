package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="GZ39T_USER", schema="COCAFOPPL")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_USER_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_USER_ID_GENERATOR")
	private long id;
	
	//bi-directional many-to-one association to MtKalkulace
	@OneToMany(mappedBy="gz39tMvPopis")
	private Set<MvPopis> gz39tMvPopiss;

	private String email;

	@Column(name="ID_SKONET")
	private long idSkonet;

	private String jmeno;

	private String netusername;

	private String oddeleni;

	private String prijmeni;

	@Column(name="USER_ROLE")
	private String userRole;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getIdSkonet() {
		return this.idSkonet;
	}

	public void setIdSkonet(long idSkonet) {
		this.idSkonet = idSkonet;
	}

	public String getJmeno() {
		return this.jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getNetusername() {
		return this.netusername;
	}

	public void setNetusername(String netusername) {
		this.netusername = netusername;
	}

	public String getOddeleni() {
		return this.oddeleni;
	}

	public void setOddeleni(String oddeleni) {
		this.oddeleni = oddeleni;
	}

	public String getPrijmeni() {
		return this.prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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