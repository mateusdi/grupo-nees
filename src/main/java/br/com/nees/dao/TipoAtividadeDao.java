package br.com.nees.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nees.model.TipoAtividade;

public interface TipoAtividadeDao extends JpaRepository<TipoAtividade, Integer> {

}
