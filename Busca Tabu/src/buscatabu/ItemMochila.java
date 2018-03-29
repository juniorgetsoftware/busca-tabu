package buscatabu;

public class ItemMochila {

	private String nome;
	private double peso;
	private double benefincio;

	public ItemMochila() {
		super();
	}

	public ItemMochila(String nome, double peso, double benefincio) {
		super();
		this.nome = nome;
		this.peso = peso;
		this.benefincio = benefincio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getBenefincio() {
		return benefincio;
	}

	public void setBenefincio(double benefincio) {
		this.benefincio = benefincio;
	}

}
