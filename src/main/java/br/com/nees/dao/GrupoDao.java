package br.com.nees.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.Grupo;

public interface GrupoDao extends JpaRepository<Grupo, Integer> {
	
	@Query(value = "SELECT * FROM grupo as m WHERE m.nome = ?1 " , nativeQuery = true)
	Optional<Grupo> findByName(String nome);
	
	@Query(value = "SELECT g.* FROM grupo AS g JOIN grupo_has_membro AS gm ON (g.id = gm.grupo_id)  WHERE gm.membro_id = ?1 ;" , nativeQuery = true)
	List<Grupo> listaGruposDoMembro(int idMembro);
	

}
