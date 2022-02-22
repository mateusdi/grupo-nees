package br.com.nees.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.Atividade;


public interface AtividadeDao extends JpaRepository<Atividade, Integer> {
	
	@Query(value = "SELECT * FROM atividade as c WHERE c.grupo_id = ?1", nativeQuery = true)
	List<Atividade> findAllByIdGrupo(Integer idGrupo);
	
	@Query(value = "SELECT * FROM atividade AS a WHERE a.prazo_entrega >= ?1 AND a.prazo_entrega <= ?2 ", nativeQuery = true)
	List<Atividade> verificacaoAtividadesDiaria(String hoje, String amanha);

}
