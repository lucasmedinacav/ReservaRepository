package br.com.reserva.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Integer getDiferencaDiasEntreDuasDatas(Date dtInicio, Date dtFim) {
		try {
			Date dtInicioSemHoras = zerarHoras(dtInicio);
			Date dtFimSemHoras = zerarHoras(dtFim);
			long day = 24L * 60L * 60L * 1000L;
			return (int) ((dtFimSemHoras.getTime() - dtInicioSemHoras.getTime()) / day);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date zerarHoras(Date data) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.parse(format.format(data));
	}
}
