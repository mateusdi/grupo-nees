package br.com.nees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nees.dao.CidadeDao;
import br.com.nees.dao.EstadoDao;
import br.com.nees.dao.MembroDao;
import br.com.nees.dao.OrgaoExpedidorDao;
import br.com.nees.dao.SexoDao;

@Service
public class MembroService {
	
	@Autowired
	private MembroDao membroRepositorio;

	@Autowired
	private CidadeDao cidadeRepositorio;

	@Autowired
	private EstadoDao estadoRepositorio;

	@Autowired
	private SexoDao sexoRepositorio;

	@Autowired
	private OrgaoExpedidorDao orgaoExpedidorRepositorio;

	
	

}
