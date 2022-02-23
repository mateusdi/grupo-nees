package br.com.nees.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.nees.dao.AtividadeDao;
import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.GrupoMembroDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.enums.Atribuicao;
import br.com.nees.model.Atividade;
import br.com.nees.model.EmailLog;
import br.com.nees.model.Membro;
import br.com.nees.model.MembroGrupo;
import br.com.nees.model.Usuario;

@Service
public class AtividadeService {
	@Autowired
	EmailService emailService;

	@Autowired
	MembroDao membroRepository;

	// temporario
	@Autowired
	GrupoDao grupoRepositorio;

	@Autowired
	GrupoMembroDao grupoMembroRepositorio;

	@Autowired
	AtividadeDao atividadeRepository;

//	@Async
//	public void enviaEmail(Atividade atividade, EmailLog template) {
//
//		List<Membro> membros = membroRepository.buscaMembrosDaAtividade(atividade.getGrupo().getId());
//		
//		for (Membro membro : membros) {
//			
//			EmailLog emailModel = template;
//			
//			
//			
//			System.out.println(emailModel.getText());
//			
//			//emailService.enviaEmail(emailModel);
//		}
//
//	}

	@Async
	public void enviaEmailTemplate(Atividade atividade, List<String> template) {

		List<Membro> membros = membroRepository.buscaMembrosDaAtividade(atividade.getGrupo().getId());

		for (Membro membro : membros) {

			EmailLog emailModel = new EmailLog();

			emailModel.setEmailTo(membro.getEmail());
			// atividade.getGrupo().getNome()
			emailModel
					.setSubject(String.format(template.get(0), atividade.getGrupo().getNome(), atividade.getTitulo()));// 3
			// parametros
			emailModel.setText(String.format(template.get(1), membro.getNome(), atividade.getGrupo().getNome(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm").format(atividade.getPrazoEntrega()), atividade.getGrupo().getId())); // 2 parametros

			emailModel.setMembroId(membro.getId());

			emailService.enviaEmail(emailModel);
		}

	}

	@Async
	public void enviaEmail(Atividade atividade) {

		List<Membro> membros = membroRepository.buscaMembrosDaAtividade(atividade.getGrupo().getId());

		for (Membro membro : membros) {
			EmailLog emailModel = new EmailLog();
			emailModel.setEmailTo(membro.getEmail());
			emailModel.setText("teste");
			emailModel.setSubject("teste");
			emailModel.setMembroId(membro.getId());

			emailService.enviaEmail(emailModel);
		}

	}

	public List<Atividade> atividadesDiarias() {

		Calendar cal = Calendar.getInstance();
		String hoje = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, 1);
		String amanha = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTimeInMillis());

		return atividadeRepository.verificacaoAtividadesDiaria(hoje, amanha);

	}

	public boolean isCoordenador(Usuario userDetails, Integer id) {// colocar no service
		// verifico no grupo membro se aquele membro é admin
		if (!(userDetails != null
				&& userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))) {
			Optional<MembroGrupo> optional = grupoMembroRepositorio.verificaSeExiste(userDetails.getIdMembro(), id);
			if (!optional.isEmpty()) {
				if (optional.get().getAtribuicaoMembro() == Atribuicao.COORDENADOR) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		}else {
			return true;
		}
	}

}
