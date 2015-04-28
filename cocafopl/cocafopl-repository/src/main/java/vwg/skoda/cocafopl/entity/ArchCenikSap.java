package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GZ40T_CENIK_SAP database table.
 * 
 */
@Entity
@Table(name="GZ40T_CENIK", schema="COCAFOPPL_ARCH")
public class ArchCenikSap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="GZ40T_CENIK_SAP_ID_GENERATOR", sequenceName="COCAFOPPL_ARCH.HIBERNATE_SEQUENCE")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GZ40T_CENIK_SAP_ID_GENERATOR")
	private long id;

	private String cdilu;

	private BigDecimal ceniak;

	private BigDecimal ceniake;

	private BigDecimal cenias;

	private BigDecimal ceniase;

	private BigDecimal cenibk;

	private BigDecimal cenibke;

	private BigDecimal cenibs;

	private BigDecimal cenibse;

	private BigDecimal cenmak;

	private BigDecimal cenmake;

	private BigDecimal cenmas;

	private BigDecimal cenmase;

	private BigDecimal cenmbk;

	private BigDecimal cenmbke;

	private BigDecimal cenmbs;

	private BigDecimal cenmbse;

	private BigDecimal censak;

	private BigDecimal censake;

	private BigDecimal censas;

	private BigDecimal censase;

	private BigDecimal censbk;

	private BigDecimal censbke;

	private BigDecimal censbs;

	private BigDecimal censbse;

	private BigDecimal cenzak;

	private BigDecimal cenzake;

	private BigDecimal cenzas;

	private BigDecimal cenzase;

	private BigDecimal cenzbk;

	private BigDecimal cenzbke;

	private BigDecimal cenzbs;

	private BigDecimal cenzbse;

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
	private BigDecimal sapPeinh;

	@Column(name="SAP_POCRS")
	private BigDecimal sapPocrs;

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;

	//bi-directional many-to-one association to ArchKalkulace
	@ManyToOne
	@JoinColumn(name="KALKULACE")
	private ArchKalkulace gz40tKalkulace;

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

	public BigDecimal getCeniak() {
		return this.ceniak;
	}

	public void setCeniak(BigDecimal ceniak) {
		this.ceniak = ceniak;
	}

	public BigDecimal getCeniake() {
		return this.ceniake;
	}

	public void setCeniake(BigDecimal ceniake) {
		this.ceniake = ceniake;
	}

	public BigDecimal getCenias() {
		return this.cenias;
	}

	public void setCenias(BigDecimal cenias) {
		this.cenias = cenias;
	}

	public BigDecimal getCeniase() {
		return this.ceniase;
	}

	public void setCeniase(BigDecimal ceniase) {
		this.ceniase = ceniase;
	}

	public BigDecimal getCenibk() {
		return this.cenibk;
	}

	public void setCenibk(BigDecimal cenibk) {
		this.cenibk = cenibk;
	}

	public BigDecimal getCenibke() {
		return this.cenibke;
	}

	public void setCenibke(BigDecimal cenibke) {
		this.cenibke = cenibke;
	}

	public BigDecimal getCenibs() {
		return this.cenibs;
	}

	public void setCenibs(BigDecimal cenibs) {
		this.cenibs = cenibs;
	}

	public BigDecimal getCenibse() {
		return this.cenibse;
	}

	public void setCenibse(BigDecimal cenibse) {
		this.cenibse = cenibse;
	}

	public BigDecimal getCenmak() {
		return this.cenmak;
	}

	public void setCenmak(BigDecimal cenmak) {
		this.cenmak = cenmak;
	}

	public BigDecimal getCenmake() {
		return this.cenmake;
	}

	public void setCenmake(BigDecimal cenmake) {
		this.cenmake = cenmake;
	}

	public BigDecimal getCenmas() {
		return this.cenmas;
	}

	public void setCenmas(BigDecimal cenmas) {
		this.cenmas = cenmas;
	}

	public BigDecimal getCenmase() {
		return this.cenmase;
	}

	public void setCenmase(BigDecimal cenmase) {
		this.cenmase = cenmase;
	}

	public BigDecimal getCenmbk() {
		return this.cenmbk;
	}

	public void setCenmbk(BigDecimal cenmbk) {
		this.cenmbk = cenmbk;
	}

	public BigDecimal getCenmbke() {
		return this.cenmbke;
	}

	public void setCenmbke(BigDecimal cenmbke) {
		this.cenmbke = cenmbke;
	}

	public BigDecimal getCenmbs() {
		return this.cenmbs;
	}

	public void setCenmbs(BigDecimal cenmbs) {
		this.cenmbs = cenmbs;
	}

	public BigDecimal getCenmbse() {
		return this.cenmbse;
	}

	public void setCenmbse(BigDecimal cenmbse) {
		this.cenmbse = cenmbse;
	}

	public BigDecimal getCensak() {
		return this.censak;
	}

	public void setCensak(BigDecimal censak) {
		this.censak = censak;
	}

	public BigDecimal getCensake() {
		return this.censake;
	}

	public void setCensake(BigDecimal censake) {
		this.censake = censake;
	}

	public BigDecimal getCensas() {
		return this.censas;
	}

	public void setCensas(BigDecimal censas) {
		this.censas = censas;
	}

	public BigDecimal getCensase() {
		return this.censase;
	}

	public void setCensase(BigDecimal censase) {
		this.censase = censase;
	}

	public BigDecimal getCensbk() {
		return this.censbk;
	}

	public void setCensbk(BigDecimal censbk) {
		this.censbk = censbk;
	}

	public BigDecimal getCensbke() {
		return this.censbke;
	}

	public void setCensbke(BigDecimal censbke) {
		this.censbke = censbke;
	}

	public BigDecimal getCensbs() {
		return this.censbs;
	}

	public void setCensbs(BigDecimal censbs) {
		this.censbs = censbs;
	}

	public BigDecimal getCensbse() {
		return this.censbse;
	}

	public void setCensbse(BigDecimal censbse) {
		this.censbse = censbse;
	}

	public BigDecimal getCenzak() {
		return this.cenzak;
	}

	public void setCenzak(BigDecimal cenzak) {
		this.cenzak = cenzak;
	}

	public BigDecimal getCenzake() {
		return this.cenzake;
	}

	public void setCenzake(BigDecimal cenzake) {
		this.cenzake = cenzake;
	}

	public BigDecimal getCenzas() {
		return this.cenzas;
	}

	public void setCenzas(BigDecimal cenzas) {
		this.cenzas = cenzas;
	}

	public BigDecimal getCenzase() {
		return this.cenzase;
	}

	public void setCenzase(BigDecimal cenzase) {
		this.cenzase = cenzase;
	}

	public BigDecimal getCenzbk() {
		return this.cenzbk;
	}

	public void setCenzbk(BigDecimal cenzbk) {
		this.cenzbk = cenzbk;
	}

	public BigDecimal getCenzbke() {
		return this.cenzbke;
	}

	public void setCenzbke(BigDecimal cenzbke) {
		this.cenzbke = cenzbke;
	}

	public BigDecimal getCenzbs() {
		return this.cenzbs;
	}

	public void setCenzbs(BigDecimal cenzbs) {
		this.cenzbs = cenzbs;
	}

	public BigDecimal getCenzbse() {
		return this.cenzbse;
	}

	public void setCenzbse(BigDecimal cenzbse) {
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

	public BigDecimal getSapPeinh() {
		return this.sapPeinh;
	}

	public void setSapPeinh(BigDecimal sapPeinh) {
		this.sapPeinh = sapPeinh;
	}

	public BigDecimal getSapPocrs() {
		return this.sapPocrs;
	}

	public void setSapPocrs(BigDecimal sapPocrs) {
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

	public ArchKalkulace getGz40tKalkulace() {
		return this.gz40tKalkulace;
	}

	public void setGz40tKalkulace(ArchKalkulace gz40tKalkulace) {
		this.gz40tKalkulace = gz40tKalkulace;
	}

}