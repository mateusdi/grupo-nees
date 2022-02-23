package br.com.nees.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.Membro;

public interface MembroDao extends JpaRepository<Membro, Integer> {
	

	@Query(value = "SELECT * FROM membro as m WHERE m.email = ?1 " , nativeQuery = true)
	Membro buscaLogin(String email);
	
	@Query(value = "SELECT * FROM membro as m WHERE m.email = ?1 " , nativeQuery = true)
	Optional<Membro> verificaSeMembroExiste(String email);
	
	@Query(value = "SELECT m.* FROM  membro as m INNER JOIN grupo_has_membro as gm ON (m.id = gm.membro_id) WHERE gm.grupo_id = ?1 " , nativeQuery = true)
	List<Membro> buscaMembrosDaAtividade(int idGrupo);
	
	@Query(value = "SELECT * FROM  membro as m WHERE m.id != 1 " , nativeQuery = true)
	List<Membro> BuscaMembrosSemOAdmin();
	
	@Query(value = "SELECT * FROM  membro as m WHERE m.nome LIKE CONCAT('%',?1,'%') OR m.cpf = CONCAT('%',?1,'%') OR m.email LIKE CONCAT('%',?1,'%') OR m.nascimento = ?1 OR m.rg = CONCAT('%',?1,'%')  " , nativeQuery = true)
	List<Membro> buscaMembros(String valor);


}
