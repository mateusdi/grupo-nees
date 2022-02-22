package br.com.nees.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orgao_expedidor")
@Getter
@Setter
public class OrgaoExpedidor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;

	@Column(name = "sigla")
	private String sigla;
	
	@Column(name = "tipo")
	private Integer tipo;
	

	public OrgaoExpedidor() {
		// TODO Auto-generated constructor stub
	}
	
}
