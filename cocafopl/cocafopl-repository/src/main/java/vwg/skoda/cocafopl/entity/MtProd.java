package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "GZ39V_MT_PROD", schema = "COCAFOPPL")
public class MtProd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MODELOVA_TRIDA")
	private String modelovaTrida;

	private String produkt;

	public MtProd() {}

	public String getModelovaTrida() {
		return this.modelovaTrida;
	}

	public String getProdukt() {
		return this.produkt;
	}
}