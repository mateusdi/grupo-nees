package br.com.nees.enums;

public enum Tipo {//isso tem que ser uma tabela do banco de dados
	REUNIAO("Reunião"),
	POST("Post"),
	EVENTO("Evento"),
	VIAGEM("Viagem");
	
	private String tipo;
	
	private Tipo (String tipo) {
		this.tipo = tipo;
	}
	
    public String getTipo() {
        return this.tipo;
    }
	
}
