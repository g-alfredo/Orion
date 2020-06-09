package Model.Beans;

public class ShippingAddressBean {
	private int numIndirizzo;
	private long IDUser;
	private boolean preferred;
	private String provincia;
	private String città;
	private int cap;
	private String via;
	private int civico;
	
	
	public boolean isPreferred() {
		return preferred;
	}
	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCittà() {
		return città;
	}
	public void setCittà(String città) {
		this.città = città;
	}
	public int getCap() {
		return cap;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public int getCivico() {
		return civico;
	}
	public void setCivico(int civico) {
		this.civico = civico;
	}
	public int getNumIndirizzo() {
	  return numIndirizzo;
	}
	public void setNumIndirizzo(int numIndirizzo) {
	  this.numIndirizzo = numIndirizzo;
	}
	public long getIDUser() {
	  return IDUser;
	}
	public void setIDUser(long IDUser) {
	  this.IDUser = IDUser;
	}
}