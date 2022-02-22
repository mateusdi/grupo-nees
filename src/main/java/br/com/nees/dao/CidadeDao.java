package br.com.nees.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.nees.model.Cidade;

public interface CidadeDao extends JpaRepository<Cidade, Integer> {
	
	@Query(value = "SELECT * FROM cidade as c WHERE c.estado_id = ?1", nativeQuery = true)
	List<Cidade> findAllCidadesIdEstado(Integer idEstado);

}
