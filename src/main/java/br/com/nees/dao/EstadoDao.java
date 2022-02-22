package br.com.nees.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nees.model.Estado;

public interface EstadoDao extends JpaRepository<Estado, Integer> {

}
