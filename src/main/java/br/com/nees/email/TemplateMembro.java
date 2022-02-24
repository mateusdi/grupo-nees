package br.com.nees.email;

import java.util.ArrayList;
import java.util.List;

import br.com.nees.utils.SenhaPadrao;

public class TemplateMembro {
	
	private String urlLogin = "https://grupo-nees.herokuapp.com/login";

/** ------------------------------------------ Mensagem Bem vindo! ---------------------------------------**/
	
	private String bemVindoTexto() {

		return "<p>Olá, <b></b>%s!</p><p>Você foi cadastrado no sistema de gerenciamento do Núcleo de Estudos em Engenharia de Software.</p><p>A sua senha é: <b>"+SenhaPadrao.senhaPadrao+"</b></p><p>para entrar no sistema, basta acessar o link:<b>&nbsp;<a href='"
				+ urlLogin +  "'>" + "Fazer Login" + "</a></b></p><p><br></p> ";
	}

	private String bemVindoAssunto() {
		return "Cadastro realizado - NEES"; 
	}	

	public List<String> bemVindo() {
		List<String> template = new ArrayList<String>();
		template.add(bemVindoAssunto());
		template.add(bemVindoTexto());
		return template;
	}
	

}
