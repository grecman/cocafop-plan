package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="GZ39T_MV_POPIS", schema="COCAFOPPL")
public class MvPopis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MV_POPIS_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MV_POPIS_ID_GENERATOR")
	private long id;
	
	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;
	
	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODELOVA_TRIDA")
	private String modelovaTrida;

	@Column(name="MODELOVY_KLIC")
	private String modelovyKlic;

	private Integer mrok;

	private String popis;

	private String produkt;

	@Column(name="TYP_VOZU")
	private String typVozu;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	private String vybavy;

	private Integer kalkulace;
	
	private String zavod;	
	

	//bi-directional many-to-one association to MvPopisMessage
	@OneToMany(mappedBy="gz39tMvPopis")
	private List<MvPopisMessage> gz39tMvPopisMessages;

	//bi-directional many-to-one association to MvPopisPr
	@OneToMany(mappedBy="gz39tMvPopis")
	private List<MvPopisPr> gz39tMvPopisPrs;
	
	//bi-directional many-to-one association to Kalkulace
	@ManyToOne
	@JoinColumn(name="ID_USER")
	private User gz39tUser;

	
	
	public Integer getKalkulace() {
		return kalkulace;
	}

	public void setKalkulace(Integer kalkulace) {
		this.kalkulace = kalkulace;
	}

	public String getVybavy() {
		return vybavy;
	}

	public void setVybavy(String vybavy) {
		this.vybavy = vybavy;
	}

	public MvPopis() {
	}
	
	
	public String getZavod() {
		return zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

	public long getId() {
		return id;
	}

	public User getGz39tMvPopis() {
		return gz39tUser;
	}

	public String getKalkulacniDatum() {
		return kalkulacniDatum;
	}

	public String getKodZeme() {
		return kodZeme;
	}

	public String getModelovaTrida() {
		return modelovaTrida;
	}

	public String getModelovyKlic() {
		return modelovyKlic;
	}

	public Integer getMrok() {
		return mrok;
	}

	public String getPopis() {
		return popis;
	}

	public String getProdukt() {
		return produkt;
	}

	public String getTypVozu() {
		return typVozu;
	}

	public Date getUtime() {
		return utime;
	}

	public String getUuser() {
		return uuser;
	}

	public List<MvPopisMessage> getGz39tMvPopisMessages() {
		return gz39tMvPopisMessages;
	}

	public List<MvPopisPr> getGz39tMvPopisPrs() {
		return gz39tMvPopisPrs;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setGz39tUser(User gz39tUser) {
		this.gz39tUser = gz39tUser;
	}

	public void setKalkulacniDatum(String datumKomunikace) {
		this.kalkulacniDatum = datumKomunikace;
	}

	public void setKodZeme(String kodZeme) {
		this.kodZeme = kodZeme;
	}

	public void setModelovaTrida(String modelovaTrida) {
		this.modelovaTrida = modelovaTrida;
	}

	public void setModelovyKlic(String modelovyKlic) {
		this.modelovyKlic = modelovyKlic;
	}

	public void setMrok(Integer mrok) {
		this.mrok = mrok;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public void setTypVozu(String typVozu) {
		this.typVozu = typVozu;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public void setGz39tMvPopisMessages(List<MvPopisMessage> gz39tMvPopisMessages) {
		this.gz39tMvPopisMessages = gz39tMvPopisMessages;
	}

	public void setGz39tMvPopisPrs(List<MvPopisPr> gz39tMvPopisPrs) {
		this.gz39tMvPopisPrs = gz39tMvPopisPrs;
	}


}