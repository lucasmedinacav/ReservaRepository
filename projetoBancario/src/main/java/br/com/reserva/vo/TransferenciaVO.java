package br.com.reserva.vo;

import java.util.Date;

public class TransferenciaVO {
	private Integer contaOrigem;
	private Integer contaDestino;
	private Double valorTransferido;
	private Double taxaCobrada;
	private Date dataAgendada;
	private Short tipo;

	public TransferenciaVO() {
		super();
		this.valorTransferido = 0.0;
	}

	public Integer getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Integer contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Integer getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Integer contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Double getValorTransferido() {
		if (valorTransferido == null) {
			valorTransferido = 0.0;
		}
		return valorTransferido;
	}

	public void setValorTransferido(Double valorTransferido) {
		this.valorTransferido = valorTransferido;
	}

	public Double getTaxaCobrada() {
		return taxaCobrada;
	}

	public void setTaxaCobrada(Double taxaCobrada) {
		this.taxaCobrada = taxaCobrada;
	}

	public Date getDataAgendada() {
		return dataAgendada;
	}

	public void setDataAgendada(Date dataAgendada) {
		this.dataAgendada = dataAgendada;
	}

	public Short getTipo() {
		return tipo;
	}

	public void setTipo(Short tipo) {
		this.tipo = tipo;
	}
}