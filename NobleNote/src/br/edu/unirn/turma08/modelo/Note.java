package br.edu.unirn.turma08.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
	private int id;
	private String anotacao;
	private Date data;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
