package br.com.nees.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.CidadeDao;
import br.com.nees.dao.EstadoDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.dao.OrgaoExpedidorDao;
import br.com.nees.dao.SexoDao;
import br.com.nees.dto.MembroEditarDto;
import br.com.nees.dto.MembroNovoDto;
import br.com.nees.email.TemplateMembro;
import br.com.nees.enums.Status;
import br.com.nees.model.Grupo;
import br.com.nees.model.Membro;
import br.com.nees.model.MembroGrupo;
import br.com.nees.model.Telefone;
import br.com.nees.services.MembroService;
import br.com.nees.utils.SenhaPadrao;

@Controller
//@RestController
@RequestMapping("/membro/admin")
public class MembroController {

	@Autowired
	private MembroDao membroRepositorio;
	
	@Autowired
	private MembroService membroService;

	@Autowired
	private CidadeDao cidadeRepositorio;

	@Autowired
	private EstadoDao estadoRepositorio;

	@Autowired
	private SexoDao sexoRepositorio;

	@Autowired
	private OrgaoExpedidorDao orgaoExpedidorRepositorio;

	@GetMapping("/novoMembro")
	public ModelAndView novoMembro(MembroNovoDto membroNovoDto) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/membro/novoMembro");
		
		mv.addObject("sexos", sexoRepositorio.findAll());
		mv.addObject("expedidores", orgaoExpedidorRepositorio.findAll());
		mv.addObject("estados", estadoRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nome")));

		return mv;
	}

	@PostMapping("/salvaMembro") 
	public ModelAndView salvaMembro(@Valid MembroNovoDto membroNovoDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			
			return novoMembro(membroNovoDto);

		} else {

			Membro membro = membroNovoDto.toMembro();

			for (Telefone telefone : membro.getTelefones()) {
				telefone.setMembro(membro);
			}

			if (membro.getEndereco() != null) {
				membro.getEndereco().setMembro(membro);
			}
			membro.setSenha(new BCryptPasswordEncoder().encode(SenhaPadrao.senhaPadrao));

			membro.setStatus(Status.ATIVO);

			Optional<Membro> optional = membroRepositorio.verificaSeMembroExiste(membro.getEmail());
			if (optional.isEmpty()) {// verifica se o membro já existe
				try {
				membroRepositorio.save(membro);
				membroService.enviaEmailTemplate(membro, new TemplateMembro().bemVindo());
				}catch (Exception e) {
					// TODO: handle exception
				}
				return new ModelAndView("redirect:/membro/admin/listarMembros");
			} else {
				System.out.println(optional.get());
				return novoMembro(membroNovoDto);
			}

		}
	}

	@GetMapping("/listarMembros")
	public ModelAndView listarMembros() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/membro/listarMembros");
		mv.addObject("membros", membroRepositorio.BuscaMembrosSemOAdmin()); 
		return mv;
	}

	@GetMapping("/editarMembro/{id}")
	public ModelAndView editarMembro(@PathVariable("id") Integer id, MembroEditarDto membroEditarDto) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/membro/editarMembro");

		Optional<Membro> optional = membroRepositorio.findById(id);

		if (!optional.isEmpty()) {
			// Membro membro = membroRepositorio.getById(id);
			Membro membro = optional.get();
			membroEditarDto.fromMembro(membro);

			mv.addObject("sexos", sexoRepositorio.findAll());
			mv.addObject("expedidores", orgaoExpedidorRepositorio.findAll());
			mv.addObject("estados", estadoRepositorio.findAll());
			
		
			if (membro.getEndereco().getCidade() != null) {
				mv.addObject("cidades",
						cidadeRepositorio.findAllCidadesIdEstado(membro.getEndereco().getCidade().getEstado().getId()));
			}

			return mv;
		} else {
			System.out.println("error!");

			return editarMembro(id, membroEditarDto);
		}

	}

	@GetMapping("/detalhesMembro/{id}")
	public ModelAndView detalhesMembro(@PathVariable("id") Integer id, MembroEditarDto membroEditarDto) {
		ModelAndView mv = new ModelAndView();

		Optional<Membro> optional = membroRepositorio.findById(id);

		if (!optional.isEmpty()) {

			Membro membro = optional.get();
			membroEditarDto.fromMembro(membro);

			mv.setViewName("admin/membro/detalhesMembro");

			mv.addObject("sexos", sexoRepositorio.findAll());
			mv.addObject("expedidores", orgaoExpedidorRepositorio.findAll());
			mv.addObject("estados", estadoRepositorio.findAll());

			if (membro.getEndereco().getCidade() != null) {
				mv.addObject("cidades",
						cidadeRepositorio.findAllCidadesIdEstado(membro.getEndereco().getCidade().getEstado().getId()));
			}

			return mv;
		} else {
			System.out.println("error!");
			return new ModelAndView("redirect:/membro/admin/listarMembros");
		}

	}

	@PostMapping("/atualizaMembro/{id}")
	public ModelAndView atualizarMembro(@PathVariable Integer id, @Valid MembroEditarDto membroEditarDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return editarMembro(id, membroEditarDto);

		} else {

			Optional<Membro> optional = membroRepositorio.findById(id);

			membroEditarDto.getEndereco().setId(optional.get().getEndereco().getId());

			if (!optional.get().getTelefones().isEmpty()) {
				membroEditarDto.getTelefones().get(0).setId(optional.get().getTelefones().get(0).getId());
			}

			membroEditarDto.setEmail(optional.get().getEmail());// não deixa alterar o email

			Membro membro = membroEditarDto.toMembro(optional.get());

			for (Telefone telefone : membro.getTelefones()) {
				telefone.setMembro(membro);
			}

			membro.getEndereco().setMembro(membro);

			membroRepositorio.save(membro);

			return new ModelAndView("redirect:/membro/admin/editarMembro/" + membro.getId() + "?sucess");

		}
	}
	
	
	@GetMapping(value = "/listaMembrosTabela/{valor}")
	public ModelAndView pegaFragmento(@PathVariable("valor") String valor) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("fragmentos/fragmentoMembros :: membros");

		List<Membro> membros = membroRepositorio.buscaMembros(valor);
		System.out.println(membros.size());
		mv.addObject("membros", membros);

		return mv;
	}

}
