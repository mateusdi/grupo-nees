package br.com.nees.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.AtividadeDao;
import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.TipoAtividadeDao;
import br.com.nees.dto.AtividadeNovoDto;
import br.com.nees.email.Templates;
import br.com.nees.model.Atividade;
import br.com.nees.model.Usuario;
import br.com.nees.services.AtividadeService;
import br.com.nees.services.GrupoService;

@Controller
//@RestController
//@RequestMapping("/membro/grupo/atividade")
public class AtividadeController {

	@Autowired
	private GrupoDao grupoRepositorio;
	
	@Autowired
	GrupoService grupoService;

	@Autowired
	private AtividadeDao atividadeRepositorio;

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private TipoAtividadeDao tipoAtividadeRepositorio;
	
	//tá atribuindo o id do gurpo ao id da atividade
	
	@GetMapping("/membro/grupo/{idGrupo}/novaAtividade") // recebe o id do grupo que vai ser essa nova atividade
	public ModelAndView novaAtividade(@PathVariable("idGrupo") Integer idGrupo, AtividadeNovoDto atividadeNovoDto,@AuthenticationPrincipal Usuario userDetails) {
		
		if (!grupoService.MembroDoGrupo(userDetails.getIdMembro(), idGrupo,userDetails)) {// verifica se o membro pertence ao grupo
			return new ModelAndView("redirect:/membro/grupos/");
		}
		
		if(!atividadeService.isCoordenador(userDetails, idGrupo)) {
			return new ModelAndView("redirect:/membro/grupos/");
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/atividade/novaAtividade");
		mv.addObject("tipos", tipoAtividadeRepositorio.findAll());

		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		String minData = formatador.format(data);

		mv.addObject("minData", minData);

		return mv;
	}

	
	@PostMapping("/membro/grupo/{idGrupo}/novaAtividade")
	public ModelAndView salvaAtividade(@PathVariable("idGrupo") Integer idGrupo, @Valid AtividadeNovoDto atividadeNovoDto,
			@AuthenticationPrincipal Usuario userDetails, BindingResult bindingResult) {
		
		if (!grupoService.MembroDoGrupo(userDetails.getIdMembro(), idGrupo,userDetails)) {// verifica se o membro pertence ao grupo
			return new ModelAndView("redirect:/membro/grupos/");
		}
		
		if(!atividadeService.isCoordenador(userDetails, idGrupo)) {
			return new ModelAndView("redirect:/membro/grupos/");
		}

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			return novaAtividade(idGrupo, atividadeNovoDto,userDetails);

		} else {
			Atividade atividade = atividadeNovoDto.toAtividade();
			Calendar cal = Calendar.getInstance();
			atividade.setDataCriacao(new Timestamp(cal.getTimeInMillis()));

			atividade.setGrupo(grupoRepositorio.findById(idGrupo).get());// gambiarra temporaria

			try {
				atividadeRepositorio.save(atividade);
				atividadeService.enviaEmailTemplate(atividade, new Templates().novaAtividade());// nova atividade, então
																								// envia email
			} catch (Exception e) {
				System.out.println(e);
			}

			return new ModelAndView("redirect:/membro/grupo/" + idGrupo);
		}
	}

	@PostMapping("/membro/grupo/editaAtividade/{id}")
	public ModelAndView alteraAtividade(@PathVariable("id") Integer id, @Valid AtividadeNovoDto atividadeNovoDto,@AuthenticationPrincipal Usuario userDetails,
			BindingResult bindingResult) {
		
	

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			return novaAtividade(id, atividadeNovoDto,userDetails);

		} else {

			Optional<Atividade> optional = atividadeRepositorio.findById(id);

			if (!optional.isEmpty()) {
				
				if (!grupoService.MembroDoGrupo(userDetails.getIdMembro(), optional.get().getGrupo().getId(),userDetails )) {// verifica se o membro pertence ao grupo
					return new ModelAndView("redirect:/membro/grupos/");
				}
				
				if(!atividadeService.isCoordenador(userDetails, optional.get().getGrupo().getId())) {
					return new ModelAndView("redirect:/membro/grupos/");
				}
				
				atividadeNovoDto.setGrupo(optional.get().getGrupo());
				atividadeNovoDto.setId(optional.get().getId());

			

			Atividade atividade = atividadeNovoDto.toAtividade(optional.get());
			// atividade.setGrupo(optional.get().getGrupo());

			try {
				atividadeRepositorio.save(atividade);
				atividadeService.enviaEmailTemplate(atividade, new Templates().alteradaAtividade());

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return new ModelAndView("redirect:/membro/grupo/atividades/" + atividade.getId());
			}else {
				return new ModelAndView("redirect:/membro/grupos");
			}
		}
	}

	@GetMapping("/membro/grupo/editaAtividade/{id}")
	public ModelAndView editaAtividade(@PathVariable("id") Integer id, AtividadeNovoDto atividadeNovoDto, @AuthenticationPrincipal Usuario userDetails) {
		
	

		ModelAndView mv = new ModelAndView();

		mv.setViewName("admin/atividade/editarAtividade");

		Optional<Atividade> optional = atividadeRepositorio.findById(id);

		if (!optional.isEmpty()) {
			
			
			
			
			atividadeNovoDto.fromAtividade(optional.get());
			mv.addObject("tipos", tipoAtividadeRepositorio.findAll());

			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
			String minData = formatador.format(data);

			mv.addObject("minData", minData);
			// Grupo grupo = grupoRepositorio.getById(id);

			// mv.addObject("atividade", atividade);
			// mv.addObject("grupoId",id);

			return mv;
		} else {
			// lança excessao
			return new ModelAndView("redirect:/membro/grupos");
		}
	}// criar um atribuito auxiliar na classe que recebe a data passada e no post
		// comparar se mudou a data. Se mudou entao mando email

	@GetMapping("/membro/grupo/atividades/{id}") // recebe o id do grupo que ele pertence
	public ModelAndView atividadeDetalhes(@PathVariable("id") Integer id,@AuthenticationPrincipal Usuario userDetails) { 

		ModelAndView mv = new ModelAndView();

		mv.setViewName("admin/atividade/verAtividade");

		Optional<Atividade> optional = atividadeRepositorio.findById(id);
		
		
		
		
		

		if (!optional.isEmpty()) {
			mv.addObject("atividade", optional.get());
			
			if (!grupoService.MembroDoGrupo(userDetails.getIdMembro(),optional.get().getGrupo().getId() ,userDetails)) {// verifica se o membro pertence ao grupo
				return new ModelAndView("redirect:/membro/grupos/");
			}
			
			mv.addObject("isCoordenador",  atividadeService.isCoordenador(userDetails,optional.get().getGrupo().getId()));
			
			return mv;
		}else {
			return new ModelAndView("redirect:/membro/grupos");
		}

		
	}

}
