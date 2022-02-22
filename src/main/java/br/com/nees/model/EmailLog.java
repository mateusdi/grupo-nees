package br.com.nees.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nees.enums.StatusEmail;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="email_log")
@Getter
@Setter
public class EmailLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String emailFrom;
	
	private int membroId;
	
	private String emailTo;

	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String text;
	
	private Timestamp sendDateEmail;
	
	private StatusEmail statusEmail;
	

	
	

}
