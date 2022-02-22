package br.com.nees.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.FrequenciaDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.model.Frequencia;
import br.com.nees.model.Membro;

@Controller
public class FrequenciaController {

	@Autowired
	private MembroDao membroRepositorio;

	@Autowired
	private FrequenciaDao frequenciaRepositorio;

	@GetMapping("/membro/grupo/atividade/frequencia/{id}") // posso listar a frequencia da atividade ou o id da
															// frequencia
	public ModelAndView frequencia(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView();

		List<Membro> membros = membroRepositorio.buscaMembrosDaAtividade(id);

		// List<FrequenciaMembro> frequenciaMembro = new ArrayList<FrequenciaMembro>();
//		//para cada membro adicionar na frequenciaMemrbo
//		for( Membro membro : membros) {
//			FrequenciaMembro m = new FrequenciaMembro();
//			m.setMembro(membro);
//			frequenciaMembro.add(m);
//		}

		// frequencia.setFrequenciaMembro(fresquenciaMembro);

		// posso pegar esses membros e passar para dentro da frequenciamembros
//		frequencia.getFrequenciaMembro();
		mv.addObject("membros", membros);

		// se for null o a frequencia da atividade, ent crio uma no else e jogo o objeto
		// depois salvo com o id da url

		mv.setViewName("/admin/atividade/frequencia/frequencia");

		return mv;
	}

	@PostMapping("/membro/grupo/atividade/frequencia/{id}")
	public ModelAndView salvaFrequencia(@PathVariable("id") Integer id, @Valid Frequencia frequencia,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			return frequencia(id);

		} else {

			Optional<Frequencia> optional = frequenciaRepositorio.buscaFrequeciaAtividade(id);
			if (!optional.isEmpty()) {

				System.out.println("é vazio");

			} else {

				System.out.println("n é vazio");
			}

			return new ModelAndView("redirect:/membro/grupos/");
		}
	}

}
