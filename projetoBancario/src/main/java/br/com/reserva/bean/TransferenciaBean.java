package br.com.reserva.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.reserva.exceptions.TaxaException;
import br.com.reserva.helper.CalculoTaxaHelper;
import br.com.reserva.helper.ClienteHelper;
import br.com.reserva.helper.ContaHelper;
import br.com.reserva.vo.ClienteVO;
import br.com.reserva.vo.ContaVO;
import br.com.reserva.vo.TransferenciaVO;

@ManagedBean
@SessionScoped
public class TransferenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ClienteHelper clienteHelper;
	private ContaHelper contaHelper;
	private List<ClienteVO> listaClientesOrigem;
	private List<ClienteVO> listaClientesDestino;
	private ClienteVO cliOrigemSelec;
	private ClienteVO cliDestinoSelec;
	private ContaVO contaOrigemSelec;
	private ContaVO contaDestinoSelec;
	private List<ContaVO> listaContasOrigem;
	private List<ContaVO> listaContasDestino;
	private String valor;
	private Double valorD;
	private String senha;
	private Date dtAgendamento;
	private Short tipoTransferencia;

	@PostConstruct
	public void init() {
		clienteHelper = new ClienteHelper();
		contaHelper = new ContaHelper();
		listaClientesOrigem = clienteHelper.getClientes();
		cliOrigemSelec = new ClienteVO();
		cliDestinoSelec = new ClienteVO();
		listaClientesDestino = new ArrayList<>();
		contaOrigemSelec = new ContaVO();
		contaDestinoSelec = new ContaVO();
		listaContasOrigem = new ArrayList<>();
		listaContasDestino = new ArrayList<>();
	}

	public void transferir() {
		if (validaCampos()) {
			CalculoTaxaHelper calcHelper = new CalculoTaxaHelper();
			TransferenciaVO transf = new TransferenciaVO();
			transf.setContaDestino(contaDestinoSelec.getNumero());
			transf.setContaOrigem(contaOrigemSelec.getNumero());
			transf.setDataAgendada(dtAgendamento);
			transf.setValorTransferido(valorD);
			transf.setTipo(tipoTransferencia);
			Double taxa;
			try {
				taxa = calcHelper.realizaCalculoTaxa(transf);
				transf.setTaxaCobrada(taxa);

				if (valorD + taxa > contaOrigemSelec.getSaldo()) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!",
									"Você não possui saldo suficiente para transferir o valor de R$" + valorD.toString()
											+ " + a taxa de R$" + taxa.toString()));
				} else {
					contaOrigemSelec.setSaldo(contaOrigemSelec.getSaldo() - (valorD + taxa));
					contaDestinoSelec.setSaldo(contaDestinoSelec.getSaldo() + valorD);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Sucesso!", "Transferência realizada com sucesso! INFO: Valor de taxa cobrado: R$" + taxa));
				}
			} catch (TaxaException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", e.getMessage()));
			}

		}
	}

	private boolean validaCampos() {
		try {
			valorD = Double.parseDouble(valor);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "O valor transferido não é válido"));
			return false;
		}
		if (cliOrigemSelec == null || cliDestinoSelec == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!",
					"É necessário selecionar o cliente de origem e destino"));
			return false;
		} else if (contaDestinoSelec == null || contaOrigemSelec == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!",
					"É necessário selecionar a conta de origem e destino"));
			return false;
		} else if (tipoTransferencia == null || tipoTransferencia == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!",
					"É necessário selecionar um tipo de transferência"));
			return false;
		} else if (valorD == null || valorD <= 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "É necessário preencher algum valor"));
			return false;
		} else if (senha == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "É necessário preencher a senha"));
			return false;
		} else if (senha == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "É necessário preencher a senha"));
			return false;
		} else if (senha.length() < 6) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!",
					"A senha deve ser composta por seis números"));
			return false;
		} else if (senha != null) {
			try {
				Integer senhaInt = Integer.valueOf(senha);
				if (!senhaInt.equals(contaOrigemSelec.getSenha())) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Senha Incorreta"));
					return false;
				}
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "A senha deve ser númerica"));
				return false;
			}
		}
		return true;
	}

	public void gerarListaDestino() {
		if (cliOrigemSelec.getCod() != null) {
			setListaClientesDestino(clienteHelper.buscarClientesDestino(cliOrigemSelec.getCod()));
			listaContasOrigem = contaHelper.getListaContasCliente(cliOrigemSelec.getCod());
		}
	}

	public void obterContasDestino() {
		if (cliDestinoSelec.getCod() != null) {
			listaContasDestino = contaHelper.getListaContasCliente(cliDestinoSelec.getCod());
		}
	}

	public List<ClienteVO> getListaClientesOrigem() {
		return listaClientesOrigem;
	}

	public void setListaClientesOrigem(List<ClienteVO> listaClientesOrigem) {
		this.listaClientesOrigem = listaClientesOrigem;
	}

	public ClienteVO getCliOrigemSelec() {
		return cliOrigemSelec;
	}

	public void setCliOrigemSelec(ClienteVO cliOrigemSelec) {
		this.cliOrigemSelec = cliOrigemSelec;
	}

	public List<ClienteVO> getListaClientesDestino() {
		return listaClientesDestino;
	}

	public void setListaClientesDestino(List<ClienteVO> listaClientesDestino) {
		this.listaClientesDestino = listaClientesDestino;
	}

	public ClienteVO getCliDestinoSelec() {
		return cliDestinoSelec;
	}

	public void setCliDestinoSelec(ClienteVO cliDestinoSelec) {
		this.cliDestinoSelec = cliDestinoSelec;
	}

	public ContaVO getContaOrigemSelec() {
		return contaOrigemSelec;
	}

	public void setContaOrigemSelec(ContaVO contaOrigemSelec) {
		this.contaOrigemSelec = contaOrigemSelec;
	}

	public ContaVO getContaDestinoSelec() {
		return contaDestinoSelec;
	}

	public void setContaDestinoSelec(ContaVO contaDestinoSelec) {
		this.contaDestinoSelec = contaDestinoSelec;
	}

	public List<ContaVO> getListaContasOrigem() {
		return listaContasOrigem;
	}

	public void setListaContasOrigem(List<ContaVO> listaContasOrigem) {
		this.listaContasOrigem = listaContasOrigem;
	}

	public List<ContaVO> getListaContasDestino() {
		return listaContasDestino;
	}

	public void setListaContasDestino(List<ContaVO> listaContasDestino) {
		this.listaContasDestino = listaContasDestino;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDtAgendamento() {
		return dtAgendamento;
	}

	public void setDtAgendamento(Date dtAgendamento) {
		this.dtAgendamento = dtAgendamento;
	}

	public Short getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(Short tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}
}