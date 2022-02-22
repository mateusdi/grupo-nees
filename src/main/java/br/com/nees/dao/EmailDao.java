package br.com.nees.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nees.model.EmailLog;

public interface EmailDao extends JpaRepository<EmailLog, Integer> {

}
