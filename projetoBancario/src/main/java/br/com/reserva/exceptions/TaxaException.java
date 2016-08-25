package br.com.reserva.exceptions;

public class TaxaException extends Exception {

	private static final long serialVersionUID = 1L;
	private String msg;

	public TaxaException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}
}
