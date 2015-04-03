package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ39V_PREDSTAVITEL_KALKULACE", schema="COCAFOPPL")
public class PredstavitelKalkulaceView implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long cetnost;

	@Column(name="CISLO_PRED")
	private Integer cisloPred;

	@Column(name="CISLO_PRED_MIN")
	private Integer cisloPredMin;

	private Boolean comix;

	@Column(name="EU_NORMA")
	private String euNorma;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PK")
	private long idPk;

	private int kalkulace;

	@Column(name="KALKULACNI_DATUM")
	private String kalkulacniDatum;

	@Column(name="KOD_ZEME")
	private String kodZeme;

	@Column(name="MODEL_TR")
	private String modelTr;

	@Column(name="MODELOVY_KLIC")
	private String modelovyKlic;

	private String obsah;

	@Column(name="PLATNOST_DO")
	private Integer platnostDo;

	@Column(name="PLATNOST_OD")
	private Integer platnostOd;
	
	@Column(name="PLATNOST_DO_MT")
	private Integer platnostDoMt;

	@Column(name="PLATNOST_OD_MT")
	private Integer platnostOdMt;

	private String poznamka;
	
	private String popis;
	
	private String mrok;

	@Column(name="PR_COUNT")
	private Integer prCount;

	private String produkt;

	private String rozlozenost;

	private String typ;

	private String vybava;

	private String vybavy;

	@Column(name="VYBAVY_EDIT")
	private String vybavyEdit;

	private String vykon;

	private String zavod;

	public PredstavitelKalkulaceView() {
	}

	public Long getCetnost() {
		return cetnost;
	}

	public Integer getCisloPred() {
		return cisloPred;
	}

	public Integer getCisloPredMin() {
		return cisloPredMin;
	}

	public Boolean getComix() {
		return comix;
	}

	public String getEuNorma() {
		return euNorma;
	}

	public long getIdPk() {
		return idPk;
	}

	public int getKalkulace() {
		return kalkulace;
	}

	public String getKalkulacniDatum() {
		return kalkulacniDatum;
	}

	public String getKodZeme() {
		return kodZeme;
	}

	public String getModelTr() {
		return modelTr;
	}

	public String getModelovyKlic() {
		return modelovyKlic;
	}

	public String getObsah() {
		return obsah;
	}

	public Integer getPlatnostDo() {
		return platnostDo;
	}

	public Integer getPlatnostOd() {
		return platnostOd;
	}

	public Integer getPlatnostDoMt() {
		return platnostDoMt;
	}

	public Integer getPlatnostOdMt() {
		return platnostOdMt;
	}

	public String getPoznamka() {
		return poznamka;
	}

	public String getPopis() {
		return popis;
	}

	public String getMrok() {
		return mrok;
	}

	public Integer getPrCount() {
		return prCount;
	}

	public String getProdukt() {
		return produkt;
	}

	public String getRozlozenost() {
		return rozlozenost;
	}

	public String getTyp() {
		return typ;
	}

	public String getVybava() {
		return vybava;
	}

	public String getVybavy() {
		return vybavy;
	}

	public String getVybavyEdit() {
		return vybavyEdit;
	}

	public String getVykon() {
		return vykon;
	}

	public String getZavod() {
		return zavod;
	}

	

}