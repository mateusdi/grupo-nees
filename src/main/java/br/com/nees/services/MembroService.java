package br.com.nees.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.nees.model.EmailLog;
import br.com.nees.model.Membro;

@Service
public class MembroService {
	


	@Autowired
	EmailService emailService;
	


	@Async
	public void enviaEmailTemplate(Membro membro, List<String> template) {


	

			EmailLog emailModel = new EmailLog();

			emailModel.setEmailTo(membro.getEmail());
			// atividade.getGrupo().getNome()
			emailModel
					.setSubject(template.get(0));// 3
			// parametros
			emailModel.setText(String.format(template.get(1), membro.getNome())); 

			emailModel.setMembroId(membro.getId());

			emailService.enviaEmail(emailModel);
		

	}
	

}
