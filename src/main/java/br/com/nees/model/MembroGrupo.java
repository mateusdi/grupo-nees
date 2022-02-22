package br.com.nees.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nees.enums.Atribuicao;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="grupo_has_membro")
@Getter
@Setter
public class MembroGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "grupo_id" )
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "membro_id")
	private Membro membro;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "atribuicao_membro")
	private Atribuicao atribuicaoMembro; //talvez precise de um status tbm com um enum
}
