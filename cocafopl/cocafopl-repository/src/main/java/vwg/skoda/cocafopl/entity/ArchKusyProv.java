package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the GZ40T_KUSY_PROV database table.
 * 
 */
@Entity
@Table(name = "GZ40T_KUSY_PROV", schema = "COCAFOPPL_ARCH")
public class ArchKusyProv implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArchKusyProvPK id;

	private String bza;

	private String local;

	private int mnfin;

	private String skoda;

	private String vw;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "kalkulace", referencedColumnName = "kalkulace", updatable = false, insertable = false),
			@JoinColumn(name = "model_Tr", referencedColumnName = "model_tr", updatable = false, insertable = false),
			@JoinColumn(name = "zavod", referencedColumnName = "zavod", updatable = false, insertable = false),
			@JoinColumn(name = "cislo_Pred", referencedColumnName = "cislo_pred", updatable = false, insertable = false) })
	ArchPredstavitel archPredstavitel;

	public ArchKusyProv() {}

	public ArchKusyProvPK getId() {
		return this.id;
	}

	public void setId(ArchKusyProvPK id) {
		this.id = id;
	}

	public String getBza() {
		return this.bza;
	}

	public void setBza(String bza) {
		this.bza = bza;
	}

	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getMnfin() {
		return this.mnfin;
	}

	public void setMnfin(int mnfin) {
		this.mnfin = mnfin;
	}

	public String getSkoda() {
		return this.skoda;
	}

	public void setSkoda(String skoda) {
		this.skoda = skoda;
	}

	public String getVw() {
		return this.vw;
	}

	public void setVw(String vw) {
		this.vw = vw;
	}

}