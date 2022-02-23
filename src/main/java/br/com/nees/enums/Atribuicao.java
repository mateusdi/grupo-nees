package br.com.nees.enums;

public enum Atribuicao {
	
	MEMBRO("Membro"),
	COORDENADOR("Membro Editor");
	
	private String atribuicao;
	
	private Atribuicao (String atribuicao) {
		this.atribuicao = atribuicao;
	}
	
    public String getAtribuicao() {
        return this.atribuicao;
    }
}
