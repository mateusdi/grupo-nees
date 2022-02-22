package br.com.nees.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nees.model.Sexo;

public interface SexoDao extends JpaRepository<Sexo, Integer> {

}
