package br.com.nees.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.MembroGrupo;

public interface GrupoMembroDao extends JpaRepository<MembroGrupo, Integer> {

	@Query(value = "SELECT * FROM grupo_has_membro as m WHERE m.membro_id = ?1 AND m.grupo_id = ?2 " , nativeQuery = true)
	Optional<MembroGrupo> verificaSeExiste(int idMembro, int idGrupo);
	
	
}
