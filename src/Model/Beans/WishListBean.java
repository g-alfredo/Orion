package Model.Beans;

import java.io.Serializable;

public class WishListBean implements Serializable {
	private static final long serialVersionUID = -5913537635861618805L;
	
	private long idUser;
	private ProductBean prodotto;
	
	public ProductBean getProdotto() {
		return prodotto;
	}
	public void setProdotto(ProductBean prodotto) {
		this.prodotto = prodotto;
	}
	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
}
