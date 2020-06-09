package Model.Beans;

import java.io.Serializable;

public class CartBean implements Serializable {	
	private static final long serialVersionUID = 8805874641121048806L;
	
	private long idUser;
	private ProductBean prodotto;
	private int quantit�;
	
	public ProductBean getProdotto() {
		return prodotto;
	}
	public void setProdotto(ProductBean prodotto) {
		this.prodotto = prodotto;
	}
	public int getQuantit�() {
		return quantit�;
	}
	public void setQuantit�(int quantit�) {
		this.quantit� = quantit�;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
}
