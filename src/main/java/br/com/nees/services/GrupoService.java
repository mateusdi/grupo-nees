package br.com.nees.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.com.nees.dao.GrupoMembroDao;
import br.com.nees.enums.Atribuicao;
import br.com.nees.model.Grupo;
import br.com.nees.model.Membro;
import br.com.nees.model.MembroGrupo;
import br.com.nees.model.Usuario;

@Service
public class GrupoService {

	@Autowired
	GrupoMembroDao grupoMembroRepositorio;

	public boolean MembroDoGrupo(Integer idMembro, Integer idGrupo, Usuario userDetails) {

		if (!(userDetails != null
				&& userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))) {

			Optional<MembroGrupo> optional = grupoMembroRepositorio.verificaSeExiste(idMembro, idGrupo);

			if (optional.isEmpty()) {
				// ele não pertence ao grupo
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}

	}
	
	public boolean isCoordenador( Usuario userDetails, Integer id) {// colocar no service
		// verifico no grupo membro se aquele membro é admin
		Optional<MembroGrupo> optional = grupoMembroRepositorio.verificaSeExiste(userDetails.getIdMembro(),
				id);
		if (!optional.isEmpty()) {
			if (optional.get().getAtribuicaoMembro() == Atribuicao.COORDENADOR) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

}
