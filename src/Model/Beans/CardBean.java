package Model.Beans;

public class CardBean {
	private long idUser;
	private String nrCarta;
	private String nome;
	private String cognome;
	private boolean preferred;
	
	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
	
	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getNrCarta() {
		return nrCarta;
	}
	public void setNrCarta(String nrCarta) {
		this.nrCarta = nrCarta;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}
