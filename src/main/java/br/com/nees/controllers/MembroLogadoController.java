package br.com.nees.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.CidadeDao;
import br.com.nees.dao.EstadoDao;
import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.dao.OrgaoExpedidorDao;
import br.com.nees.dao.SexoDao;
import br.com.nees.dto.MembroPerfilDto;
import br.com.nees.model.Membro;
import br.com.nees.model.Telefone;
import br.com.nees.model.Usuario;

@Controller
@RequestMapping("/membro")
public class MembroLogadoController {

	@Autowired
	private MembroDao membroRepositorio;

	@Autowired
	private CidadeDao cidadeRepositorio;

	@Autowired
	private EstadoDao estadoRepositorio;

	@Autowired
	private SexoDao sexoRepositorio;
	
	@Autowired
	private GrupoDao grupoRepositorio;
	


	@Autowired
	private OrgaoExpedidorDao orgaoExpedidorRepositorio;

	// pegar o usuario autenticado e listar os grupos e atividades dele.
	// mostrar as notificações dele

	@GetMapping(value = { "/index", "/" })
	public ModelAndView listarMembros() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("membro/index");

		return mv;
	}

	@GetMapping("/detalhesPerfil")
	public ModelAndView detalhesPerfil(@AuthenticationPrincipal Usuario userDetails, MembroPerfilDto membroPerfilDto) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("membro/perfil/detalhesPerfil");

		Membro membro = membroRepositorio.getById(userDetails.getIdMembro());
		membroPerfilDto.fromMembro(membro);

		mv.addObject("sexos", sexoRepositorio.findAll());
		mv.addObject("expedidores", orgaoExpedidorRepositorio.findAll());
		mv.addObject("estados", estadoRepositorio.findAll());

		if (membro.getEndereco().getCidade() != null) {

			mv.addObject("cidades",
					cidadeRepositorio.findAllCidadesIdEstado(membro.getEndereco().getCidade().getEstado().getId()));
		}
		return mv;
	}

	@GetMapping("/editarPerfil")
	public ModelAndView editarPerfil(@AuthenticationPrincipal Usuario userDetails, MembroPerfilDto membroPerfilDto) {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("membro/perfil/editarPerfil");

		Membro membro = membroRepositorio.getById(userDetails.getIdMembro());
		membroPerfilDto.fromMembro(membro);

		mv.addObject("sexos", sexoRepositorio.findAll());
		mv.addObject("expedidores", orgaoExpedidorRepositorio.findAll());
		mv.addObject("estados", estadoRepositorio.findAll());

		if (membro.getEndereco().getCidade() != null) {

			mv.addObject("cidades",
					cidadeRepositorio.findAllCidadesIdEstado(membro.getEndereco().getCidade().getEstado().getId()));
		}
		return mv;
	}

	@PostMapping("/atualizaPerfil") // verificar se o id é o mesmo da seção.
	public ModelAndView atualizarPerfil(@AuthenticationPrincipal Usuario userDetails,
			@Valid MembroPerfilDto membroPerfilDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return editarPerfil(userDetails, membroPerfilDto);

		} else {

			Optional<Membro> optional = membroRepositorio.findById(userDetails.getIdMembro());

			membroPerfilDto.getEndereco().setId(optional.get().getEndereco().getId());// Adicionando manualmente o id do
																						// endereço
			membroPerfilDto.getTelefones().get(0).setId(optional.get().getTelefones().get(0).getId());// ajuste técnico
			membroPerfilDto.setEmail(optional.get().getEmail());// não deixa alterar o email

			Membro membro = membroPerfilDto.toMembro(optional.get());

			for (Telefone telefone : membro.getTelefones()) {
				telefone.setMembro(membro);
			}

			membro.getEndereco().setMembro(membro);

			membroRepositorio.save(membro);

			return new ModelAndView("redirect:/membro/editarPerfil?sucess");

		}
	}
	
	@GetMapping("/grupos")
	public ModelAndView listarGrupos(@AuthenticationPrincipal Usuario userDetails) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("membro/grupo/listarGrupos");
		mv.addObject("grupos", grupoRepositorio.listaGruposDoMembro(userDetails.getIdMembro())); 

		return mv;
	}
	
	

}
