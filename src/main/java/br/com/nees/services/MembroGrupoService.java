package br.com.nees.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nees.dao.GrupoDao;
import br.com.nees.dao.GrupoMembroDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.model.Grupo;
import br.com.nees.model.Membro;
import br.com.nees.model.MembroGrupo;

@Service
public class MembroGrupoService {
	
	@Autowired
	private GrupoDao grupoRepositorio;

	@Autowired
	private MembroDao membroRepositorio;

	@Autowired
	private GrupoMembroDao grupoMembroRepositorio;
	
	public void nsei(Integer id) {
		
		List<Membro> todosMembros = membroRepositorio.findAll();
		Grupo grupo = grupoRepositorio.getById(id);

		List<Membro> membrosDoGrupo = new ArrayList<Membro>();
		for (MembroGrupo s : grupo.getMembroGrupos()) {
			membrosDoGrupo.add(s.getMembro());
		}

		List<Membro> membrosDisponiveis = todosMembros.stream().filter(p -> !membrosDoGrupo.contains(p))
				.collect(Collectors.toList());
	}

}
