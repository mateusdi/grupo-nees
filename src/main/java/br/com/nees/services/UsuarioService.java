package br.com.nees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nees.dao.MembroDao;
import br.com.nees.model.Membro;
import br.com.nees.model.Usuario;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private MembroDao membroRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Membro membroUsuario = membroRepository.buscaLogin(login);

		if (membroUsuario == null) {
			throw new UsernameNotFoundException("MembroUsuario não encontado!");
		}
		return new Usuario(membroUsuario);
	}
}
