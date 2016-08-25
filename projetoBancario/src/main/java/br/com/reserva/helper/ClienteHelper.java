package br.com.reserva.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.reserva.vo.ClienteVO;

public class ClienteHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ClienteVO> clientes;

	public ClienteHelper() {
		clientes = new ArrayList<>();
		ClienteVO cli1 = new ClienteVO();
		cli1.setCod((short) 1);
		cli1.setCpfCnpj(111111111L);
		cli1.setEmail("email_cli1@teste.com");
		cli1.setEndereco("Rua do Cliente 1 num 111 ap 11");
		cli1.setNome("Cliente 1");
		ClienteVO cli2 = new ClienteVO();
		cli2.setCod((short) 2);
		cli2.setCpfCnpj(222222222L);
		cli2.setEmail("email_cli2@teste.com");
		cli2.setEndereco("Rua do Cliente 2 num 222 ap 22");
		cli2.setNome("Cliente 2");
		ClienteVO cli3 = new ClienteVO();
		cli3.setCod((short) 3);
		cli3.setCpfCnpj(333333333L);
		cli3.setEmail("email_cli3@teste.com");
		cli3.setEndereco("Rua do Cliente 3 num 333 ap 33");
		cli3.setNome("Cliente 3");
		ClienteVO cli4 = new ClienteVO();
		cli4.setCod((short) 4);
		cli4.setCpfCnpj(444444444L);
		cli4.setEmail("email_cli4@teste.com");
		cli4.setEndereco("Rua do Cliente 4 num 444 ap 44");
		cli4.setNome("Cliente 4");
		ClienteVO cli5 = new ClienteVO();
		cli5.setCod((short) 5);
		cli5.setCpfCnpj(555555555L);
		cli5.setEmail("email_cli5@teste.com");
		cli5.setEndereco("Rua do Cliente 5 num 555 ap 55");
		cli5.setNome("Cliente 5");
		clientes.add(cli1);
		clientes.add(cli2);
		clientes.add(cli3);
		clientes.add(cli4);
		clientes.add(cli5);
	}

	public List<ClienteVO> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteVO> clientes) {
		this.clientes = clientes;
	}

	public List<ClienteVO> buscarClientes() {
		return getClientes();
	}

	public List<ClienteVO> buscarClientesDestino(Short cod) {
		List<ClienteVO> listClientesDestino = new ArrayList<>();
		for (ClienteVO cli : getClientes()) {
			if (!cli.getCod().equals(cod)) {
				listClientesDestino.add(cli);
			}
		}
		return listClientesDestino;
	}
}