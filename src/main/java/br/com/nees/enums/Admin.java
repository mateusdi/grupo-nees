package br.com.nees.enums;

public enum Admin {
	
	NAO("NAO"),
	SIM("SIM");


	
	private String ativo;
	
	private Admin (String ativo) {
		this.ativo = ativo;
	}
}
