package br.edu.unirn.turma08.modelo;

import java.util.Calendar;

public class Note {
	private int id;
	private String anotacao;
	private boolean anotacaoPublica;
	private Calendar dataAtualizacao;
	private boolean modificada;
	private boolean excluida;

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

	public boolean isAnotacaoPublica() {
		return anotacaoPublica;
	}

	public void setAnotacaoPublica(boolean anotacaoPublica) {
		this.anotacaoPublica = anotacaoPublica;
	}

	public Calendar getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Calendar dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean isModificada() {
		return modificada;
	}

	public void setModificada(boolean modificada) {
		this.modificada = modificada;
	}

	public boolean isExcluida() {
		return excluida;
	}

	public void setExcluida(boolean excluida) {
		this.excluida = excluida;
	}

}
