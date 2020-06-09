package Model.Beans;

import java.io.Serializable;

public class CartBean implements Serializable {	
	private static final long serialVersionUID = 8805874641121048806L;
	
	private long idUser;
	private ProductBean prodotto;
	private int quantità;
	
	public ProductBean getProdotto() {
		return prodotto;
	}
	public void setProdotto(ProductBean prodotto) {
		this.prodotto = prodotto;
	}
	public int getQuantità() {
		return quantità;
	}
	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
}
