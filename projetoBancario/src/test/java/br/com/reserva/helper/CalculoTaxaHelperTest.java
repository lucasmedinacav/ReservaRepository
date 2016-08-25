package br.com.reserva.helper;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.reserva.enums.FaixasTransfTipoCEnum;
import br.com.reserva.enums.TipoTransferenciaEnum;
import br.com.reserva.exceptions.TaxaException;
import br.com.reserva.util.Constantes;
import br.com.reserva.vo.TransferenciaVO;

@RunWith(MockitoJUnitRunner.class)
public class CalculoTaxaHelperTest {
	@InjectMocks
	private CalculoTaxaHelper calculoTaxaHelper;
	private TransferenciaVO mockTransferencia;
	Calendar c = Calendar.getInstance();

	@Before
	public void init() {
		mockTransferencia = new TransferenciaVO();
		c.setTime(new Date());
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaComValorTransferidoNulo() throws TaxaException {
		mockTransferencia.setValorTransferido(null);
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaComTipoTransferenciaNulo() throws TaxaException {
		mockTransferencia.setTipo(null);
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaComTipoTransferenciaDiferenteDosExistentes() throws TaxaException {
		mockTransferencia.setTipo(new Short("5"));
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaTransTipoBComDtVazia() throws TaxaException {
		mockTransferencia.setDataAgendada(null);
		mockTransferencia.setTipo(TipoTransferenciaEnum.B.getTipo());
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaTransTipoCComDtVazia() throws TaxaException {
		mockTransferencia.setDataAgendada(null);
		mockTransferencia.setTipo(TipoTransferenciaEnum.C.getTipo());
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test(expected = TaxaException.class)
	public void realizaCalculoTaxaTransTipoAComValorVazio() throws TaxaException {
		mockTransferencia.setDataAgendada(new Date());
		mockTransferencia.setValorTransferido(null);
		mockTransferencia.setTipo(TipoTransferenciaEnum.A.getTipo());
		calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
	}

	@Test
	public void realizaCalculoTaxaTransTipoAComValorPreenchido() throws TaxaException {
		mockTransferencia.setValorTransferido(10.0);
		mockTransferencia.setTipo(TipoTransferenciaEnum.A.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(2.3, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoBComDataAteTrintaDias() throws TaxaException {
		c.add(Calendar.DATE, +1);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(10.0);
		mockTransferencia.setTipo(TipoTransferenciaEnum.B.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(10.0, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoBComDataMaisQueTrintaDias() throws TaxaException {
		c.add(Calendar.DATE, +31);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(10.0);
		mockTransferencia.setTipo(TipoTransferenciaEnum.B.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(8.0, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDAComValorPreenchido() throws TaxaException {
		mockTransferencia.setValorTransferido(Constantes.faixaMenorTransfD - 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		double regraA = mockTransferencia.getValorTransferido() * Constantes.percentualTransfA
				+ Constantes.taxaFixaTransfA;
		Assert.assertEquals(regraA, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDBComDataAteTrintaDias() throws TaxaException {
		c.add(Calendar.DATE, +1);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(Constantes.faixaMaiorTransfD - 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(Constantes.taxaMaiorTransfB, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDBComDataAtual() throws TaxaException {
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(Constantes.faixaMaiorTransfD - 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(Constantes.taxaMaiorTransfB, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDBComDataMaisQueTrintaDias() throws TaxaException {
		c.add(Calendar.DATE, +31);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(Constantes.faixaMaiorTransfD - 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertEquals(Constantes.taxaMenorTransfB, retorno, 0);
	}

	@Test
	public void realizaCalculoTaxaTransTipoCComAgendamentoMaiorQueAFaixaMaxima() throws TaxaException {
		c.add(Calendar.DATE, +(FaixasTransfTipoCEnum.FAIXA_MAX.getDiasMin()+1));
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(100.0);
		mockTransferencia.setTipo(TipoTransferenciaEnum.C.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertNotNull(retorno);
	}
	
	@Test
	public void realizaCalculoTaxaTransTipoCComAgendamentoMenorQueAFaixaMaxima() throws TaxaException {
		int dias = FaixasTransfTipoCEnum.FAIXA_MAX.getDiasMin()-1;
		c.add(Calendar.DATE, +dias);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(100.0);
		mockTransferencia.setTipo(TipoTransferenciaEnum.C.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertNotNull(retorno);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDCComValorPreenchidoeDataPreenchida() throws TaxaException {
		c.add(Calendar.DATE, +31);
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(Constantes.faixaMaiorTransfD + 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertNotNull(retorno);
	}

	@Test
	public void realizaCalculoTaxaTransTipoDCComValorPreenchidoeDataAtual() throws TaxaException {
		mockTransferencia.setDataAgendada(c.getTime());
		mockTransferencia.setValorTransferido(Constantes.faixaMaiorTransfD + 1);
		mockTransferencia.setTipo(TipoTransferenciaEnum.D.getTipo());
		double retorno = calculoTaxaHelper.realizaCalculoTaxa(mockTransferencia);
		Assert.assertNotNull(retorno);
	}
}