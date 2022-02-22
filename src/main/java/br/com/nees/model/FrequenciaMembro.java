package br.com.nees.model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="membro_has_frequencia")
@Getter
@Setter
public class FrequenciaMembro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private boolean presente;
	
	@ManyToOne
	@JoinColumn(name = "frequencia_id" )
	private Frequencia frequencia;

	@ManyToOne
	@JoinColumn(name = "membro_id")
	private Membro membro;
	
	

}
