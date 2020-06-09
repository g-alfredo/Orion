package Model.Beans;

public class OrderedProductBean {
	private int IDOrdine;
	private long IDProdotto;
	private int quantit�;
	private double prezzo;
	private String titolo;
	private ProductBean prodotto;
	
	public ProductBean getProdotto() {
		return prodotto;
	}

	public void setProdotto(ProductBean prodotto) {
		this.prodotto = prodotto;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}	
	public int getIDOrdine() {
		return IDOrdine;
	}
	public void setIDOrdine(int iDOrdine) {
		IDOrdine = iDOrdine;
	}
	public long getIDProdotto() {
		return IDProdotto;
	}
	public void setIDProdotto(long IDProdotto) {
		this.IDProdotto = IDProdotto;
	}
	public int getQuantit�() {
		return quantit�;
	}
	public void setQuantit�(int quantit�) {
		this.quantit� = quantit�;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
}
