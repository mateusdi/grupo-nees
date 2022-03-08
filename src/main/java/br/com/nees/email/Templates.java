package br.com.nees.email;

import java.util.ArrayList;
import java.util.List;

public class Templates {

	private String urlAtividades = "https://grupo-nees.herokuapp.com/membro/grupo/";


	/** ------------------------------------------ template para novas atividades ---------------------------------------**/
	
	private String novaAtividadeTexto() {

		return "<p>Olá, <b></b>%s!</p><p>Você tem uma nova atividade do grupo <b>%s</b> para <b>%s.</b></p><p>para mais detalhes, basta acessar o link:<b>&nbsp;<a href='"
				+ urlAtividades + "%s'>" + "Minhas Atividades" + "</a></b></p><p><br></p>";
	}// 2 parametros

	private String novaAtividadeAssunto() {
		return "Nova atividade - %s"; // 2 parametros
	}	

	public List<String> novaAtividade() {
		List<String> template = new ArrayList<String>();
		template.add(novaAtividadeAssunto());
		template.add(novaAtividadeTexto());
		return template;
	}
	
	/** ------------------------------------------ template para atividades alteradas ---------------------------------------**/
	
	private String alteradaDataAtividadeTexto() {

		return "<p>Olá, <b></b>%s!</p><p>A atividade do grupo <b>%s </b>foi alterada para a data: <b>%s.</b></p><p>para mais detalhes, basta acessar o link:<b>&nbsp;<a href='"
				+ urlAtividades +  "%s'>" + "Minhas Atividades" + "</a></b></p><p><br></p>";
	}// 2 parametros

	private String alteradaDataAtividadeAssunto() {
		return "Atividade alterada para nova data - %s"; // 2 parametros
	}	

	public List<String> alteradaDataAtividade() {
		List<String> template = new ArrayList<String>();
		template.add(alteradaDataAtividadeAssunto());
		template.add(alteradaDataAtividadeTexto());
		return template;
	}
	
	
	/** ------------------------------------------ template para avisar que o dia é hoje ---------------------------------------**/

	
	private String atividadeHojeAtividadeTexto() {

		return "<p>Olá, <b></b>%s!</p><p>Fique atento, você tem uma atividade do grupo <b>%s </b>para hoje <b>%s.</b></p><p>para mais detalhes, basta acessar o link:<b>&nbsp;<a href='"
				+ urlAtividades +  "%s'>" + "Minhas Atividades" + "</a></b></p><p><br></p>";
	}// 2 parametros

	private String atividadeHojeAtividadeAssunto() {
		return "Atividade para hoje - %s"; // 2 parametros
	}	

	public List<String> atividadeHojeAtividade() {
		List<String> template = new ArrayList<String>();
		template.add(atividadeHojeAtividadeAssunto());
		template.add(atividadeHojeAtividadeTexto());
		return template;
	}
	
/** ------------------------------------------ template para atividades alteradas ---------------------------------------**/
	
	private String alteradaAtividadeTexto() {

		return "<p>Olá, <b></b>%s!</p><p>A atividade do grupo <b>%s </b>foi alterada, a data para entrega da atividade é: <b>%s.</b></p><p>para mais detalhes, basta acessar o link:<b>&nbsp;<a href='"
				+ urlAtividades +  "%s'>" + "Minhas Atividades" + "</a></b></p><p><br></p>";
	}// 2 parametros

	private String alteradaAtividadeAssunto() {
		return "Atividade alterada para nova data - %s"; // 2 parametros
	}	

	public List<String> alteradaAtividade() {
		List<String> template = new ArrayList<String>();
		template.add(alteradaAtividadeAssunto());
		template.add(alteradaAtividadeTexto());
		return template;
	}
	
	
	
	
//	public EmailLog novaAtividade(Atividade atividade) {
//		EmailLog emailModel = new EmailLog();
//		
//		emailModel.setSubject(
//				String.format(novaAtividadeAssunto(), atividade.getGrupo().getNome(), atividade.getTitulo()));
//		
//		
//		emailModel.setEmailTo(emailModel.getMembro().getEmail());		
//		
//		emailModel.setText(String.format(novaAtividadeTexto(), emailModel.getMembro().getNome(), atividade.getGrupo().getNome(),
//				new SimpleDateFormat("yyyy-MM-dd HH:mm").format(atividade.getPrazoEntrega()))); // 2 parametros
//
//		emailModel.setMembroId(emailModel.getMembro().getId());
//		
//		return emailModel;
//	}

}
