package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the GZ39T_PREDSTAVITEL_KALKULACE database table.
 * 
 */
@Entity
@Table(name="GZ39T_PREDSTAVITEL_KALKULACE", schema="COCAFOPPL")
@NamedQuery(name = "PredstavitelKalkulace.findAll", query = "SELECT p FROM PredstavitelKalkulace p")
public class PredstavitelKalkulace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ39T_PREDSTAVITEL_KALKULACE_ID_GENERATOR", sequenceName="COCAFOPPL.HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ39T_PREDSTAVITEL_KALKULACE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date utime;

	private String uuser;

	@Column(name="VYBAVY_EDIT")
	private String vybavyEdit;

	//bi-directional many-to-one association to MtKalkulace
	@ManyToOne
	@JoinColumn(name="ID_MT_KALKULACE")
	private MtKalkulace gz39tMtKalkulace;

	//bi-directional many-to-one association to Predstavitel
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL")
	private Predstavitel gz39tPredstavitel;

	//bi-directional many-to-one association to PredstavitelMessage
	@OneToMany(mappedBy="gz39tPredstavitelKalkulace")
	private List<PredstavitelMessage> gz39tPredstavitelMessages;

	//bi-directional many-to-one association to PredstavitelPaketInfo
	@OneToMany(mappedBy="gz39tPredstavitelKalkulace")
	private List<PredstavitelPaketInfo> gz39tPredstavitelPaketInfos;

	//bi-directional many-to-one association to PredstavitelPr
	@OneToMany(mappedBy="gz39tPredstavitelKalkulace")
	private List<PredstavitelPr> gz39tPredstavitelPrs;

	public PredstavitelKalkulace() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getVybavyEdit() {
		return this.vybavyEdit;
	}

	public void setVybavyEdit(String vybavyEdit) {
		this.vybavyEdit = vybavyEdit;
	}

	public MtKalkulace getGz39tMtKalkulace() {
		return this.gz39tMtKalkulace;
	}

	public void setGz39tMtKalkulace(MtKalkulace gz39tMtKalkulace) {
		this.gz39tMtKalkulace = gz39tMtKalkulace;
	}

	public Predstavitel getGz39tPredstavitel() {
		return this.gz39tPredstavitel;
	}

	public void setGz39tPredstavitel(Predstavitel gz39tPredstavitel) {
		this.gz39tPredstavitel = gz39tPredstavitel;
	}

	public List<PredstavitelMessage> getGz39tPredstavitelMessages() {
		return this.gz39tPredstavitelMessages;
	}

	public void setGz39tPredstavitelMessages(List<PredstavitelMessage> gz39tPredstavitelMessages) {
		this.gz39tPredstavitelMessages = gz39tPredstavitelMessages;
	}

	public PredstavitelMessage addGz39tPredstavitelMessage(PredstavitelMessage gz39tPredstavitelMessage) {
		getGz39tPredstavitelMessages().add(gz39tPredstavitelMessage);
		gz39tPredstavitelMessage.setGz39tPredstavitelKalkulace(this);

		return gz39tPredstavitelMessage;
	}

	public PredstavitelMessage removeGz39tPredstavitelMessage(PredstavitelMessage gz39tPredstavitelMessage) {
		getGz39tPredstavitelMessages().remove(gz39tPredstavitelMessage);
		gz39tPredstavitelMessage.setGz39tPredstavitelKalkulace(null);

		return gz39tPredstavitelMessage;
	}

	public List<PredstavitelPaketInfo> getGz39tPredstavitelPaketInfos() {
		return this.gz39tPredstavitelPaketInfos;
	}

	public void setGz39tPredstavitelPaketInfos(List<PredstavitelPaketInfo> gz39tPredstavitelPaketInfos) {
		this.gz39tPredstavitelPaketInfos = gz39tPredstavitelPaketInfos;
	}

	public PredstavitelPaketInfo addGz39tPredstavitelPaketInfo(PredstavitelPaketInfo gz39tPredstavitelPaketInfo) {
		getGz39tPredstavitelPaketInfos().add(gz39tPredstavitelPaketInfo);
		gz39tPredstavitelPaketInfo.setGz39tPredstavitelKalkulace(this);

		return gz39tPredstavitelPaketInfo;
	}

	public PredstavitelPaketInfo removeGz39tPredstavitelPaketInfo(PredstavitelPaketInfo gz39tPredstavitelPaketInfo) {
		getGz39tPredstavitelPaketInfos().remove(gz39tPredstavitelPaketInfo);
		gz39tPredstavitelPaketInfo.setGz39tPredstavitelKalkulace(null);

		return gz39tPredstavitelPaketInfo;
	}

	public List<PredstavitelPr> getGz39tPredstavitelPrs() {
		return this.gz39tPredstavitelPrs;
	}

	public void setGz39tPredstavitelPrs(List<PredstavitelPr> gz39tPredstavitelPrs) {
		this.gz39tPredstavitelPrs = gz39tPredstavitelPrs;
	}

	public PredstavitelPr addGz39tPredstavitelPr(PredstavitelPr gz39tPredstavitelPr) {
		getGz39tPredstavitelPrs().add(gz39tPredstavitelPr);
		gz39tPredstavitelPr.setGz39tPredstavitelKalkulace(this);

		return gz39tPredstavitelPr;
	}

	public PredstavitelPr removeGz39tPredstavitelPr(PredstavitelPr gz39tPredstavitelPr) {
		getGz39tPredstavitelPrs().remove(gz39tPredstavitelPr);
		gz39tPredstavitelPr.setGz39tPredstavitelKalkulace(null);

		return gz39tPredstavitelPr;
	}

}