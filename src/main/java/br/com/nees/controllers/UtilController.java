package br.com.nees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.nees.dao.CidadeDao;
import br.com.nees.model.Cidade;

@Controller
@RestController
public class UtilController {

	@Autowired
	private CidadeDao cidadeRepositorio;

	@RequestMapping(value = "/membro/cidade/{id}", method = RequestMethod.GET)
	public List<Cidade> Get(@PathVariable(value = "id") Integer id) {
		return cidadeRepositorio.findAllCidadesIdEstado(id);
	}
}
