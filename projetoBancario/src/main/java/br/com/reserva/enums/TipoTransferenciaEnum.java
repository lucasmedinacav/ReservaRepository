package br.com.reserva.enums;

public enum TipoTransferenciaEnum {
	A(new Short("1")), B(new Short("2")), C(new Short("3")), D(new Short("4"));

	private Short tipo;

	TipoTransferenciaEnum(Short tipo) {
		this.tipo = tipo;
	}

	public Short getTipo() {
		return tipo;
	}
}