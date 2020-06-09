package Model.Beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class ProductBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4112104002093223222L;
	private long idProdotto;
	private String titolo;
	private Date dataPubblicazione;
	private double prezzo;
	private int percSconto;
	private String descrizione;
	private int quantità;
	private ArrayList<ImageBean> immagini;
	private ArrayList<CategoryBean> categorie;
	
	public ArrayList<ImageBean> getImmagini() {
		return immagini;
	}

	public void setImmagini(ArrayList<ImageBean> immagini) {
		this.immagini = immagini;
	}
	
	public ArrayList<CategoryBean> getCategorie() {
		return categorie;
	}

	public void setCategorie(ArrayList<CategoryBean> categorie) {
		this.categorie = categorie;
	}
	
	public long getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(long iDProdotto) {
		this.idProdotto = iDProdotto;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public int getPercSconto() {
		return percSconto;
	}
	public void setPercSconto(int percSconto) {
		this.percSconto = percSconto;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getQuantità() {
		return quantità;
	}
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}
	
}
