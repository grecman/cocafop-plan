package vwg.skoda.cocafopl.obj;

import java.util.Date;

public class OfflineObj {

	String user;
	Date casZadani;
	Date casSpusteni;
	Date casUkonceni;
	String popis;
	String status;
	
	
	public String getUser() {
		return user;
	}
	public Date getCasZadani() {
		return casZadani;
	}
	public Date getCasSpusteni() {
		return casSpusteni;
	}
	public Date getCasUkonceni() {
		return casUkonceni;
	}
	public String getPopis() {
		return popis;
	}
	public String getStatus() {
		return status;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setCasZadani(Date casZadani) {
		this.casZadani = casZadani;
	}
	public void setCasSpusteni(Date casSpusteni) {
		this.casSpusteni = casSpusteni;
	}
	public void setCasUkonceni(Date casUkonceni) {
		this.casUkonceni = casUkonceni;
	}
	public void setPopis(String popis) {
		this.popis = popis;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
		
	
}
