package vwg.skoda.cocafopl.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="GZ40T_CENIK_SAP", schema="COCAFOPPL_ARCH")
public class ArchCenikSap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GZ40T_CENIK_SAP_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_CENIK_SAP_ID_GENERATOR")
	private long id;

	private String cdilu;

	private float ceniak;

	private float ceniake;

	private float cenias;

	private float ceniase;

	private float cenibk;

	private float cenibke;

	private float cenibs;

	private float cenibse;

	private float cenmak;

	private float cenmake;

	private float cenmas;

	private float cenmase;

	private float cenmbk;

	private float cenmbke;

	private float cenmbs;

	private float cenmbse;

	private float censak;

	private float censake;

	private float censas;

	private float censase;

	private float censbk;

	private float censbke;

	private float censbs;

	private float censbse;

	private float cenzak;

	private float cenzake;

	private float cenzas;

	private float cenzase;

	private float cenzbk;

	private float cenzbke;

	private float cenzbs;

	private float cenzbse;

	private String cizav;

	private String matnr;

	private String me;

	@Column(name="ME_SAP")
	private String meSap;

	@Column(name="SAP_DISPO")
	private String sapDispo;

	@Column(name="SAP_EKGRP")
	private String sapEkgrp;

	@Column(name="SAP_KLIC")
	private String sapKlic;

	@Column(name="SAP_LIFNR")
	private String sapLifnr;

	@Column(name="SAP_MEINS")
	private String sapMeins;

	@Column(name="SAP_MENA")
	private String sapMena;

	@Column(name="SAP_NAME1")
	private String sapName1;

	@Column(name="SAP_NAZEV")
	private String sapNazev;

	@Column(name="SAP_PEINH")
	private int sapPeinh;

	@Column(name="SAP_POCRS")
	private int sapPocrs;

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
	@ManyToOne
	@JoinColumn(name="kalkulace")
	private ArchKalkulace archKalkulace;

	public ArchCenikSap() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCdilu() {
		return this.cdilu;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
	}

	public float getCeniak() {
		return ceniak;
	}

	public float getCeniake() {
		return ceniake;
	}

	public float getCenias() {
		return cenias;
	}

	public float getCeniase() {
		return ceniase;
	}

	public float getCenibk() {
		return cenibk;
	}

	public float getCenibke() {
		return cenibke;
	}

	public float getCenibs() {
		return cenibs;
	}

	public float getCenibse() {
		return cenibse;
	}

	public float getCenmak() {
		return cenmak;
	}

	public float getCenmake() {
		return cenmake;
	}

	public float getCenmas() {
		return cenmas;
	}

	public float getCenmase() {
		return cenmase;
	}

	public float getCenmbk() {
		return cenmbk;
	}

	public float getCenmbke() {
		return cenmbke;
	}

	public float getCenmbs() {
		return cenmbs;
	}

	public float getCenmbse() {
		return cenmbse;
	}

	public float getCensak() {
		return censak;
	}

	public float getCensake() {
		return censake;
	}

	public float getCensas() {
		return censas;
	}

	public float getCensase() {
		return censase;
	}

	public float getCensbk() {
		return censbk;
	}

	public float getCensbke() {
		return censbke;
	}

	public float getCensbs() {
		return censbs;
	}

	public float getCensbse() {
		return censbse;
	}

	public float getCenzak() {
		return cenzak;
	}

	public float getCenzake() {
		return cenzake;
	}

	public float getCenzas() {
		return cenzas;
	}

	public float getCenzase() {
		return cenzase;
	}

	public float getCenzbk() {
		return cenzbk;
	}

	public float getCenzbke() {
		return cenzbke;
	}

	public float getCenzbs() {
		return cenzbs;
	}

	public float getCenzbse() {
		return cenzbse;
	}

	public void setCeniak(float ceniak) {
		this.ceniak = ceniak;
	}

	public void setCeniake(float ceniake) {
		this.ceniake = ceniake;
	}

	public void setCenias(float cenias) {
		this.cenias = cenias;
	}

	public void setCeniase(float ceniase) {
		this.ceniase = ceniase;
	}

	public void setCenibk(float cenibk) {
		this.cenibk = cenibk;
	}

	public void setCenibke(float cenibke) {
		this.cenibke = cenibke;
	}

	public void setCenibs(float cenibs) {
		this.cenibs = cenibs;
	}

	public void setCenibse(float cenibse) {
		this.cenibse = cenibse;
	}

	public void setCenmak(float cenmak) {
		this.cenmak = cenmak;
	}

	public void setCenmake(float cenmake) {
		this.cenmake = cenmake;
	}

	public void setCenmas(float cenmas) {
		this.cenmas = cenmas;
	}

	public void setCenmase(float cenmase) {
		this.cenmase = cenmase;
	}

	public void setCenmbk(float cenmbk) {
		this.cenmbk = cenmbk;
	}

	public void setCenmbke(float cenmbke) {
		this.cenmbke = cenmbke;
	}

	public void setCenmbs(float cenmbs) {
		this.cenmbs = cenmbs;
	}

	public void setCenmbse(float cenmbse) {
		this.cenmbse = cenmbse;
	}

	public void setCensak(float censak) {
		this.censak = censak;
	}

	public void setCensake(float censake) {
		this.censake = censake;
	}

	public void setCensas(float censas) {
		this.censas = censas;
	}

	public void setCensase(float censase) {
		this.censase = censase;
	}

	public void setCensbk(float censbk) {
		this.censbk = censbk;
	}

	public void setCensbke(float censbke) {
		this.censbke = censbke;
	}

	public void setCensbs(float censbs) {
		this.censbs = censbs;
	}

	public void setCensbse(float censbse) {
		this.censbse = censbse;
	}

	public void setCenzak(float cenzak) {
		this.cenzak = cenzak;
	}

	public void setCenzake(float cenzake) {
		this.cenzake = cenzake;
	}

	public void setCenzas(float cenzas) {
		this.cenzas = cenzas;
	}

	public void setCenzase(float cenzase) {
		this.cenzase = cenzase;
	}

	public void setCenzbk(float cenzbk) {
		this.cenzbk = cenzbk;
	}

	public void setCenzbke(float cenzbke) {
		this.cenzbke = cenzbke;
	}

	public void setCenzbs(float cenzbs) {
		this.cenzbs = cenzbs;
	}

	public void setCenzbse(float cenzbse) {
		this.cenzbse = cenzbse;
	}

	public String getCizav() {
		return this.cizav;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public String getMatnr() {
		return this.matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMe() {
		return this.me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getMeSap() {
		return this.meSap;
	}

	public void setMeSap(String meSap) {
		this.meSap = meSap;
	}

	public String getSapDispo() {
		return this.sapDispo;
	}

	public void setSapDispo(String sapDispo) {
		this.sapDispo = sapDispo;
	}

	public String getSapEkgrp() {
		return this.sapEkgrp;
	}

	public void setSapEkgrp(String sapEkgrp) {
		this.sapEkgrp = sapEkgrp;
	}

	public String getSapKlic() {
		return this.sapKlic;
	}

	public void setSapKlic(String sapKlic) {
		this.sapKlic = sapKlic;
	}

	public String getSapLifnr() {
		return this.sapLifnr;
	}

	public void setSapLifnr(String sapLifnr) {
		this.sapLifnr = sapLifnr;
	}

	public String getSapMeins() {
		return this.sapMeins;
	}

	public void setSapMeins(String sapMeins) {
		this.sapMeins = sapMeins;
	}

	public String getSapMena() {
		return this.sapMena;
	}

	public void setSapMena(String sapMena) {
		this.sapMena = sapMena;
	}

	public String getSapName1() {
		return this.sapName1;
	}

	public void setSapName1(String sapName1) {
		this.sapName1 = sapName1;
	}

	public String getSapNazev() {
		return this.sapNazev;
	}

	public void setSapNazev(String sapNazev) {
		this.sapNazev = sapNazev;
	}

	public int getSapPeinh() {
		return this.sapPeinh;
	}

	public void setSapPeinh(int sapPeinh) {
		this.sapPeinh = sapPeinh;
	}

	public int getSapPocrs() {
		return this.sapPocrs;
	}

	public void setSapPocrs(int sapPocrs) {
		this.sapPocrs = sapPocrs;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}