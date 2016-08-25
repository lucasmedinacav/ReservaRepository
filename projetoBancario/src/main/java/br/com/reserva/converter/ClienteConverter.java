package br.com.reserva.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.reserva.vo.ClienteVO;

@FacesConverter(forClass=ClienteVO.class, value="clienteConverter")
public class ClienteConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (ClienteVO) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof ClienteVO) {
			ClienteVO cliente = (ClienteVO) value;
			if (cliente != null && cliente instanceof ClienteVO && cliente.getCod() != null) {
				uiComponent.getAttributes().put(cliente.getCod().toString(), cliente);
				return cliente.getCod().toString();
			}
		}
		return "";
	}
}