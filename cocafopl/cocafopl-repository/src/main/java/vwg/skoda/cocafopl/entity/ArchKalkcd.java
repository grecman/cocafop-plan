package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the GZ40T_KALKCD database table.
 * 
 */
@Entity
@Table(name="GZ40T_KALKCD", schema="COCAFOPPL_ARCH")
public class ArchKalkcd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArchKalkcdPK id;

	private int kalvar;

	private float matiac;

	private float matiace;

	private float matibc;

	private float matibce;

	private float matsac;

	private float matsace;

	private float matsbc;

	private float matsbce;


	@ManyToOne(cascade={CascadeType.REMOVE})
	@JoinColumns({ @JoinColumn(name = "kalkulace", referencedColumnName = "kalkulace", updatable = false, insertable = false),
			@JoinColumn(name = "model_Tr", referencedColumnName = "model_tr", updatable = false, insertable = false),
			@JoinColumn(name = "zavod", referencedColumnName = "zavod", updatable = false, insertable = false),
			@JoinColumn(name = "cislo_Pred", referencedColumnName = "cislo_pred", updatable = false, insertable = false) })
	ArchPredstavitel archPredstavitel;

	public ArchKalkcd() {
	}

	public ArchKalkcdPK getId() {
		return this.id;
	}

	public void setId(ArchKalkcdPK id) {
		this.id = id;
	}

	public int getKalvar() {
		return this.kalvar;
	}

	public void setKalvar(int kalvar) {
		this.kalvar = kalvar;
	}

	public float getMatiac() {
		return this.matiac;
	}

	public void setMatiac(float matiac) {
		this.matiac = matiac;
	}

	public float getMatiace() {
		return this.matiace;
	}

	public void setMatiace(float matiace) {
		this.matiace = matiace;
	}

	public float getMatibc() {
		return this.matibc;
	}

	public void setMatibc(float matibc) {
		this.matibc = matibc;
	}

	public float getMatibce() {
		return this.matibce;
	}

	public void setMatibce(float matibce) {
		this.matibce = matibce;
	}

	public float getMatsac() {
		return this.matsac;
	}

	public void setMatsac(float matsac) {
		this.matsac = matsac;
	}

	public float getMatsace() {
		return this.matsace;
	}

	public void setMatsace(float matsace) {
		this.matsace = matsace;
	}

	public float getMatsbc() {
		return this.matsbc;
	}

	public void setMatsbc(float matsbc) {
		this.matsbc = matsbc;
	}

	public float getMatsbce() {
		return this.matsbce;
	}

	public void setMatsbce(float matsbce) {
		this.matsbce = matsbce;
	}


}