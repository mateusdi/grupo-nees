package br.com.nees.model;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Entity
@Table(name="celular")
@Getter
@Setter
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "numero")
	private Long numero;
	
	@ManyToOne
	@JoinColumn(name="membro_id", referencedColumnName = "id")
	private Membro membro;
	
	public Telefone() {
		// TODO Auto-generated constructor stub
	}

}
