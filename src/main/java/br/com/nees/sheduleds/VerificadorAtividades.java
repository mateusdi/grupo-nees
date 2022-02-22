package br.com.nees.sheduleds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.nees.email.Templates;
import br.com.nees.model.Atividade;
import br.com.nees.services.AtividadeService;

@Component 
@EnableScheduling 
public class VerificadorAtividades {
	
	private static final String TIME_ZONE = "America/Sao_Paulo";

	@Autowired
	private AtividadeService atividadeService;
	
//	 // meia-noite 12:00 AM todos os dias
//    @Scheduled(cron="0 0 0 * * ?",zone = TIME_ZONE)
//    public void verificaPorHora() { 
//    	System.out.println(LocalDateTime.now()); 
//    	//verificar se existem tarefas para hoje e mandar email para os membros!
//    	//select todas atividades para hoje
//    	
//        
//    }
    
	 // meia-noite 12:00 AM todos os dias
    @Scheduled(cron="0 0 0 * * ?",zone = TIME_ZONE)
    public void verificaPorHora() { 
    	
    	for(Atividade atividade : atividadeService.atividadesDiarias()) {
    		atividadeService.enviaEmailTemplate(atividade,new Templates().atividadeHojeAtividade());
    	}
    	//verificar se existem tarefas para hoje e mandar email para os membros!
    	//select todas atividades para hoje
    	
        
    }
    


}
