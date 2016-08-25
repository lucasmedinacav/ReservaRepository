package br.com.reserva.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.reserva.vo.ContaVO;

@FacesConverter(forClass = ContaVO.class, value = "contaConverter")
public class ContaConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (ContaVO) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof ContaVO) {
			ContaVO conta = (ContaVO) value;
			if (conta != null && conta instanceof ContaVO && conta.getNumero() != null) {
				uiComponent.getAttributes().put(conta.getNumero().toString(), conta);
				return conta.getNumero().toString();
			}
		}
		return "";
	}
}