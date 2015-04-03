package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="GZ40T_PREDSTAVITEL_PR", schema="COCAFOPPL_ARCH")
public class ArchPredstavitelPr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_PREDSTAVITEL_PR_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_PREDSTAVITEL_PR_ID_GENERATOR")
	private long id;

	private String paket;

	private String pr;

	@Column(name="PR_EDITOVANE")
	private String prEditovane;

	private String rodina;

	private String typ;

	//bi-directional many-to-one association to Gz40tPredstavitel
	@ManyToOne
	@JoinColumn(name="ID_PREDSTAVITEL")
	private ArchPredstavitel archPredstavitel;

	public ArchPredstavitelPr() {
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

	public String getPrEditovane() {
		return this.prEditovane;
	}

	public void setPrEditovane(String prEditovane) {
		this.prEditovane = prEditovane;
	}

	public String getRodina() {
		return this.rodina;
	}

	public void setRodina(String rodina) {
		this.rodina = rodina;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

}
