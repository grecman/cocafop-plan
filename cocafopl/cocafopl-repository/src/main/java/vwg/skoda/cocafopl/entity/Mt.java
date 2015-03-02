package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GZ39T_MT database table.
 * 
 */
@Entity
@Table(name="GZ39T_MT", schema="COCAFOPPL")
public class Mt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_MT_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_MT_ID_GENERATOR")
	private long id;

	@Column(name="DELIVERY_DESTINATION")
	private String deliveryDestination;

	@Column(name="IMPORTER_NUMBER")
	private String importerNumber;

	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODEL_TR")
	private String modelTr;

	@Column(name="PLATNOST_DO")
	private Integer platnostDo;

	@Column(name="PLATNOST_OD")
	private Integer platnostOd;

	private String popis;

	private String produkt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	private String zavod;

	//bi-directional many-to-one association to MtKalkulace
	@OneToMany(mappedBy="gz39tMt")
	private List<MtKalkulace> gz39tMtKalkulaces;
	
	//bi-directional many-to-one association to MtKalkulace
	@OneToMany(mappedBy="gz39tMt")
	private List<Predstavitel> gz39tPredstavitels;

	public Mt() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeliveryDestination() {
		return this.deliveryDestination;
	}

	public void setDeliveryDestination(String deliveryDestination) {
		this.deliveryDestination = deliveryDestination;
	}

	public String getImporterNumber() {
		return this.importerNumber;
	}

	public void setImporterNumber(String importerNumber) {
		this.importerNumber = importerNumber;
	}

	public String getKodZeme() {
		return this.kodZeme;
	}

	public void setKodZeme(String kodZeme) {
		this.kodZeme = kodZeme;
	}

	public String getModelTr() {
		return this.modelTr;
	}

	public void setModelTr(String modelTr) {
		this.modelTr = modelTr;
	}

	public Integer getPlatnostDo() {
		return this.platnostDo;
	}

	public void setPlatnostDo(Integer platnostDo) {
		this.platnostDo = platnostDo;
	}

	public Integer getPlatnostOd() {
		return this.platnostOd;
	}

	public void setPlatnostOd(Integer platnostOd) {
		this.platnostOd = platnostOd;
	}

	public String getPopis() {
		return this.popis;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public String getProdukt() {
		return this.produkt;
	}

	public void setProdukt(String produkt) {
		this.produkt = produkt;
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

	public String getZavod() {
		return this.zavod;
	}

	public void setZavod(String zavod) {
		this.zavod = zavod;
	}

	public List<MtKalkulace> getGz39tMtKalkulaces() {
		return this.gz39tMtKalkulaces;
	}

	public void setGz39tMtKalkulaces(List<MtKalkulace> gz39tMtKalkulaces) {
		this.gz39tMtKalkulaces = gz39tMtKalkulaces;
	}
	
	public List<Predstavitel> getGz39tPredstavitels() {
		return gz39tPredstavitels;
	}

	public void setGz39tPredstavitels(List<Predstavitel> gz39tPredstavitels) {
		this.gz39tPredstavitels = gz39tPredstavitels;
	}

	public MtKalkulace addGz39tMtKalkulace(MtKalkulace gz39tMtKalkulace) {
		getGz39tMtKalkulaces().add(gz39tMtKalkulace);
		gz39tMtKalkulace.setGz39tMt(this);

		return gz39tMtKalkulace;
	}

	public MtKalkulace removeGz39tMtKalkulace(MtKalkulace gz39tMtKalkulace) {
		getGz39tMtKalkulaces().remove(gz39tMtKalkulace);
		gz39tMtKalkulace.setGz39tMt(null);

		return gz39tMtKalkulace;
	}

}