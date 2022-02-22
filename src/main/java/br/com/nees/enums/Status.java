package br.com.nees.enums;

public enum Status {
	
	INATIVO(false),
	ATIVO(true);

	private Boolean ativo;
	
	private Status (Boolean ativo) {
		this.ativo = ativo;
	}
}
