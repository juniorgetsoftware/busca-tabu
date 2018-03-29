package buscatabu;

public class ItemMochila {

	private String nome;
	private double peso;
	private double beneficio;

	public ItemMochila() {
		super();
	}

	public ItemMochila(String nome, double peso, double beneficio) {
		super();
		this.nome = nome;
		this.peso = peso;
		this.beneficio = beneficio;
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

	public double getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(double beneficio) {
		this.beneficio = beneficio;
	}

}
