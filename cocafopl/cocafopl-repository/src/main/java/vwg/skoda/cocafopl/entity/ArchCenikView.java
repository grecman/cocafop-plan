package vwg.skoda.cocafopl.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name="GZ40V_CENIK_ALL", schema="COCAFOPPL_ARCH")
public class ArchCenikView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cdilu;

	@Column(name="CENA_IA_CZK")
	private BigDecimal cenaIaCzk;

	@Column(name="CENA_IA_EUR")
	private BigDecimal cenaIaEur;

	@Column(name="CENA_IB_CZK")
	private BigDecimal cenaIbCzk;

	@Column(name="CENA_IB_EUR")
	private BigDecimal cenaIbEur;

	@Column(name="CENA_MA")
	private BigDecimal cenaMa;

	@Column(name="CENA_MB")
	private BigDecimal cenaMb;

	@Column(name="CENA_ORIG_LOCAL")
	private BigDecimal cenaOrigLocal;

	@Column(name="CENA_SA_CZK")
	private BigDecimal cenaSaCzk;

	@Column(name="CENA_SA_EUR")
	private BigDecimal cenaSaEur;

	@Column(name="CENA_SB_CZK")
	private BigDecimal cenaSbCzk;

	@Column(name="CENA_SB_EUR")
	private BigDecimal cenaSbEur;

	@Column(name="CISLO_DODAVATELE")
	private String cisloDodavatele;

	private String cizav;

	private String dodavatel;

	@Id
	private BigDecimal id;

	private String me;

	private String mena;

	private String nazev;

	private String pred;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public ArchCenikView() {
	}

	public String getCdilu() {
		return this.cdilu;
	}

	public void setCdilu(String cdilu) {
		this.cdilu = cdilu;
	}

	public BigDecimal getCenaIaCzk() {
		return this.cenaIaCzk;
	}

	public void setCenaIaCzk(BigDecimal cenaIaCzk) {
		this.cenaIaCzk = cenaIaCzk;
	}

	public BigDecimal getCenaIaEur() {
		return this.cenaIaEur;
	}

	public void setCenaIaEur(BigDecimal cenaIaEur) {
		this.cenaIaEur = cenaIaEur;
	}

	public BigDecimal getCenaIbCzk() {
		return this.cenaIbCzk;
	}

	public void setCenaIbCzk(BigDecimal cenaIbCzk) {
		this.cenaIbCzk = cenaIbCzk;
	}

	public BigDecimal getCenaIbEur() {
		return this.cenaIbEur;
	}

	public void setCenaIbEur(BigDecimal cenaIbEur) {
		this.cenaIbEur = cenaIbEur;
	}

	public BigDecimal getCenaMa() {
		return this.cenaMa;
	}

	public void setCenaMa(BigDecimal cenaMa) {
		this.cenaMa = cenaMa;
	}

	public BigDecimal getCenaMb() {
		return this.cenaMb;
	}

	public void setCenaMb(BigDecimal cenaMb) {
		this.cenaMb = cenaMb;
	}

	public BigDecimal getCenaOrigLocal() {
		return this.cenaOrigLocal;
	}

	public void setCenaOrigLocal(BigDecimal cenaOrigLocal) {
		this.cenaOrigLocal = cenaOrigLocal;
	}

	public BigDecimal getCenaSaCzk() {
		return this.cenaSaCzk;
	}

	public void setCenaSaCzk(BigDecimal cenaSaCzk) {
		this.cenaSaCzk = cenaSaCzk;
	}

	public BigDecimal getCenaSaEur() {
		return this.cenaSaEur;
	}

	public void setCenaSaEur(BigDecimal cenaSaEur) {
		this.cenaSaEur = cenaSaEur;
	}

	public BigDecimal getCenaSbCzk() {
		return this.cenaSbCzk;
	}

	public void setCenaSbCzk(BigDecimal cenaSbCzk) {
		this.cenaSbCzk = cenaSbCzk;
	}

	public BigDecimal getCenaSbEur() {
		return this.cenaSbEur;
	}

	public void setCenaSbEur(BigDecimal cenaSbEur) {
		this.cenaSbEur = cenaSbEur;
	}

	public String getCisloDodavatele() {
		return this.cisloDodavatele;
	}

	public void setCisloDodavatele(String cisloDodavatele) {
		this.cisloDodavatele = cisloDodavatele;
	}

	public String getCizav() {
		return this.cizav;
	}

	public void setCizav(String cizav) {
		this.cizav = cizav;
	}

	public String getDodavatel() {
		return this.dodavatel;
	}

	public void setDodavatel(String dodavatel) {
		this.dodavatel = dodavatel;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getMe() {
		return this.me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	public String getMena() {
		return this.mena;
	}

	public void setMena(String mena) {
		this.mena = mena;
	}

	public String getNazev() {
		return this.nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public String getPred() {
		return this.pred;
	}

	public void setPred(String pred) {
		this.pred = pred;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}