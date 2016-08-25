package br.com.reserva.enums;

public enum FaixasTransfTipoCEnum {
	FAIXA_MIN(0.083, 0, 5), 
	FAIXA_2(0.074, 5, 10), 
	FAIXA_3(0.067, 10, 15), 
	FAIXA_4(0.054, 15, 20),
	FAIXA_5(0.043, 20, 25),
	FAIXA_6(0.021, 25, 30),
	FAIXA_MAX(0.012, 30, null);

	private double taxa;
	private Integer diasMin;
	private Integer diasMax;

	FaixasTransfTipoCEnum(double taxa, Integer diasMin, Integer diasMax) {
		this.taxa = taxa;
		this.diasMin = diasMin;
		this.diasMax = diasMax;
	}

	public double getTaxa() {
		return taxa;
	}

	public Integer getDiasMin() {
		return diasMin;
	}

	public Integer getDiasMax() {
		return diasMax;
	}
}