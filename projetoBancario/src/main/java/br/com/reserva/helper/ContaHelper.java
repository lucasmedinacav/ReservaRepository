package br.com.reserva.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.reserva.enums.BancoEnum;
import br.com.reserva.vo.ContaVO;

public class ContaHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ContaVO> contas;

	public ContaHelper() {
		contas = new ArrayList<>();

		ContaVO conta1 = new ContaVO();
		conta1.setAgencia(1111);
		conta1.setBanco(BancoEnum.ITAU.getCod());
		conta1.setCodCliente((short) 1);
		conta1.setDataAbertura(new Date());
		conta1.setDataEncerramento(null);
		conta1.setSaldo(10000.00);
		conta1.setSenha(111111);
		conta1.setNumero(11111111);

		ContaVO conta2 = new ContaVO();
		conta2.setAgencia(2222);
		conta2.setBanco(BancoEnum.ITAU.getCod());
		conta2.setCodCliente((short) 1);
		conta2.setDataAbertura(new Date());
		conta2.setDataEncerramento(null);
		conta2.setSaldo(20000.00);
		conta2.setSenha(222222);
		conta2.setNumero(22222222);

		ContaVO conta3 = new ContaVO();
		conta3.setAgencia(3333);
		conta3.setBanco(BancoEnum.ITAU.getCod());
		conta3.setCodCliente((short) 1);
		conta3.setDataAbertura(new Date());
		conta3.setDataEncerramento(null);
		conta3.setSaldo(30000.00);
		conta3.setSenha(333333);
		conta3.setNumero(33333333);

		ContaVO conta4 = new ContaVO();
		conta4.setAgencia(4444);
		conta4.setBanco(BancoEnum.ITAU.getCod());
		conta4.setCodCliente((short) 2);
		conta4.setDataAbertura(new Date());
		conta4.setDataEncerramento(null);
		conta4.setSaldo(40000.00);
		conta4.setSenha(444444);
		conta4.setNumero(44444444);

		ContaVO conta5 = new ContaVO();
		conta5.setAgencia(5555);
		conta5.setBanco(BancoEnum.ITAU.getCod());
		conta5.setCodCliente((short) 3);
		conta5.setDataAbertura(new Date());
		conta5.setDataEncerramento(null);
		conta5.setSaldo(50000.00);
		conta5.setSenha(555555);
		conta5.setNumero(55555555);

		ContaVO conta6 = new ContaVO();
		conta6.setAgencia(6666);
		conta6.setBanco(BancoEnum.ITAU.getCod());
		conta6.setCodCliente((short) 4);
		conta6.setDataAbertura(new Date());
		conta6.setDataEncerramento(null);
		conta6.setSaldo(60000.00);
		conta6.setSenha(666666);
		conta6.setNumero(66666666);

		ContaVO conta7 = new ContaVO();
		conta7.setAgencia(7777);
		conta7.setBanco(BancoEnum.ITAU.getCod());
		conta7.setCodCliente((short) 4);
		conta7.setDataAbertura(new Date());
		conta7.setDataEncerramento(null);
		conta7.setSaldo(70000.00);
		conta7.setSenha(777777);
		conta7.setNumero(77777777);

		ContaVO conta8 = new ContaVO();
		conta8.setAgencia(8888);
		conta8.setBanco(BancoEnum.ITAU.getCod());
		conta8.setCodCliente((short) 5);
		conta8.setDataAbertura(new Date());
		conta8.setDataEncerramento(null);
		conta8.setSaldo(80000.00);
		conta8.setSenha(888888);
		conta8.setNumero(88888888);

		getContas().add(conta1);
		getContas().add(conta2);
		getContas().add(conta3);
		getContas().add(conta4);
		getContas().add(conta5);
		getContas().add(conta6);
		getContas().add(conta7);
		getContas().add(conta8);
	}

	public List<ContaVO> getListaContasCliente(Short codCli) {
		List<ContaVO> listContasCli = new ArrayList<>();
		for (ContaVO conta : getContas()) {
			if (conta.getCodCliente().equals(codCli)) {
				listContasCli.add(conta);
			}
		}
		return listContasCli;
	}

	public List<ContaVO> getContas() {
		return contas;
	}

	public void setContas(List<ContaVO> contas) {
		this.contas = contas;
	}
}