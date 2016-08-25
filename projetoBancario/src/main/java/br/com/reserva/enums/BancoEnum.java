package br.com.reserva.enums;

public enum BancoEnum {
	ITAU(123, "Itau"), SANTANDER(222, "Santander"), CAIXA(433, "Caixa"), BRADESCO(289, "Bradesco");

	private Integer cod;
	private String nome;

	BancoEnum(Integer cod, String nome) {
		this.cod = cod;
		this.nome = nome;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}