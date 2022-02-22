package br.com.nees.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.Frequencia;

public interface FrequenciaDao extends JpaRepository<Frequencia, Integer> {
	
	@Query(value = "SELECT * FROM frequencia as m WHERE m.atividade_id = ?1 " , nativeQuery = true)
	Optional<Frequencia> buscaFrequeciaAtividade(Integer idAtividade);
	
	


}
