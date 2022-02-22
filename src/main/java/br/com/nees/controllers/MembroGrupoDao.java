package br.com.nees.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.GrupoMembroDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.model.Grupo;
import br.com.nees.model.Membro;
import br.com.nees.model.MembroGrupo;

@Controller
//@RestController
@RequestMapping("/membro/admin/grupo/membroGrupo")
@PreAuthorize("hasRole('ADMIN')")
public class MembroGrupoDao {

	@Autowired
	private GrupoDao grupoRepositorio;

	@Autowired
	private MembroDao membroRepositorio;

	@Autowired
	private GrupoMembroDao grupoMembroRepositorio;

	@GetMapping("/participantes/{id}")
	public ModelAndView participantesMenu(@PathVariable("id") Integer id, MembroGrupo membroGrupo) { 
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/grupo/membrosGrupo");

		List<Membro> todosMembros = membroRepositorio.findAll();
		Grupo grupo = grupoRepositorio.getById(id);

		List<Membro> membrosDoGrupo = new ArrayList<Membro>();
		for (MembroGrupo s : grupo.getMembroGrupos()) {
			membrosDoGrupo.add(s.getMembro());
		}

		List<Membro> membrosDisponiveis = todosMembros.stream().filter(p -> !membrosDoGrupo.contains(p))
				.collect(Collectors.toList());

		mv.addObject("todosMembros", membrosDisponiveis);
		mv.addObject("grupoId", id);

		mv.addObject("grupo", grupo);
		mv.addObject("membrosInscritos", grupo.getMembroGrupos());

		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/grupo/participante/editarParticipante");

		MembroGrupo membroGrupo = grupoMembroRepositorio.getById(id);

		mv.addObject("membroGrupo", membroGrupo);
		// mv.addObject("grupoId",grupo.getId());

		return mv;
	}

	@PostMapping("/salva") 
	public ModelAndView salvarMembroGrupo(MembroGrupo membroGrupo) {
		ModelAndView mv = new ModelAndView();
		System.out.println("recebiRequest!");

		Optional<MembroGrupo> optional = grupoMembroRepositorio.verificaSeExiste(membroGrupo.getMembro().getId(),
				membroGrupo.getGrupo().getId());
		if (optional.isEmpty()) {
			grupoMembroRepositorio.save(membroGrupo);// tem que verificar se o membro já n existe

		} // else error

		mv.setViewName("redirect:/membro/admin/grupo/membroGrupo/participantes/" + membroGrupo.getGrupo().getId());
		return mv;
	}

	@PostMapping("/atualiza") // verificar se o nome do grupo já n existe
	public ModelAndView atualizarMembroGrupo(MembroGrupo membroGrupo) {
		ModelAndView mv = new ModelAndView();

		grupoMembroRepositorio.save(membroGrupo);// tem que verificar se o membro já n existe
		mv.setViewName("redirect:/membro/admin/grupo/membroGrupo/participantes/" + membroGrupo.getGrupo().getId());
		return mv;
	}

	@GetMapping(value = "/listaParticipantes/{id}")
	public ModelAndView pegaFragmento(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("fragmentos/fragmentoParticipantes :: participantes");

		List<Membro> todosMembros = membroRepositorio.findAll();
		Grupo grupo = grupoRepositorio.getById(id);

		List<Membro> membrosDoGrupo = new ArrayList<Membro>();
		for (MembroGrupo s : grupo.getMembroGrupos()) {
			membrosDoGrupo.add(s.getMembro());
		}

		List<Membro> membrosDisponiveis = todosMembros.stream().filter(p -> !membrosDoGrupo.contains(p))
				.collect(Collectors.toList());

		mv.addObject("todosMembros", membrosDisponiveis);

		return mv;
	}

	@GetMapping("/apagaMembroGrupo/{id}/grupo/{idGrupo}") // verificar se o nome do grupo já n existe
	public ModelAndView apagaMembro(@PathVariable Integer id, @PathVariable Integer idGrupo) {
		ModelAndView mv = new ModelAndView();

		grupoMembroRepositorio.deleteById(id);
		// Optional<MembroGrupo> membroGrupo = grupoMembroRepositorio.findById(id);
		mv.setViewName("redirect:/membro/admin/grupo/membroGrupo/participantes/" + idGrupo);
		return mv;
	}

}
