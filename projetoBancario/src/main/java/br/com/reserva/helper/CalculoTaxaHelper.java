package br.com.reserva.helper;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.reserva.enums.FaixasTransfTipoCEnum;
import br.com.reserva.enums.TipoTransferenciaEnum;
import br.com.reserva.exceptions.TaxaException;
import br.com.reserva.util.Constantes;
import br.com.reserva.util.DateUtil;
import br.com.reserva.vo.TransferenciaVO;

public class CalculoTaxaHelper {

	public Double realizaCalculoTaxa(TransferenciaVO transferencia) throws TaxaException {
		Double taxa = 0.0;
		Short tipoTransf = transferencia.getTipo();
		if (transferencia.getValorTransferido() == 0) {
			throw new TaxaException("O necessário um valor positivo para realizar a transferência");
		}
		validaTipoTransferencia(tipoTransf);
		if (TipoTransferenciaEnum.D.getTipo().equals(tipoTransf)) {
			tipoTransf = verificarTransferenciaTipoD(transferencia.getValorTransferido());
		}
		if (TipoTransferenciaEnum.A.getTipo().equals(tipoTransf)) {
			taxa = calcularTaxaA(transferencia.getValorTransferido());
		} else if (TipoTransferenciaEnum.B.getTipo().equals(tipoTransf)) {
			taxa = calcularTaxaB(transferencia.getDataAgendada());
		} else if (TipoTransferenciaEnum.C.getTipo().equals(tipoTransf)) {
			taxa = calcularTaxaC(transferencia.getDataAgendada(), transferencia.getValorTransferido());
		}
		return taxa;

	}

	private Double calcularTaxaA(Double valor) {
		return (valor * Constantes.percentualTransfA) + Constantes.taxaFixaTransfA;
	}

	private Double calcularTaxaB(Date dataAgendada) throws TaxaException {
		try {
			validaData(dataAgendada);
			if (DateUtil.getDiferencaDiasEntreDuasDatas(new Date(), dataAgendada) <= 30) {
				return Constantes.taxaMaiorTransfB;
			} else {
				return Constantes.taxaMenorTransfB;
			}
		} catch (TaxaException erroTaxa) {
			throw erroTaxa;
		}

	}

	private Double calcularTaxaC(Date dataAgendada, Double valor) throws TaxaException {
		validaData(dataAgendada);
		int qtdDias = DateUtil.getDiferencaDiasEntreDuasDatas(new Date(), dataAgendada);
		List<FaixasTransfTipoCEnum> faixasTransfC = Arrays.asList(FaixasTransfTipoCEnum.values());
		if (qtdDias == 0) {
			return valor * FaixasTransfTipoCEnum.FAIXA_MIN.getTaxa();
		}
		for (FaixasTransfTipoCEnum faixa : faixasTransfC) {
			if (FaixasTransfTipoCEnum.FAIXA_MAX == faixa && faixa.getDiasMin() < qtdDias) {
				return valor * faixa.getTaxa();
			} else if (FaixasTransfTipoCEnum.FAIXA_MAX != faixa) {
				if (faixa.getDiasMin() < qtdDias && faixa.getDiasMax() >= qtdDias) {
					return valor * faixa.getTaxa();
				}
			}
		}
		return null;
	}

	private Short verificarTransferenciaTipoD(double valor) {
		if (valor <= Constantes.faixaMenorTransfD) {
			return TipoTransferenciaEnum.A.getTipo();
		} else if (valor > Constantes.faixaMenorTransfD && valor <= Constantes.faixaMaiorTransfD) {
			return TipoTransferenciaEnum.B.getTipo();
		} else if (valor > Constantes.faixaMaiorTransfD) {
			return TipoTransferenciaEnum.C.getTipo();
		}
		return null;

	}

	private void validaData(Date data) throws TaxaException {
		try {
			DateUtil.zerarHoras(data).before(DateUtil.zerarHoras(new Date()));

		} catch (ParseException e) {
			throw new TaxaException("A data não é valida");
		}
	}

	private void validaTipoTransferencia(Short tipoTransf) throws TaxaException {
		boolean erro = true;
		if (tipoTransf != null) {
			List<TipoTransferenciaEnum> tipoTransfEnum = Arrays.asList(TipoTransferenciaEnum.values());
			for (TipoTransferenciaEnum tipoEnum : tipoTransfEnum) {
				if (tipoTransf.equals(tipoEnum.getTipo())) {
					erro = false;
					break;
				}
			}
		}

		if (erro) {
			throw new TaxaException("Tipo de transferência inválido");
		}
	}
}