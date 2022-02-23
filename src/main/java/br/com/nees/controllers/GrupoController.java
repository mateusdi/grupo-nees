package br.com.nees.controllers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.AtividadeDao;
import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.GrupoMembroDao;
import br.com.nees.enums.Atribuicao;
import br.com.nees.enums.Status;
import br.com.nees.model.Grupo;
import br.com.nees.model.MembroGrupo;
import br.com.nees.model.Usuario;
import br.com.nees.services.GrupoService;

@Controller
//@RestController
//@RequestMapping("/membro/admin/grupo")
public class GrupoController {

	@Autowired
	private GrupoDao grupoRepositorio;

	@Autowired
	GrupoService grupoService;

	@Autowired
	private GrupoMembroDao grupoMembroRepositorio;

	@Autowired
	private AtividadeDao atividadeRepositorio;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/membro/admin/grupo/novoGrupo") // adicionar um criador do grupo pelo id da sessao.
	public ModelAndView novoGrupo(Grupo grupo) { // criar uma Dto pra esse grupo aqui talvez e verificar se é admin
		ModelAndView mv = new ModelAndView();// só pra postar atividades no grupo que poderá ser admin ou cordenador
		mv.setViewName("admin/grupo/novoGrupo");

		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/membro/admin/grupo/editarGrupo/{id}") // adicionar um criador do grupo pelo id da sessao.
	public ModelAndView editarGrupo(@PathVariable("id") Integer id, Grupo grupo) { // criar uma Dto pra esse grupo aqui
																					// talvez e verificar se é admin
		ModelAndView mv = new ModelAndView();// só pra postar atividades no grupo que poderá ser admin ou cordenador
		mv.setViewName("admin/grupo/editarGrupo");

		Optional<Grupo> optional = grupoRepositorio.findById(id);
		if (!optional.isEmpty()) {
			grupo.fromGrupo(optional.get());
			return mv;
		} else {// erro lança excessao
			return new ModelAndView("redirect:/membro/");
		}

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/membro/admin/grupo/salvarGrupo") // verificar se o nome do grupo já n existe
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {

			ModelAndView mv = new ModelAndView();
			Calendar cal = Calendar.getInstance();
			grupo.setDataCriacao(new Timestamp(cal.getTimeInMillis()));

			grupo.setStatus(Status.ATIVO);
			Optional<Grupo> optional = grupoRepositorio.findByName(grupo.getNome());
			if (optional.isEmpty()) {
				grupoRepositorio.save(grupo);
			}
			mv.setViewName("redirect:/membro/admin/grupo/");

			return mv;
		} else {
			return novoGrupo(grupo);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/membro/admin/grupo/atualizaGrupo/{id}") // verificar se é admin
	public ModelAndView salvaMembro(@PathVariable("id") Integer id, @Valid Grupo grupo, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();

		if (!bindingResult.hasErrors()) {
			grupo.setId(id);

			grupoRepositorio.save(grupo);
			mv.setViewName("redirect:/membro/admin/grupo/");
			return mv;
		} else {
			return editarGrupo(id, grupo);
		}

	}

	@GetMapping("/membro/grupo/{id}") // recebo o id do grupo e listo as atividades daquele grupo
	public ModelAndView listarGrupos(@PathVariable Integer id, @AuthenticationPrincipal Usuario userDetails) {


		if (!grupoService.MembroDoGrupo(userDetails.getIdMembro(), id,userDetails)) {// verifica se o membro pertence ao grupo
			return new ModelAndView("redirect:/membro/grupos/");
		}
		

		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/grupo/grupoAtividades");

		Optional<Grupo> optional = grupoRepositorio.findById(id);
		if (!optional.isEmpty()) {
			mv.addObject("isCoordenador", grupoService.isCoordenador(userDetails, optional.get().getId()));
			mv.addObject("grupo", optional.get());
			mv.addObject("grupoId", optional.get().getId());
			mv.addObject("atividades", atividadeRepositorio.findAllByIdGrupo(id));

			return mv;

		} else {// error
			return new ModelAndView("redirect:/");
		}
	}



	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/membro/admin/grupo/")
	public ModelAndView listarGrupos() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/grupo/listarGrupos");
		mv.addObject("grupos", grupoRepositorio.findAll());

		return mv;
	}

	
//	@GetMapping("/membro/grupo/frequencia/{id}")
//	public ModelAndView frequenciaGrupo(@PathVariable Integer id, @AuthenticationPrincipal Usuario userDetails) {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("admin/grupo/atividade/frequencia");
//
//		Optional<Grupo> optional = grupoRepositorio.findById(id);
//		if (!optional.isEmpty()) {
//			mv.addObject("isCoordenador", isCoordenador(userDetails, optional.get()));
//			mv.addObject("grupo", optional.get());
//			mv.addObject("grupoId", optional.get().getId());
//			mv.addObject("atividades", atividadeRepositorio.findAllByIdGrupo(id));
//
//			return mv;
//
//		} else {// error
//			return new ModelAndView("redirect:/");
//		}
//
//	}

}
