package Model.Beans;

import java.sql.Date;
import java.util.ArrayList;

public class OrderBean {
	private int IDOrdine;
	private long IDUser;
	private int nrIndirizzo;
	private String nrCarta;
	private Date dataOrdine;
	private double costoSpedizione;
	private ArrayList<OrderedProductBean> prodottiOrdinati;
	
	public int getIDOrdine() {
		return IDOrdine;
	}
	public void setIDOrdine(int iDOrdine) {
		IDOrdine = iDOrdine;
	}
	
	public long getIDUser() {
		return IDUser;
	}

	public void setIDUser(long iDUser) {
		IDUser = iDUser;
	}

	public int getNrIndirizzo() {
		return nrIndirizzo;
	}
	public void setNrIndirizzo(int nrIndirizzo) {
		this.nrIndirizzo = nrIndirizzo;
	}
	public String getNrCarta() {
		return nrCarta;
	}
	public void setNrCarta(String nrCarta) {
		this.nrCarta = nrCarta;
	}
	public Date getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public double getCostoSpedizione() {
		return costoSpedizione;
	}
	public void setCostoSpedizione(double costoSpedizione) {
		this.costoSpedizione = costoSpedizione;
	}

	public ArrayList<OrderedProductBean> getProdottiOrdinati() {
		return prodottiOrdinati;
	}

	public void setProdottiOrdinati(ArrayList<OrderedProductBean> prodottiOrdinati) {
		this.prodottiOrdinati = prodottiOrdinati;
	}

	
	
}